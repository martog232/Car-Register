import  {Country} from "./country.model"
export interface Brand{
    id?:number;
    name:string;
    country:Country;
    isValid?:boolean;
}