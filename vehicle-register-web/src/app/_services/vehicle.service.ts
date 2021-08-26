import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Vehicle } from '../_models/vehicle.model';
import { environment} from '../../environments/environment';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {

  private apiServerUrl = environment.apiBaseUrl + '/vehicles/';
  params=new HttpParams();

  constructor(private http:HttpClient) { }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }

  public getVehicle(id:number): Observable<Vehicle> {
    return this.http.get<Vehicle>(this.apiServerUrl  + id)
    .pipe(catchError(this.errorhandler));
  }

  public getListOfVehicle(): Observable<Vehicle[]>{
    return this.http.get<Vehicle[]>(this.apiServerUrl);
  }
}
