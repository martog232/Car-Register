import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import { HttpClientModule,HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CountryListComponent } from './_components/country-list/country-list.component';
import { HomeComponent } from './_components/home/home.component';
import { MainNavbarComponent } from './_components/main-navbar/main-navbar.component';
import { PageNotFoundComponent } from './_components/page-not-found/page-not-found.component';
import { CountryAddEditComponent } from './_components/country-add-edit/country-add-edit.component';
import { BrandListComponent } from './_components/brands/brand-list/brand-list.component';
import { BrandsUpdateComponent } from './_components/brands/brands-update/brands-update.component';
import { BookingComponent } from './_components/booking/booking.component';
import { FuelTypeListComponent}  from './_components/fuel-type/fuel-type-list/fuel-type-list.component';
import { FuelTypeUpdateComponent } from './_components/fuel-type/fuel-type-update/fuel-type-update.component';
import { CityListComponent } from './_components/city-list/city-list.component';
import { CityAddEditComponent } from './_components/city-add-edit/city-add-edit.component';
import { AddressListComponent } from './_components/address-list/address-list.component';
import { AddressAddEditComponent } from './_components/address-add-edit/address-add-edit.component';
import { ServiceStationListComponent } from './_components/service-station-list/service-station-list.component';
import { ServiceStationAddEditComponent } from './_components/service-station-add-edit/service-station-add-edit.component';
import { ServiceTypeListComponent } from './_components/service-type-list/service-type-list.component';
import { ServiceTypeAddEditComponent } from './_components/service-type-add-edit/service-type-add-edit.component';
import { DriverListComponent } from './_components/driver-list/driver-list.component';
import { DriverAddEditComponent } from './_components/driver-add-edit/driver-add-edit.component';
import { CarTypeListComponent } from './_components/car-type/car-type-list/car-type-list.component';
import { CarTypeUpdateComponent } from './_components/car-type/car-type-update/car-type-update.component';
import { VehicleListComponent } from './_components/vehicle-list/vehicle-list.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MainNavbarComponent,
    PageNotFoundComponent,
    CountryListComponent,
    CountryAddEditComponent,
    BrandListComponent,
    BrandsUpdateComponent,
    BookingComponent,
    FuelTypeListComponent,
    FuelTypeUpdateComponent,
    CityListComponent,
    CityAddEditComponent,
    AddressListComponent,
    AddressAddEditComponent,
    ServiceStationListComponent,
    ServiceStationAddEditComponent,
    ServiceTypeListComponent,
    ServiceTypeAddEditComponent,
    DriverListComponent,
    DriverAddEditComponent,
    CarTypeListComponent,
    CarTypeUpdateComponent,
    VehicleListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
  })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
