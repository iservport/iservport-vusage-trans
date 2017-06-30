package com.iservport.vusage

import scala.beans.BeanProperty

/**
  * Dados próprios de um veículo.
  *
  * Created by mauriciofernandesdecastro on 21/05/17.
  */
class Usage(

   @BeanProperty var year: String,

   @BeanProperty var cityId: String,

   @BeanProperty var entityId: String,

   @BeanProperty var fuel: String,

   @BeanProperty var usage: Double

)
