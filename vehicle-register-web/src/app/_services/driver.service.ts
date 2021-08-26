import { DriverFilter } from '../_models/driver-filter.model';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { Driver } from '../_models/driver.model';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private apiServerUrl = environment.apiBaseUrl;
  params = new HttpParams();

  constructor(private http: HttpClient) { }

  public getListOfDrivers(): Observable<Driver[]> {
    return this.http.get<Driver[]>(this.apiServerUrl + '/drivers');
  }

  public getDrivers(driverFilter: DriverFilter): Observable<Driver[]> {
    if(driverFilter.age != 0) {
      let params= new HttpParams()
      .set('name', driverFilter.name)
      .set('address', driverFilter.address)
      .set('city', driverFilter.city)
      .set('country', driverFilter.country)
      .set('age', driverFilter.age)
      .set('isValid', driverFilter.isValid)
      .set('page', driverFilter.page)
      .set('size', driverFilter.size)
      .set('sortBy', driverFilter.sortBy)
      .set('sortDirection', driverFilter.sortDirection);
      this.params = params;
    } else {
      let params= new HttpParams()
      .set('name', driverFilter.name)
      .set('address', driverFilter.address)
      .set('city', driverFilter.city)
      .set('country', driverFilter.country)
      .set('isValid', driverFilter.isValid)
      .set('page', driverFilter.page)
      .set('size', driverFilter.size)
      .set('sortBy', driverFilter.sortBy)
      .set('sortDirection', driverFilter.sortDirection);
      this.params = params;
    }
    return this.http.get<Driver[]>(this.apiServerUrl + '/drivers/paging', { params: this.params })
                    .pipe(catchError(this.errorhandler));             
  }

  public getDriver(id: number): Observable<Driver> {
    return this.http.get<Driver>(this.apiServerUrl + '/drivers/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addCity(driver: Driver): Observable<Driver> {
    return this.http.post<Driver>(this.apiServerUrl + '/drivers', driver)
                    .pipe(catchError(this.errorhandler));
  }

  public updateCity(driver: Driver): Observable<Driver> {
    return this.http.put<Driver>(this.apiServerUrl + '/drivers/' + driver.id, driver)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<Driver> {
    return this.http.put<Driver>(this.apiServerUrl + '/drivers/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}
