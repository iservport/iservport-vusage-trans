package com.iservport.vusage

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.{ReadConfig, WriteConfig}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

/**
  * Created by mauriciofernandesdecastro on 29/06/17.
  */
object Application extends App {

  @transient lazy val session = SparkSession.builder().master("local").appName("vusage").getOrCreate()

  val config = ConfigFactory.load()
  val mongoUri = config.getString("mongoUri")

  import session.implicits._
  val rc1 = ReadConfig(Map("uri"  -> s"$mongoUri.usage?readPreference=primaryPreferred"))
  val rc2 = ReadConfig(Map("uri"  -> s"$mongoUri.quantity?readPreference=primaryPreferred"))
  val wc3 = WriteConfig(Map("uri" -> s"$mongoUri.expenditure"))

  val usage = MongoSpark.load(session, rc1, classOf[Usage])
  val us = usage.groupBy($"year", $"cityId", $"entityId", $"fuel").sum("usage").persist()

  val quantity = MongoSpark.load(session, rc2, classOf[Quantity]).persist()
  val qs = quantity.groupBy($"year", $"cityId", $"entityId", $"subject").avg("averagePrice").persist()

  val expenditure = us.join(qs,
    qs.col("year") === us.col("year")
      && qs.col("cityId") === us.col("cityId")
      && qs.col("entityId") === us.col("entityId")
      && qs.col("subject").startsWith(us.col("fuel"))
  ).withColumn("expenditure", $"avg(averagePrice)" * $"sum(usage)")
    .drop(qs.col("year"))
    .drop(qs.col("cityId"))
    .drop(qs.col("entityId"))
    .drop(qs.col("subject"))
    .toDF("year", "cityId", "entityId", "fuel", "usage", "averagePrice", "expenditure")

  MongoSpark.save(expenditure.write.mode("overwrite"), wc3)

}