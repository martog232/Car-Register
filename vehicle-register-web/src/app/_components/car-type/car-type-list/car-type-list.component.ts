import { Component, OnInit } from '@angular/core';
import { CarType } from '../../../_models/car-type.model';
import { CarTypeService } from '../../../_services/car-type.service';

@Component({
  selector: 'app-car-type-list',
  templateUrl: './car-type-list.component.html',
  styleUrls: ['./car-type-list.component.css']
})
export class CarTypeListComponent implements OnInit {
  carTypeList:CarType[]=[];
  carType$=this.carTypeService.getCarTypeList();
  

changeIsValid(carType: CarType){
  carType.isValid = !carType.isValid;
  this.carTypeService.changeIsValid(carType).subscribe((response:CarType)=>console.log);
}

  constructor(private carTypeService: CarTypeService) { }

  ngOnInit(): void {
    this.reloadData
  }
  reloadData() {
    this.carType$ ;
  }
}

