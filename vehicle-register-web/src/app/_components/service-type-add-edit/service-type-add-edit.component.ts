import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { ServiceTypeFilter } from 'src/app/_models/service-type-filter.model';
import { ServiceType } from 'src/app/_models/service-type.model';
import { ServiceTypeService } from 'src/app/_services/service-type.service';

@Component({
  selector: 'app-service-type-add-edit',
  templateUrl: './service-type-add-edit.component.html',
  styleUrls: ['./service-type-add-edit.component.css']
})
export class ServiceTypeAddEditComponent implements OnInit {

  currentServiceType: ServiceType = {
    id: undefined,
    name: '',
    description: '',
    code: '',
    isValid: undefined
  }

  serviceTypeFilter: ServiceTypeFilter = {
    name: '',
    description: '',
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

  serviceTypeSubscription: Subscription | undefined;
  serviceTypeEditSubscription: Subscription | undefined;
  serviceTypeAddSubscription: Subscription | undefined;

  constructor(private serviceTypeService: ServiceTypeService, private router: Router, private rote: ActivatedRoute) { }

  ngOnInit(): void {
    if (this.rote.snapshot.params.id !== undefined) {
      this.getServiceType(this.rote.snapshot.params.id);
    }
  }

  ngOnDestroy(): void {
    this.serviceTypeSubscription?.unsubscribe();
    this.serviceTypeEditSubscription?.unsubscribe();
    this.serviceTypeAddSubscription?.unsubscribe();
  }

  getServiceType(id: number): void {
    this.serviceTypeSubscription = this.serviceTypeService.getServiceType(id).subscribe(
      data => {
        console.log(data);
        this.currentServiceType = data;
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  onUpdateServiceType(): void {
    this.serviceTypeEditSubscription = this.serviceTypeService.updateServiceType(this.currentServiceType).subscribe(
      data => {
        console.log(data);
        this.serviceTypeService.getServiceTypes(this.serviceTypeFilter);
        this.goToServiceTypeList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }  

  onAddServiceType(): void {
    this.serviceTypeAddSubscription = this.serviceTypeService.addServiceType(this.currentServiceType).subscribe(
      data => {
        console.log(data);
        this.serviceTypeService.getServiceTypes(this.serviceTypeFilter);
        this.goToServiceTypeList();
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    );
  }

  goToServiceTypeList() {
    this.router.navigate(['/service-types']);
  }

}
