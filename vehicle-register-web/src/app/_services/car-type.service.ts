import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CarType } from '../_models/car-type.model';

@Injectable({
  providedIn: 'root'
})
export class CarTypeService {
  private carTypeUrl = 'http://localhost:8080/vehicle-register-ws/car-types';

  constructor(private http: HttpClient) { }

getCarType(id:number): Observable<any>{
  return this.http.get(`${this.carTypeUrl}/${id}`);
}

public add(carType: CarType): Observable<CarType> {
  return this.http.post<CarType>(this.carTypeUrl, carType);
}

update(carType: CarType): Observable<any> {
  return this.http.put<CarType>(`${this.carTypeUrl}/${carType.id}`, carType);
}

getCarTypeList(): Observable<CarType[]> {
  return this.http.get<CarType[]>(this.carTypeUrl);
}

changeIsValid(carType: CarType):Observable<any>{
  return this.http.put(`${this.carTypeUrl}/change-is-valid/${carType.id}`, carType);
}
}

