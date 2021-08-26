export interface ServiceStationServiceFilter{
    serviceType:string;
    serviceStation: string;
    city: string;
    fuelType:string;
    isValid:string;
    price:number;
    hourDuration:number;
    page: number;
    size: number;
    sortBy: string;
    sortDirection: string;
}