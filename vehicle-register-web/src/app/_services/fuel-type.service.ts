import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FuelType } from '../_models/fuel-type.model';
import { environment } from '../../environments/environment';
import { Vehicle } from '../_models/vehicle.model';

@Injectable({
  providedIn: 'root'
})
export class FuelTypeService {
  private fuelTypeUrl = 'http://localhost:8080/vehicle-register-ws/fuel-types';
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  getFuelType(id:number): Observable<any>{
  return this.http.get(`${this.fuelTypeUrl}/${id}`);
}

public add(fuelType: FuelType): Observable<FuelType> {
  return this.http.post<FuelType>(this.apiServerUrl + '/fuel-types', fuelType);
}
update(fuelType: FuelType): Observable<any> {
  return this.http.put<FuelType>(`${this.fuelTypeUrl}/${fuelType.id}`, fuelType);
}
getFuelTypeList(): Observable<FuelType[]> {
  return this.http.get<FuelType[]>(this.fuelTypeUrl);
}
changeIsValid(fuelType: FuelType):Observable<any>{
  return this.http.put(`${this.fuelTypeUrl}/change-is-valid/${fuelType.id}`, fuelType);
}
}
