import {ServiceStation} from "./service-station.model";
import {ServiceType } from "./service-type.model";
import { FuelType} from "./fuel-type.model"

export interface ServiceStationService {
    id?:number,
    serviceStation:ServiceStation,
    serviceType:ServiceType,
    hourDuration:number,
    fuelType:FuelType,
    isValid?:boolean,
    price:number
}