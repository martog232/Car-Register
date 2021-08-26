import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable} from 'rxjs';
import { environment } from '../../environments/environment';
import { ServiceStationServiceFilter } from '../_models/service-station-services-filter.model'
import { ServiceStationService } from '../_models/service-station-service.model';

@Injectable({
  providedIn: 'root'
})
export class ServiceStationServiceService {

  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getServiceStationService(id: number): Observable<ServiceStationService> {
    return this.http.get<ServiceStationService>(this.apiServerUrl + '/service-stations-services/' + id);
  }

  public addServiceStationService(serviceStationService: ServiceStationService): Observable<ServiceStationService> {
    return this.http.post<ServiceStationService>(this.apiServerUrl + '/service-stations-services', serviceStationService);
  }

  public updateServiceStationService(serviceStationService: ServiceStationService): Observable<ServiceStationService> {
    return this.http.put<ServiceStationService>(this.apiServerUrl + '/service-stations-services/' + serviceStationService.id, serviceStationService);
  }

  public changeIsValid(id: number | undefined): Observable<ServiceStationService> {
    return this.http.put<ServiceStationService>(this.apiServerUrl + '/service-stations-services/change-is-valid/' + id, null);
  }
  public getFilterSSS(serviceStationServiceFilter: ServiceStationServiceFilter): Observable<ServiceStationService[]> {
    let params = new HttpParams()
      .set('serviceType',serviceStationServiceFilter.serviceType)
      .set('fuelType',serviceStationServiceFilter.fuelType)
      .set('page', serviceStationServiceFilter.page)
      .set('size', serviceStationServiceFilter.size)
      .set('sortBy', serviceStationServiceFilter.sortBy)
      .set('sortDirection', serviceStationServiceFilter.sortDirection);
    return this.http.get<ServiceStationService[]>(this.apiServerUrl + '/service-stations-services/paging', { params: params })
}
public getListOfServiceStationServices(): Observable<ServiceStationService[]> {
  return this.http.get<ServiceStationService[]>(this.apiServerUrl + '/service-stations-services');
}
}
