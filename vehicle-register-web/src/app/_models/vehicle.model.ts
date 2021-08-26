import {Driver} from './driver.model';
import { FuelType } from './fuel-type.model';
import {CarType} from './car-type.model';
import { Country } from './country.model';
import {Brand} from './brand.model';
export interface Vehicle {
    id?: number;
    year:number;
    carType:CarType;
    seats:number;
    doors:number;
    color:string;
    power:number;
    fuelType:FuelType;
    country:Country;
    driver:Driver;
    brand:Brand;
    regNumber:string;
    isValid?:boolean;
}