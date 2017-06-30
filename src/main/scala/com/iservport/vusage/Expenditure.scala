package com.iservport.vusage

import scala.beans.BeanProperty

/**
  * Gastos por cidade
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

   @BeanProperty var expenditure: Double

)