import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddressAddEditComponent } from './_components/address-add-edit/address-add-edit.component';
import { AddressListComponent } from './_components/address-list/address-list.component';
import { CityAddEditComponent } from './_components/city-add-edit/city-add-edit.component';
import { CityListComponent } from './_components/city-list/city-list.component';
import { CountryAddEditComponent } from './_components/country-add-edit/country-add-edit.component';
import { CountryListComponent } from './_components/country-list/country-list.component';
import { DriverAddEditComponent } from './_components/driver-add-edit/driver-add-edit.component';
import { DriverListComponent } from './_components/driver-list/driver-list.component';
import { HomeComponent } from './_components/home/home.component';
import { BrandListComponent} from './_components/brands/brand-list/brand-list.component';
import { BrandsUpdateComponent } from './_components/brands/brands-update/brands-update.component'
import { PageNotFoundComponent } from './_components/page-not-found/page-not-found.component';
import { BookingComponent } from './_components/booking/booking.component';
import { FuelTypeListComponent} from './_components/fuel-type/fuel-type-list/fuel-type-list.component'
import { FuelTypeUpdateComponent } from './_components/fuel-type/fuel-type-update/fuel-type-update.component';
import { ServiceStationAddEditComponent } from './_components/service-station-add-edit/service-station-add-edit.component';
import { ServiceStationListComponent } from './_components/service-station-list/service-station-list.component';
import { ServiceTypeAddEditComponent } from './_components/service-type-add-edit/service-type-add-edit.component';
import { ServiceTypeListComponent } from './_components/service-type-list/service-type-list.component';
import { CarTypeListComponent } from './_components/car-type/car-type-list/car-type-list.component';
import {CarTypeUpdateComponent} from './_components/car-type/car-type-update/car-type-update.component';
import {VehicleListComponent} from './_components/vehicle-list/vehicle-list.component';
import {VehicleAddEditComponent} from './_components/vehicle-add-edit/vehicle-add-edit.component'

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'fuel-types', component: FuelTypeListComponent},
  { path: 'fuel-types/update/:id',component:FuelTypeUpdateComponent},
  { path: 'fuel-types/add',component:FuelTypeUpdateComponent}, 
  { path: 'countries', component: CountryListComponent },
  { path: 'countries/add', component: CountryAddEditComponent },
  { path: 'countries/update/:id', component: CountryAddEditComponent },
  { path: 'brands', component: BrandListComponent },
  { path: 'brands/update/:id', component:BrandsUpdateComponent},
  { path: 'brands/add', component:BrandsUpdateComponent},
  { path: 'cities', component: CityListComponent },
  { path: 'cities/add', component: CityAddEditComponent },
  { path: 'cities/update/:id', component: CityAddEditComponent },
  { path: 'addresses', component: AddressListComponent },
  { path: 'addresses/add', component: AddressAddEditComponent },
  { path: 'addresses/update/:id', component: AddressAddEditComponent },
  { path: 'service-stations', component: ServiceStationListComponent },
  { path: 'service-stations/add', component: ServiceStationAddEditComponent },
  { path: 'service-stations/update/:id', component: ServiceStationAddEditComponent },
  { path: 'service-types', component: ServiceTypeListComponent },
  { path: 'service-types/add', component: ServiceTypeAddEditComponent },
  { path: 'service-types/update/:id', component: ServiceTypeAddEditComponent },
  { path: 'drivers', component: DriverListComponent },
  { path: 'drivers/add', component: DriverAddEditComponent },
  { path: 'drivers/update/:id', component: DriverAddEditComponent },
  { path: 'service-stations-services', component: BookingComponent },
  { path: 'car-types', component:CarTypeListComponent},
  { path: 'car-types/update/:id', component: CarTypeUpdateComponent},
  { path: 'car-types/add', component: CarTypeUpdateComponent},
  { path: 'vehicles', component: VehicleListComponent},
  { path: 'vehicles/add', component: VehicleAddEditComponent},
  { path: 'vehicles/edit', component: VehicleAddEditComponent},
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
