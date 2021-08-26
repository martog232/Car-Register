import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { ServiceTypeFilter } from '../_models/service-type-filter.model';
import { ServiceType } from '../_models/service-type.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceTypeService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getListOfServiceTypes(): Observable<ServiceType[]> {
    return this.http.get<ServiceType[]>(this.apiServerUrl + '/service-types');
  }

  public getServiceTypes(serviceTypeFilter: ServiceTypeFilter): Observable<ServiceType[]> {
    let params = new HttpParams()
      .set('name', serviceTypeFilter.name)
      .set('description', serviceTypeFilter.description)
      .set('code', serviceTypeFilter.code)
      .set('isValid', serviceTypeFilter.isValid)
      .set('page', serviceTypeFilter.page)
      .set('size', serviceTypeFilter.size)
      .set('sortBy', serviceTypeFilter.sortBy)
      .set('sortDirection', serviceTypeFilter.sortDirection);
    return this.http.get<ServiceType[]>(this.apiServerUrl + '/service-types/paging', { params: params })
                    .pipe(catchError(this.errorhandler));
  }

  public getServiceType(id: number): Observable<ServiceType> {
    return this.http.get<ServiceType>(this.apiServerUrl + '/service-types/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addServiceType(serviceType: ServiceType): Observable<ServiceType> {
    return this.http.post<ServiceType>(this.apiServerUrl + '/service-types', serviceType)
                    .pipe(catchError(this.errorhandler));
  }

  public updateServiceType(serviceType: ServiceType): Observable<ServiceType> {
    return this.http.put<ServiceType>(this.apiServerUrl + '/service-types/' + serviceType.id, serviceType)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<ServiceType> {
    return this.http.put<ServiceType>(this.apiServerUrl + '/service-types/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}
