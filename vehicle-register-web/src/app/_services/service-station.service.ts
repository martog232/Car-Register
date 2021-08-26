import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { ServiceStationFilter } from '../_models/service-station-filter.model';
import { ServiceStation } from '../_models/service-station.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceStationService {

  private apiServerUrl = environment.apiBaseUrl;
params=new HttpParams();

  constructor(private http: HttpClient) { }

  public getListOfServiceStations(): Observable<ServiceStation[]> {
    return this.http.get<ServiceStation[]>(this.apiServerUrl + '/service-stations');
  }

  public getServiceStations(serviceStationFilter: ServiceStationFilter): Observable<ServiceStation[]> {
   if(serviceStationFilter.startWorkTime!=0){
    let params = new HttpParams()
      .set('name', serviceStationFilter.name)
      .set('addressAddress', serviceStationFilter.addressAddress)
      .set('isValid', serviceStationFilter.isValid)
      .set('startWorkTime',serviceStationFilter.startWorkTime)
      .set('page', serviceStationFilter.page)
      .set('size', serviceStationFilter.size)
      .set('sortBy', serviceStationFilter.sortBy)
      .set('sortDirection', serviceStationFilter.sortDirection);
      this.params=params;
    }else if(serviceStationFilter.offWorkTime!=0) {
      let params = new HttpParams()
      .set('name', serviceStationFilter.name)
      .set('addressAddress', serviceStationFilter.addressAddress)
      .set('isValid', serviceStationFilter.isValid)
      .set('offWorkTime',serviceStationFilter.offWorkTime)
      .set('page', serviceStationFilter.page)
      .set('size', serviceStationFilter.size)
      .set('sortBy', serviceStationFilter.sortBy)
      .set('sortDirection', serviceStationFilter.sortDirection);
      this.params=params;
    }else{
        let params=new HttpParams()
        .set('name', serviceStationFilter.name)
        .set('addressAddress', serviceStationFilter.addressAddress)
        .set('isValid', serviceStationFilter.isValid)
        .set('page', serviceStationFilter.page)
        .set('size', serviceStationFilter.size)
        .set('sortBy', serviceStationFilter.sortBy)
        .set('sortDirection', serviceStationFilter.sortDirection);
        this.params=params;
      }
    return this.http.get<ServiceStation[]>(this.apiServerUrl + '/service-stations/paging', { params: this.params })
                    .pipe(catchError(this.errorhandler));
  }

  public getServiceStation(id: number): Observable<ServiceStation> {
    return this.http.get<ServiceStation>(this.apiServerUrl + '/service-stations/' + id)
                    .pipe(catchError(this.errorhandler));
  }

  public addServiceStation(serviceStation: ServiceStation): Observable<ServiceStation> {
    return this.http.post<ServiceStation>(this.apiServerUrl + '/service-stations', serviceStation)
                    .pipe(catchError(this.errorhandler));
  }

  public updateServiceStation(serviceStation: ServiceStation): Observable<ServiceStation> {
    return this.http.put<ServiceStation>(this.apiServerUrl + '/service-stations/' + serviceStation.id, serviceStation)
                    .pipe(catchError(this.errorhandler));
  }

  public changeIsValid(id: number | undefined): Observable<ServiceStation> {
    return this.http.put<ServiceStation>(this.apiServerUrl + '/service-stations/change-is-valid/' + id, null)
                    .pipe(catchError(this.errorhandler));
  }

  errorhandler(error: HttpErrorResponse) {
    return throwError(error);
  }
}
