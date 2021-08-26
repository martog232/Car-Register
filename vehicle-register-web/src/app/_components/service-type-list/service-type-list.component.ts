import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { APIError } from 'src/app/_models/apiError.model';
import { ServiceTypeFilter } from 'src/app/_models/service-type-filter.model';
import { ServiceType } from 'src/app/_models/service-type.model';
import { ServiceTypeService } from 'src/app/_services/service-type.service';

@Component({
  selector: 'app-service-type-list',
  templateUrl: './service-type-list.component.html',
  styleUrls: ['./service-type-list.component.css']
})
export class ServiceTypeListComponent implements OnInit {

 
  serviceTypes: ServiceType[] = [];
  serviceTypeFilter: ServiceTypeFilter = {
    name: '',
    description: '',
    code: '',
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

  serviceTypeListSubscription: Subscription | undefined;
  
  constructor(private serviceTypeService: ServiceTypeService) { }
  
  ngOnInit() {
    this.getServiceTypes(this.serviceTypeFilter);
  }

  ngOnDestroy(): void {
    this.serviceTypeListSubscription?.unsubscribe();
  }  
  
  changeIsValid(id: number | undefined, currentServiceTypeFilter: ServiceTypeFilter): void{
    this.serviceTypeService.changeIsValid(id).subscribe(
      data => {
        console.log(data);
        this.serviceTypeFilter.size = currentServiceTypeFilter.size;
        this.serviceTypeFilter.page = currentServiceTypeFilter.page - 1;
        this.serviceTypeFilter.name = currentServiceTypeFilter.name;
        this.serviceTypeFilter.description = currentServiceTypeFilter.description;
        this.serviceTypeFilter.code = currentServiceTypeFilter.code;
        this.serviceTypeFilter.isValid = currentServiceTypeFilter.isValid;
        this.getServiceTypes(this.serviceTypeFilter);
        currentServiceTypeFilter.page = this.serviceTypeFilter.page + 1;
        
      },
      error => {
        this.apiError = error.error;
        console.log(this.apiError);
      }
    )
  }

  onChange(currentSize: number) {
    this.serviceTypeFilter.size = currentSize;
    this.serviceTypeFilter.page = 0;
    this.getServiceTypes(this.serviceTypeFilter);
  }

  onPageChange(currentPage: number) {
    this.serviceTypeFilter.page = currentPage - 1;
    this.getServiceTypes(this.serviceTypeFilter);
    this.serviceTypeFilter.page = currentPage;
  }

  onSearch(currentServiceTypeFilter: ServiceTypeFilter) {
    this.serviceTypeFilter.size = currentServiceTypeFilter.size;
    this.serviceTypeFilter.page = 0;
    this.serviceTypeFilter.name = currentServiceTypeFilter.name;
    this.serviceTypeFilter.description = currentServiceTypeFilter.description;
    this.serviceTypeFilter.code = currentServiceTypeFilter.code;
    this.serviceTypeFilter.isValid = currentServiceTypeFilter.isValid;
    this.getServiceTypes(this.serviceTypeFilter);
  }

  onChangeSort(currentServiceTypeFilter: ServiceTypeFilter) {
    this.serviceTypeFilter.sortBy = currentServiceTypeFilter.sortBy;
    this.serviceTypeFilter.page = 0;
    this.getServiceTypes(this.serviceTypeFilter);
  }

  getServiceTypes(serviceTypeFilter: ServiceTypeFilter): void {
    this.serviceTypeListSubscription = this.serviceTypeService.getServiceTypes(serviceTypeFilter).subscribe(
     (response: any) => {
       this.serviceTypes = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
       alert(error.message);
     }
   );
  }

}
