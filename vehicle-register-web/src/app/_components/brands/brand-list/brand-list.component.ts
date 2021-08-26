import { Component, OnInit } from '@angular/core';
import { Brand } from '../../../_models/brand.model';
import { BrandFilter } from '../../../_models/brand-filter.model';
import { BrandsService } from '../../../_services/brands.service'
import { Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrls: ['./brand-list.component.css']
})
export class BrandListComponent implements OnInit {
 brands:Brand[]=[];
  brandFilter: BrandFilter = {
    name: '',
    countryName: '',
    isValid: '',
    page: 0,
    size: 10,
    sortBy: 'id',
    sortDirection: 'asc'
  }

  brandListSubscription: Subscription | undefined;

  constructor(private brandsService: BrandsService) { }

  brands$ = this.brandsService.getBrands(this.brandFilter);

  totalElements: number = 0;
  changeIsValid(brand: Brand) {
    brand.isValid = !brand.isValid;
    this.brandsService.changeIsValid(brand).subscribe((response: Brand) => console.log);
  }

  ngOnDestroy(): void {
    this.brandListSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.getBrands(this.brandFilter);
  }

  onChangeSort(currentBrandFilter: BrandFilter) {
    this.brandFilter.sortBy = currentBrandFilter.sortBy;
    this.brandFilter.page = 0;
    this.getBrands(this.brandFilter);
  }

  onChange(currentSize: number) {
    this.brandFilter.size = currentSize;
    this.brandFilter.page = 0;
    this.getBrands(this.brandFilter);
  }

  onSearch(currentBrandFilter: BrandFilter) {
    this.brandFilter.size = currentBrandFilter.size;
    this.brandFilter.page = 0;
    this.brandFilter.name = currentBrandFilter.name;
    this.brandFilter.countryName = currentBrandFilter.countryName;
    this.brandFilter.isValid = currentBrandFilter.isValid;
    this.getBrands(this.brandFilter);
  }

  onPageChange(currentPage: number) {
    this.brandFilter.page = currentPage - 1;
    this.getBrands(this.brandFilter);
    this.brandFilter.page = currentPage;
  }

  getBrands(brandFilter: BrandFilter): void {
    this.brandListSubscription = this.brandsService.getBrands(brandFilter).subscribe(
     (response: any) => {
       this.brands = response['content'];
       this.totalElements = response['totalElements'];
     },
     (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }
}
