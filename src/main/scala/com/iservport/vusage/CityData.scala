package com.iservport.vusage

import scala.beans.BeanProperty

/**
  * Dados da cidade.
  *
  * Created by mauriciofernandesdecastro on 21/05/17.
  */
class CityData(

   @BeanProperty var cityId: String,

   @BeanProperty var nmMunicipio: String,

   @BeanProperty var area: Double,

   @BeanProperty var population: Int

)
