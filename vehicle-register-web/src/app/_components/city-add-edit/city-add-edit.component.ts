import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { CityFilter } from 'src/app/_models/city-filter.model';
import { City } from 'src/app/_models/city.model';
import { Country } from 'src/app/_models/country.model';
import { CityService } from 'src/app/_services/city.service';
import { CountryService } from 'src/app/_services/country.service';

@Component({
  selector: 'app-city-add-edit',
  templateUrl: './city-add-edit.component.html',
  styleUrls: ['./city-add-edit.component.css']
})
export class CityAddEditComponent implements OnInit {

  currentCity: City = {
    id: undefined,
    name: '',
    country: {
      id: -1,
      name: '',
      code: '',
      isValid: true
    },
    isValid: undefined
  }

  cityFilter: CityFilter = {
    name: '',
    countryName: '',
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

  countriesList: Country[] = [];

  citySubscription: Subscription | undefined;
  citiesEditSubscription: Subscription | undefined;
  citiesAddSubscription: Subscription | undefined;
  countriesSubscription: Subscription | undefined;

  constructor(private cityService: CityService, private countryService: CountryService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getCity(this.rote.snapshot.params.id);
    }
    this.getListOfCountries();
  }

  ngOnDestroy(): void {
    this.citySubscription?.unsubscribe();
    this.citiesEditSubscription?.unsubscribe();
    this.citiesAddSubscription?.unsubscribe();
    this.countriesSubscription?.unsubscribe();
  }

  getCity(id: number): void {
    this.citySubscription = this.cityService.getCity(id).subscribe(
      data => {
        console.log(data);
        this.currentCity = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateCity(): void {
    this.citiesEditSubscription = this.cityService.updateCity(this.currentCity).subscribe(
      data => {
        console.log(data);
        this.cityService.getCities(this.cityFilter);
        this.goToCitiesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }  

  onAddCity(): void {
    this.citiesAddSubscription = this.cityService.addCity(this.currentCity).subscribe(
      data => {
        console.log(data);
        this.cityService.getCities(this.cityFilter);
        this.goToCitiesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToCitiesList() {
    this.router.navigate(['/cities']);
  }

  getListOfCountries(): void {
    this.countriesSubscription = this.countryService.getListOfCountries().subscribe(
     data => {
       this.countriesList = data;
     },
     error => {
      this.apiError = error.error;
      console.log(this.apiError);
    }
   );
  }
}
