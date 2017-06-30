package com.iservport.vusage

import scala.beans.BeanProperty

/**
  * Created by mauriciofernandesdecastro on 24/06/17.
  */
class Quantity(

  @BeanProperty var year: String,

  @BeanProperty var cityId: String,

  @BeanProperty var entityId: String,

  @BeanProperty var subject: String,

  @BeanProperty var averagePrice: Double

)
