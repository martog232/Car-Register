import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { DriverFilter } from 'src/app/_models/driver-filter.model';
import { Driver } from 'src/app/_models/driver.model';
import { DriverService } from 'src/app/_services/driver.service';

@Component({
  selector: 'app-driver-list',
  templateUrl: './driver-list.component.html',
  styleUrls: ['./driver-list.component.css']
})
export class DriverListComponent implements OnInit {

  drivers: Driver[] = [];
  driverFilter: DriverFilter = {
    name: '',
    address: '',
    city: '',
    country: '',
    age: 0,
    isValid: '',
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

  driversListSubscription: Subscription | undefined;
  
  constructor(private driverService: DriverService) { }
  
  ngOnInit() {
    this.getDrivers(this.driverFilter);
  }

  ngOnDestroy(): void {
    this.driversListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentDriverFilter: DriverFilter): void{
    this.driverService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.driverFilter.size = currentDriverFilter.size;
        this.driverFilter.page = currentDriverFilter.page - 1;
        this.driverFilter.name = currentDriverFilter.name;
        this.driverFilter.address = currentDriverFilter.address;
        // this.driverFilter.city = currentDriverFilter.city;
        // this.driverFilter.country = currentDriverFilter.country;
        this.driverFilter.isValid = currentDriverFilter.isValid;
        this.getDrivers(this.driverFilter);
        currentDriverFilter.page = this.driverFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    )
  }

  onChange(currentSize: number) {
    this.driverFilter.size = currentSize;
    this.driverFilter.page = 0;
    this.getDrivers(this.driverFilter);
  }

  onPageChange(currentPage: number) {
    this.driverFilter.page = currentPage - 1;
    this.getDrivers(this.driverFilter);
    this.driverFilter.page = currentPage;
  }

  onSearch(currentDriverFilter: DriverFilter) {
    this.driverFilter.size = currentDriverFilter.size;
    this.driverFilter.page = 0;
    this.driverFilter.name = currentDriverFilter.name;
    this.driverFilter.address = currentDriverFilter.address;
    // this.driverFilter.city = currentDriverFilter.city;
    // this.driverFilter.country = currentDriverFilter.country;
    this.driverFilter.isValid = currentDriverFilter.isValid;
    this.getDrivers(this.driverFilter);
  }

  onChangeSort(currentDriverFilter: DriverFilter) {
    this.driverFilter.sortBy = currentDriverFilter.sortBy;
    this.driverFilter.page = 0;
    this.getDrivers(this.driverFilter);
  }

  getDrivers(driverFilter: DriverFilter): void {
    this.driversListSubscription = this.driverService.getDrivers(driverFilter).subscribe(
     (response: any) => {
       this.drivers = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }

}
