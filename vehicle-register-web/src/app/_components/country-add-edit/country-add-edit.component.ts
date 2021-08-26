import { APIError } from 'src/app/_models/apiError.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CountryFilter } from 'src/app/_models/country-filter.model';
import { Country } from 'src/app/_models/country.model';
import { CountryService } from 'src/app/_services/country.service';

@Component({
  selector: 'app-country-add-edit',
  templateUrl: './country-add-edit.component.html',
  styleUrls: ['./country-add-edit.component.css']
})
export class CountryAddEditComponent implements OnInit {
  currentCountry: Country = {
    id: undefined,
    name: '',
    code: '',
    isValid: undefined
  }

  countryFilter: CountryFilter = {
    name: '',
    code: '',
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

  countrySubscription: Subscription | undefined = undefined;
  countriesEditSubscription: Subscription | undefined = undefined;
  countriesAddSubscription: Subscription | undefined = undefined;

  constructor(private countryService: CountryService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getCountry(this.rote.snapshot.params.id);
    }
  }

  ngOnDestroy(): void {
    this.countrySubscription?.unsubscribe();
    this.countriesEditSubscription?.unsubscribe();
    this.countriesAddSubscription?.unsubscribe();
  }

  getCountry(id: number): void {
    this.countrySubscription = this.countryService.getCountry(id).subscribe(
      data => {
        console.log(data);
        this.currentCountry = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateCountry(): void {
    this.countriesEditSubscription = this.countryService.updateCountry(this.currentCountry).subscribe(
      data => {
        console.log(data);
        this.countryService.getCountries(this.countryFilter);
        this.goToCountriesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onAddCountry(): void {
    this.countriesAddSubscription = this.countryService.addCountry(this.currentCountry).subscribe(
      data => {
        console.log(data);
        this.countryService.getCountries(this.countryFilter);
        this.goToCountriesList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToCountriesList() {
    this.router.navigate(['/countries']);
  }

}
