package com.iservport.vusage

import scala.beans.BeanProperty

/**
  * Expenditures by year, city, entity.
  *
  * Created by mauriciofernandesdecastro on 21/05/17.
  */
class Expenditure(

   @BeanProperty var year: String,

   @BeanProperty var cityId: String,

   @BeanProperty var entityId: String,

   @BeanProperty var fuel: Double,

   @BeanProperty var usage: Double,

   @BeanProperty var averagePrice: Double,

   @BeanProperty var expenditure: Double,

   @BeanProperty var expenditureByArea: Double,

   @BeanProperty var expenditureByInhabitant: Double

)