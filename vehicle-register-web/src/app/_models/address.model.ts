import { City } from "./city.model";

export interface Address {
    id?: number;
    city: City;
    address: string;
    isValid?: boolean;
}