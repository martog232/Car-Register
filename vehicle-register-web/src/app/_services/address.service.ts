import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AddressFilter } from '../_models/address-filter.model';
import { Address } from '../_models/address.model';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getListOfAddresses(): Observable<Address[]> {
    return this.http.get<Address[]>(this.apiServerUrl + '/addresses');
  }

  public getAddresses(addressFilter: AddressFilter): Observable<Address[]> {
    let params = new HttpParams()
      .set('address', addressFilter.address)
      .set('cityName', addressFilter.cityName)
      .set('isValid', addressFilter.isValid)
      .set('page', addressFilter.page)
      .set('size', addressFilter.size)
      .set('sortBy', addressFilter.sortBy)
      .set('sortDirection', addressFilter.sortDirection);
    return this.http.get<Address[]>(this.apiServerUrl + '/addresses/paging', { params: params })
                    .pipe(catchError(this.errorhandler));
  }

  public getAddress(id: number): Observable<Address> {
    return this.http.get<Address>(this.apiServerUrl + '/addresses/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addAddress(address: Address): Observable<Address> {
    return this.http.post<Address>(this.apiServerUrl + '/addresses', address)
                    .pipe(catchError(this.errorhandler));
  }

  public updateAddress(address: Address): Observable<Address> {
    return this.http.put<Address>(this.apiServerUrl + '/addresses/' + address.id, address)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<Address> {
    return this.http.put<Address>(this.apiServerUrl + '/addresses/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}
