import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AddressFilter } from 'src/app/_models/address-filter.model';
import { Address } from 'src/app/_models/address.model';
import { APIError } from 'src/app/_models/apiError.model';
import { City } from 'src/app/_models/city.model';
import { AddressService } from 'src/app/_services/address.service';
import { CityService } from 'src/app/_services/city.service';

@Component({
  selector: 'app-address-add-edit',
  templateUrl: './address-add-edit.component.html',
  styleUrls: ['./address-add-edit.component.css']
})
export class AddressAddEditComponent implements OnInit {

  currentAddress: Address = {
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
  }

  addressFilter: AddressFilter = {
    address: '',
    cityName: '',
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

  citiesList: City[] = [];

  addressSubscription: Subscription | undefined;
  addressEditSubscription: Subscription | undefined;
  addressAddSubscription: Subscription | undefined;
  citiesSubscription: Subscription | undefined;

  constructor(private addressService: AddressService, private cityService: CityService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getAddress(this.rote.snapshot.params.id);
    }
    this.getListOfCities();
  }

  ngOnDestroy(): void {
    this.addressSubscription?.unsubscribe();
    this.addressEditSubscription?.unsubscribe();
    this.addressAddSubscription?.unsubscribe();
    this.citiesSubscription?.unsubscribe();
  }

  getAddress(id: number): void {
    this.addressSubscription = this.addressService.getAddress(id).subscribe(
      data => {
        console.log(data);
        this.currentAddress = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateAddress(): void {
    this.addressEditSubscription = this.addressService.updateAddress(this.currentAddress).subscribe(
      data => {
        console.log(data);
        this.addressService.getAddresses(this.addressFilter);
        this.goToAddressesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }  

  onAddAddress(): void {
    this.addressAddSubscription = this.addressService.addAddress(this.currentAddress).subscribe(
      data => {
        console.log(data);
        this.addressService.getAddresses(this.addressFilter);
        this.goToAddressesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToAddressesList() {
    this.router.navigate(['/addresses']);
  }

  getListOfCities(): void {
    this.citiesSubscription = this.cityService.getListOfCities().subscribe(
     data => {
       this.citiesList = data;
     },
     error => {
      this.apiError = error.error;
      console.log(this.apiError);
    }
   );
  }

}
