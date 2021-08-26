import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/_models/address.model';
import { APIError } from 'src/app/_models/apiError.model';
import { ServiceStationFilter } from 'src/app/_models/service-station-filter.model';
import { ServiceStation } from 'src/app/_models/service-station.model';
import { AddressService } from 'src/app/_services/address.service';
import { ServiceStationService } from 'src/app/_services/service-station.service';

@Component({
  selector: 'app-service-station-add-edit',
  templateUrl: './service-station-add-edit.component.html',
  styleUrls: ['./service-station-add-edit.component.css']
})
export class ServiceStationAddEditComponent implements OnInit {

  currentServiceStation: ServiceStation = {
    id: undefined,
    name: '',
    address: {
      id: undefined,
    address: '',
    city: {
      id: -1,
      name: '',
      country: {
        id: -1,
        name: '',
        code: '',
        isValid: true
      },
      isValid: true
    },
    isValid: undefined
    },
    isValid: undefined,
    startWorkTime:0,
    offWorkTime:0
  }

  serviceStationFilter: ServiceStationFilter = {
    name: '',
    addressAddress: '',
    isValid: '',
    startWorkTime: 0,
    offWorkTime: 0,
    page: 0,
    size: 10,
    sortBy: 'name',
    sortDirection: 'asc'
  }

  apiError: APIError = {
    code: '',
    name: '',
    description: ''
  };

  addressesList: Address[] = [];

  serviceStationSubscription: Subscription | undefined;
  serviceStationEditSubscription: Subscription | undefined;
  serviceStationAddSubscription: Subscription | undefined;
  addressesSubscription: Subscription | undefined;

  constructor(private serviceStationService: ServiceStationService, private addressService: AddressService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getServiceStation(this.rote.snapshot.params.id);
    }
    this.getListOfAddresses();
  }

  ngOnDestroy(): void {
    this.serviceStationSubscription?.unsubscribe();
    this.serviceStationEditSubscription?.unsubscribe();
    this.serviceStationAddSubscription?.unsubscribe();
    this.addressesSubscription?.unsubscribe();
  }

  getServiceStation(id: number): void {
    this.serviceStationSubscription = this.serviceStationService.getServiceStation(id).subscribe(
      data => {
        console.log(data);
        this.currentServiceStation = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateServiceStation(): void {
    this.serviceStationEditSubscription = this.serviceStationService.updateServiceStation(this.currentServiceStation).subscribe(
      data => {
        console.log(data);
        this.serviceStationService.getServiceStations(this.serviceStationFilter);
        this.goToServiceStationsList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }  

  onAddServiceStation(): void {
    this.serviceStationAddSubscription = this.serviceStationService.addServiceStation(this.currentServiceStation).subscribe(
      data => {
        console.log(data);
        this.serviceStationService.getServiceStations(this.serviceStationFilter);
        this.goToServiceStationsList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToServiceStationsList() {
    this.router.navigate(['/service-stations']);
  }

  getListOfAddresses(): void {
    this.addressesSubscription = this.addressService.getListOfAddresses().subscribe(
     data => {
       this.addressesList = data;
     },
     error => {
      this.apiError = error.error;
      console.log(this.apiError);
    }
   );
  }

}
