import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators'
import { environment } from 'src/environments/environment';
import { CountryFilter } from '../_models/country-filter.model';
import { Country } from '../_models/country.model';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getListOfCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(this.apiServerUrl + '/countries');
  }

  public getCountries(countryFilter: CountryFilter): Observable<Country[]> {
    let params = new HttpParams()
      .set('name', countryFilter.name)
      .set('code', countryFilter.code)
      .set('isValid', countryFilter.isValid)
      .set('page', countryFilter.page)
      .set('size', countryFilter.size)
      .set('sortBy', countryFilter.sortBy)
      .set('sortDirection', countryFilter.sortDirection);
    return this.http.get<Country[]>(this.apiServerUrl + '/countries/paging', { params: params })
                    .pipe(catchError(this.errorhandler));
  }

  public getCountry(id: number): Observable<Country> {
    return this.http.get<Country>(this.apiServerUrl + '/countries/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addCountry(country: Country): Observable<Country> {
    return this.http.post<Country>(this.apiServerUrl + '/countries', country)
                    .pipe(catchError(this.errorhandler));
  }

  public updateCountry(country: Country): Observable<any> {
    return this.http.put<any>(this.apiServerUrl + '/countries/' + country.id, country)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<Country> {
    return this.http.put<Country>(this.apiServerUrl + '/countries/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}
