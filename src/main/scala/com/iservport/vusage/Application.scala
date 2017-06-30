package com.iservport.vusage

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.{ReadConfig, WriteConfig}
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SparkSession

/**
  * Created by mauriciofernandesdecastro on 29/06/17.
  */
object Application extends App {

  import ApplicationConfig._
  import session.implicits._

  val city = MongoSpark.load(session, cityConfig, classOf[CityData]).persist()
  val usage = MongoSpark.load(session, usageConfig, classOf[Usage])
  val us = usage.groupBy($"year", $"cityId", $"entityId", $"fuel").sum("usage").persist()
  val quantity = MongoSpark.load(session, quantityConfig, classOf[Quantity]).persist()
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
    .join(city, "cityId")
    .withColumn("expenditureByArea", $"expenditure" / $"area")
    .withColumn("expenditureByInhabitant", $"expenditure" / $"population")

  MongoSpark.save(expenditure.write.mode("overwrite"), expenditureConfig)

}
object ApplicationConfig {

  @transient lazy val session = SparkSession.builder().master("local").appName("vusage").getOrCreate()

  val config = ConfigFactory.load()
  val mongoUri = config.getString("mongoUri")

  val usageConfig = ReadConfig(Map("uri"  -> s"$mongoUri.usage?readPreference=primaryPreferred"))
  val quantityConfig = ReadConfig(Map("uri"  -> s"$mongoUri.quantity?readPreference=primaryPreferred"))
  val cityConfig = ReadConfig(Map("uri"  -> s"$mongoUri.cityData?readPreference=primaryPreferred"))
  val expenditureConfig = WriteConfig(Map("uri" -> s"$mongoUri.expenditure"))

}