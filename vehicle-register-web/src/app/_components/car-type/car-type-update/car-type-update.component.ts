import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CarType } from '../../../_models/car-type.model';
import { CarTypeService } from '../../../_services/car-type.service';
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-car-type',
  templateUrl: './car-type-update.component.html',
  styleUrls: ['./car-type-update.component.css']
})
export class CarTypeUpdateComponent implements OnInit {
 emptyCarType:CarType={
  id:undefined,
  name:"",
  code:"",
  isValid:undefined
}

carTypeSubscription: Subscription | undefined;
carTypeEditSubscription: Subscription | undefined;
carTypeAddSubscription: Subscription | undefined;

  constructor(
    public router:Router,
    private route:ActivatedRoute,
    private carTypeService:CarTypeService) {}

  ngOnInit(): void {
    if(this.route.snapshot.params.id !== undefined) {
      this.getCarType(this.route.snapshot.params.id);
  }}

  ngOnDestroy(): void {
    this.carTypeSubscription?.unsubscribe();
    this.carTypeEditSubscription?.unsubscribe();
    this.carTypeAddSubscription?.unsubscribe();
  }

  getCarType(id: number): void {
    this.carTypeSubscription = this.carTypeService.getCarType(id).subscribe(
      (response: CarType) => {
        console.log(response);
        this.emptyCarType = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  onUpdateCarType(): void {
    this.carTypeEditSubscription = this.carTypeService.update(this.emptyCarType).subscribe(
      (response: CarType) => {
        console.log(response);
        this.carTypeService.getCarTypeList();
        this.router.navigate(['/car-types']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        }
    );
  }

  onAddCarType(): void {
    this.carTypeAddSubscription = this.carTypeService.add(this.emptyCarType).subscribe(
      (response: CarType) => {
        console.log(response);
        this.carTypeService.getCarTypeList();
        this.router.navigate(['/car-types']);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        }
    );
  }
}

