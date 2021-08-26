import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FuelType } from '../../../_models/fuel-type.model';
import { FuelTypeService } from '../../../_services/fuel-type.service';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-fuel-type',
  templateUrl: './fuel-type-update.component.html',
  styleUrls: ['./fuel-type-update.component.css']
})
export class FuelTypeUpdateComponent implements OnInit {
 emptyFuelType:FuelType={
  id:undefined,
  name:"",
  code:"",
  isValid:undefined
}

fuelTypeSubscription: Subscription | undefined = undefined;
fuelTypeEditSubscription: Subscription | undefined = undefined;
fuelTypeAddSubscription: Subscription | undefined = undefined;

  constructor(
    public router:Router,
    private route:ActivatedRoute,
    private fuelTypeService:FuelTypeService) {}

  ngOnInit(): void {
    if(this.route.snapshot.params.id !== undefined) {
      this.getFuelType(this.route.snapshot.params.id);
  }}

  ngOnDestroy(): void {
    this.fuelTypeSubscription?.unsubscribe();
    this.fuelTypeEditSubscription?.unsubscribe();
    this.fuelTypeAddSubscription?.unsubscribe();
  }

  getFuelType(id: number): void {
    this.fuelTypeSubscription = this.fuelTypeService.getFuelType(id).subscribe(
      (response: FuelType) => {
        console.log(response);
        this.emptyFuelType = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  onUpdateFuelType(): void {
    this.fuelTypeEditSubscription = this.fuelTypeService.update(this.emptyFuelType).subscribe(
      (response: FuelType) => {
        console.log(response);
        this.fuelTypeService.getFuelTypeList();
        this.router.navigate(['/fuel-types']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        }
    );
  }

  onAddFuelType(): void {
    this.fuelTypeAddSubscription = this.fuelTypeService.add(this.emptyFuelType).subscribe(
      (response: FuelType) => {
        console.log(response);
        this.fuelTypeService.getFuelTypeList();
        this.router.navigate(['/fuel-types']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        }
    );
  }
}

