import {Location} from './location'
import {Fare} from './fare'

export interface PlanDetail{
   origin: Location
   dstination: Location
   fare: Fare
 }
