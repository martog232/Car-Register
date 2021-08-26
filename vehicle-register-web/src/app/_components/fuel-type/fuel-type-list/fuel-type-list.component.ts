import { Component, OnInit } from '@angular/core';
import { FuelTypeService} from 'src/app/_services/fuel-type.service';
import { FuelType } from 'src/app/_models/fuel-type.model'


@Component({
  selector: 'app-fuel-type-list',
  templateUrl: './fuel-type-list.component.html',
  styleUrls: ['./fuel-type-list.component.css']
})
export class FuelTypeListComponent implements OnInit {
  fuelType$=this.fuelTypeService.getFuelTypeList();
  

changeIsValid(fuelType: FuelType){
  fuelType.isValid = !fuelType.isValid;
  this.fuelTypeService.changeIsValid(fuelType).subscribe((response:FuelType)=>console.log);
}

  constructor(private fuelTypeService: FuelTypeService) { }

  ngOnInit(): void {
    this.reloadData
  }
  reloadData() {
    this.fuelType$ ;
  }
}

