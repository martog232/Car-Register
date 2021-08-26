import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { APIError } from '../../../app/_models/apiError.model';
import { ServiceStationFilter } from '../../../app/_models/service-station-filter.model';
import { ServiceStation } from '../../../app/_models/service-station.model';
import { ServiceStationService } from '../../../app/_services/service-station.service';

@Component({
  selector: 'app-service-station-list',
  templateUrl: './service-station-list.component.html',
  styleUrls: ['./service-station-list.component.css']
})
export class ServiceStationListComponent implements OnInit {

  serviceStations: ServiceStation[] = [];
  serviceStationFilter:ServiceStationFilter = {
    name: '',
    addressAddress: '',
    isValid: '',
    startWorkTime:0,
    offWorkTime:0,
    page: 0,
    size: 10,
    sortBy: 'id',
    sortDirection: 'asc'
  }

  apiError: APIError = {
    code: '',
    name: '',
    description: ''
  };

  totalElements: number = 0;

  serviceStationListSubscription: Subscription | undefined;
  
  constructor(private serviceStationService: ServiceStationService) { }
  
  ngOnInit() {
    this.getServiceStations(this.serviceStationFilter);
  }

  ngOnDestroy(): void {
    this.serviceStationListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentServiceStationFilter: ServiceStationFilter): void{
    this.serviceStationService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.serviceStationFilter.size = currentServiceStationFilter.size;
        this.serviceStationFilter.page = currentServiceStationFilter.page - 1;
        this.serviceStationFilter.name = currentServiceStationFilter.name;
        this.serviceStationFilter.addressAddress = currentServiceStationFilter.addressAddress;
        this.serviceStationFilter.isValid = currentServiceStationFilter.isValid;
        this.serviceStationFilter.startWorkTime=currentServiceStationFilter.startWorkTime;
        this.serviceStationFilter.offWorkTime=currentServiceStationFilter.offWorkTime;
        this.getServiceStations(this.serviceStationFilter);
        currentServiceStationFilter.page = this.serviceStationFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    )
  }

  onChange(currentSize: number) {
    this.serviceStationFilter.size = currentSize;
    this.serviceStationFilter.page = 0;
    this.getServiceStations(this.serviceStationFilter);
  }

  onPageChange(currentPage: number) {
    this.serviceStationFilter.page = currentPage - 1;
    this.getServiceStations(this.serviceStationFilter);
    this.serviceStationFilter.page = currentPage;
  }

  onSearch(currentServiceStationFilter: ServiceStationFilter) {
    this.serviceStationFilter.size = currentServiceStationFilter.size;
    this.serviceStationFilter.page = 0;
    this.serviceStationFilter.name = currentServiceStationFilter.name;
    this.serviceStationFilter.addressAddress = currentServiceStationFilter.addressAddress;
    this.serviceStationFilter.isValid = currentServiceStationFilter.isValid;
    this.serviceStationFilter.startWorkTime=currentServiceStationFilter.startWorkTime;
    this.serviceStationFilter.offWorkTime=currentServiceStationFilter.offWorkTime;
    this.getServiceStations(this.serviceStationFilter);
  }

  onChangeSort(currentCityFilter: ServiceStationFilter) {
    this.serviceStationFilter.sortBy = currentCityFilter.sortBy;
    this.serviceStationFilter.page = 0;
    this.getServiceStations(this.serviceStationFilter);
  }

  getServiceStations(serviceStationFilter: ServiceStationFilter): void {
    this.serviceStationListSubscription = this.serviceStationService.getServiceStations(serviceStationFilter).subscribe(
     (response: any) => {
       this.serviceStations = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }
}
