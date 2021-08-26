import { Address } from "./address.model";

export interface Driver {
    id?: number;
    name: string;
    address: Address;
    age: number;
    isValid?: boolean;
}