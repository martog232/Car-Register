import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { environment } from 'src/environments/environment';
import { CityFilter } from '../_models/city-filter.model';
import { City } from '../_models/city.model';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getListOfCities(): Observable<City[]> {
    return this.http.get<City[]>(this.apiServerUrl + '/cities');
  }

  public getCities(cityFilter: CityFilter): Observable<City[]> {
    let params = new HttpParams()
      .set('name', cityFilter.name)
      .set('countryName', cityFilter.countryName)
      .set('isValid', cityFilter.isValid)
      .set('page', cityFilter.page)
      .set('size', cityFilter.size)
      .set('sortBy', cityFilter.sortBy)
      .set('sortDirection', cityFilter.sortDirection);
    return this.http.get<City[]>(this.apiServerUrl + '/cities/paging', { params: params })
                    .pipe(catchError(this.errorhandler));
  }

  public getCity(id: number): Observable<City> {
    return this.http.get<City>(this.apiServerUrl + '/cities/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addCity(city: City): Observable<City> {
    return this.http.post<City>(this.apiServerUrl + '/cities', city)
                    .pipe(catchError(this.errorhandler));
  }

  public updateCity(city: City): Observable<City> {
    return this.http.put<City>(this.apiServerUrl + '/cities/' + city.id, city)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<City> {
    return this.http.put<City>(this.apiServerUrl + '/cities/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}