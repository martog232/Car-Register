import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { CityFilter } from 'src/app/_models/city-filter.model';
import { City } from 'src/app/_models/city.model';
import { CityService } from 'src/app/_services/city.service';

@Component({
  selector: 'app-city-list',
  templateUrl: './city-list.component.html',
  styleUrls: ['./city-list.component.css']
})
export class CityListComponent implements OnInit {

  cities: City[] = [];
  cityFilter: CityFilter = {
    name: '',
    countryName: '',
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

  citiesListSubscription: Subscription | undefined;
  
  constructor(private cityService: CityService) { }
  
  ngOnInit() {
    this.getCities(this.cityFilter);
  }

  ngOnDestroy(): void {
    this.citiesListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentCityFilter: CityFilter): void{
    this.cityService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.cityFilter.size = currentCityFilter.size;
        this.cityFilter.page = currentCityFilter.page - 1;
        this.cityFilter.name = currentCityFilter.name;
        this.cityFilter.countryName = currentCityFilter.countryName;
        this.cityFilter.isValid = currentCityFilter.isValid;
        this.getCities(this.cityFilter);
        currentCityFilter.page = this.cityFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    )
  }

  onChange(currentSize: number) {
    this.cityFilter.size = currentSize;
    this.cityFilter.page = 0;
    this.getCities(this.cityFilter);
  }

  onPageChange(currentPage: number) {
    this.cityFilter.page = currentPage - 1;
    this.getCities(this.cityFilter);
    this.cityFilter.page = currentPage;
  }

  onSearch(currentCityFilter: CityFilter) {
    this.cityFilter.size = currentCityFilter.size;
    this.cityFilter.page = 0;
    this.cityFilter.name = currentCityFilter.name;
    this.cityFilter.countryName = currentCityFilter.countryName;
    this.cityFilter.isValid = currentCityFilter.isValid;
    this.getCities(this.cityFilter);
  }

  onChangeSort(currentCityFilter: CityFilter) {
    this.cityFilter.sortBy = currentCityFilter.sortBy;
    this.cityFilter.page = 0;
    this.getCities(this.cityFilter);
  }

  getCities(cityFilter: CityFilter): void {
    this.citiesListSubscription = this.cityService.getCities(cityFilter).subscribe(
     (response: any) => {
       this.cities = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }


}
