import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Address } from 'src/app/_models/address.model';
import { APIError } from 'src/app/_models/apiError.model';
import { DriverFilter } from 'src/app/_models/driver-filter.model';
import { Driver } from 'src/app/_models/driver.model';
import { AddressService } from 'src/app/_services/address.service';
import { DriverService } from 'src/app/_services/driver.service';

@Component({
  selector: 'app-driver-add-edit',
  templateUrl: './driver-add-edit.component.html',
  styleUrls: ['./driver-add-edit.component.css']
})
export class DriverAddEditComponent implements OnInit {

  currentDriver: Driver = {
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
    age: 0,
    isValid: undefined
  }

  driverFilter: DriverFilter = {
    name: '',
    address: '',
    city: '',
    country: '',
    age: 0,
    isValid: '',
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

  driverSubscription: Subscription | undefined;
  driverEditSubscription: Subscription | undefined;
  driverAddSubscription: Subscription | undefined;
  addressesSubscription: Subscription | undefined;

  constructor(private driverService: DriverService, private addressService: AddressService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getDriver(this.rote.snapshot.params.id);
    }
    this.getListOfAddresses();
  }

  ngOnDestroy(): void {
    this.driverSubscription?.unsubscribe();
    this.driverEditSubscription?.unsubscribe();
    this.driverAddSubscription?.unsubscribe();
    this.addressesSubscription?.unsubscribe();
  }

  getDriver(id: number): void {
    this.driverSubscription = this.driverService.getDriver(id).subscribe(
      data => {
        console.log(data);
        this.currentDriver = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateDriver(): void {
    this.driverEditSubscription = this.driverService.updateCity(this.currentDriver).subscribe(
      data => {
        console.log(data);
        this.driverService.getDrivers(this.driverFilter);
        this.goToDriversList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }  

  onAddDriver(): void {
    this.driverAddSubscription = this.driverService.addCity(this.currentDriver).subscribe(
      data => {
        console.log(data);
        this.driverService.getDrivers(this.driverFilter);
        this.goToDriversList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToDriversList() {
    this.router.navigate(['/drivers']);
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
