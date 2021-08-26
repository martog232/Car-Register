import { Address } from "./address.model";

export interface ServiceStation {
    id?: number;
    name: string;
    address: Address;
    isValid?: boolean;
    startWorkTime:number;
    offWorkTime:number;
}