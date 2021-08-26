import { Component, OnInit } from '@angular/core';
import { ServiceStationServiceFilter } from '../../_models/service-station-services-filter.model';
import { ServiceStationService } from '../../_models/service-station-service.model';
import { ServiceStationServiceService } from '../../_services/service-station-service.service';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {
  serviceStationService: ServiceStationService[] = [];
  serviceStationServiceFilter: ServiceStationServiceFilter = {
    serviceStation: '',
    city:'',
    serviceType: '',
    fuelType:'',
    price:0,
    hourDuration:0,
    isValid: 'true',
    page: 0,
    size: 10,
    sortBy: 'id',
    sortDirection: 'asc'
  }
  totalElements: number = 0;

  sSSSubscription: Subscription | undefined;

  constructor(private serviceStationServiceService:ServiceStationServiceService) { }

  ngOnInit(): void {
    this.getFilterSSS(this.serviceStationServiceFilter);
  }
  ngOnDestroy(): void {
  this.sSSSubscription?.unsubscribe();
} 

  onSearch(currentServiceStationServiceFilter: ServiceStationServiceFilter) {
    this.serviceStationServiceFilter.size = currentServiceStationServiceFilter.size;
    this.serviceStationServiceFilter.page = 0;
    this.serviceStationServiceFilter.city = currentServiceStationServiceFilter.city;
    this.serviceStationServiceFilter.fuelType=currentServiceStationServiceFilter.fuelType;
    this.serviceStationServiceFilter.serviceType=currentServiceStationServiceFilter.serviceType;
    this.serviceStationServiceFilter.serviceStation=currentServiceStationServiceFilter.serviceStation;
    this.serviceStationServiceFilter.isValid = currentServiceStationServiceFilter.isValid;
    this.getFilterSSS(this.serviceStationServiceFilter);
  }

  getFilterSSS(sSSFilter: ServiceStationServiceFilter): void {
    this.sSSSubscription = this.serviceStationServiceService.getFilterSSS(sSSFilter).subscribe(
     (response: any) => {
       this.serviceStationService = response['content'];
       this.totalElements = response['totalElements'];
     },  (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
 }
  
}
