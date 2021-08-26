import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { CountryFilter } from 'src/app/_models/country-filter.model';
import { Country } from 'src/app/_models/country.model';
import { CountryService } from 'src/app/_services/country.service';

@Component({
  selector: 'app-country-list',
  templateUrl: './country-list.component.html',
  styleUrls: ['./country-list.component.css']
})
export class CountryListComponent implements OnInit, OnDestroy {

  countries: Country[] = [];
  countryFilter: CountryFilter = {
    name: '',
    code: '',
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

  countriesListSubscription: Subscription | undefined = undefined;
  
  constructor(private countryService: CountryService) { }
  
  ngOnInit() {
    this.getCountries();
  }

  ngOnDestroy(): void {
    this.countriesListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentCountryFilter: CountryFilter): void{
    this.countryService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.countryFilter.size = currentCountryFilter.size;
        this.countryFilter.page = currentCountryFilter.page - 1;
        this.countryFilter.name = currentCountryFilter.name;
        this.countryFilter.code = currentCountryFilter.code;
        this.countryFilter.isValid = currentCountryFilter.isValid;
        this.getCountries();
        currentCountryFilter.page = this.countryFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onChange(currentSize: number) {
    this.countryFilter.size = currentSize;
    this.countryFilter.page = 0;
    this.getCountries();
  }

  onPageChange(currentPage: number) {
    this.countryFilter.page = currentPage - 1;
    this.getCountries();
    this.countryFilter.page = currentPage;
  }

  onSearch(currentCountryFilter: CountryFilter) {
    this.countryFilter.size = currentCountryFilter.size;
    this.countryFilter.page = 0;
    this.countryFilter.name = currentCountryFilter.name;
    this.countryFilter.code = currentCountryFilter.code;
    this.countryFilter.isValid = currentCountryFilter.isValid;
    this.getCountries();
  }

  onChangeSort(currentCountryFilter: CountryFilter) {
    this.countryFilter.sortBy = currentCountryFilter.sortBy;
    this.countryFilter.page = 0;
    this.getCountries();
  }

  getCountries(): void {
    this.countriesListSubscription = this.countryService.getCountries(this.countryFilter).subscribe(
     (response: any) => {
       this.countries = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }

}
