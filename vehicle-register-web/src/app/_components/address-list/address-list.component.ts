import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AddressFilter } from 'src/app/_models/address-filter.model';
import { Address } from 'src/app/_models/address.model';
import { APIError } from 'src/app/_models/apiError.model';
import { AddressService } from 'src/app/_services/address.service';

@Component({
  selector: 'app-address-list',
  templateUrl: './address-list.component.html',
  styleUrls: ['./address-list.component.css']
})
export class AddressListComponent implements OnInit {

  addresses: Address[] = [];
  addressFilter: AddressFilter = {
    address: '',
    cityName: '',
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

  addressListSubscription: Subscription | undefined;
  
  constructor(private addressService: AddressService) { }
  
  ngOnInit() {
    this.getAddresses(this.addressFilter);
  }

  ngOnDestroy(): void {
    this.addressListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentAddressFilter: AddressFilter): void{
    this.addressService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.addressFilter.size = currentAddressFilter.size;
        this.addressFilter.page = currentAddressFilter.page - 1;
        this.addressFilter.address = currentAddressFilter.address;
        this.addressFilter.cityName = currentAddressFilter.cityName;
        this.addressFilter.isValid = currentAddressFilter.isValid;
        this.getAddresses(this.addressFilter);
        currentAddressFilter.page = this.addressFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    )
  }

  onChange(currentSize: number) {
    this.addressFilter.size = currentSize;
    this.addressFilter.page = 0;
    this.getAddresses(this.addressFilter);
  }

  onPageChange(currentPage: number) {
    this.addressFilter.page = currentPage - 1;
    this.getAddresses(this.addressFilter);
    this.addressFilter.page = currentPage;
  }

  onSearch(currentAddressFilter: AddressFilter) {
    this.addressFilter.size = currentAddressFilter.size;
    this.addressFilter.page = 0;
    this.addressFilter.address = currentAddressFilter.address;
    this.addressFilter.cityName = currentAddressFilter.cityName;
    this.addressFilter.isValid = currentAddressFilter.isValid;
    this.getAddresses(this.addressFilter);
  }

  onChangeSort(currentAddressFilter: AddressFilter) {
    this.addressFilter.sortBy = currentAddressFilter.sortBy;
    this.addressFilter.page = 0;
    this.getAddresses(this.addressFilter);
  }

  getAddresses(addressFilter: AddressFilter): void {
    this.addressListSubscription = this.addressService.getAddresses(addressFilter).subscribe(
     (response: any) => {
       this.addresses = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }

}
