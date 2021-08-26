import { Component, OnInit } from '@angular/core';
import { Brand } from '../../../_models/brand.model';
import { BrandFilter } from '../../../_models/brand-filter.model';
import { Router, ActivatedRoute } from '@angular/router';
import { BrandsService } from '../../../_services/brands.service';
import { CountryService } from '../../../_services/country.service'
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-brands-update',
  templateUrl: './brands-update.component.html',
  styleUrls: ['./brands-update.component.css']
})
export class BrandsUpdateComponent implements OnInit {

  emptyBrand: Brand = {
    id: undefined,
    name: '',
    country: {
      id: undefined,
      name: '',
      code: '',
      isValid: true
    },
    isValid: undefined
  }

  brandFilter: BrandFilter = {
    name: '',
    countryName: '',
    isValid: '',
    page: 0,
    size: 10,
    sortBy: 'name',
    sortDirection: 'asc'
  }

  brandSubscription: Subscription | undefined;
  brandsEditSubscription: Subscription | undefined;
  brandsAddSubscription: Subscription | undefined;
  countriesSubscription: Subscription | undefined;

  constructor(private countryService: CountryService,public router: Router, private route: ActivatedRoute, private brandsService: BrandsService) { }

  ngOnInit(): void {
    if (this.route.snapshot.params.id !== undefined) {
      this.brandsService.getBrand(this.route.snapshot.params.id);
    }
  }

  ngOnDestroy(): void {
    this.brandSubscription?.unsubscribe();
    this.brandsEditSubscription?.unsubscribe();
    this.brandsAddSubscription?.unsubscribe();
    this.countriesSubscription?.unsubscribe();
  }

  onAddBrand(): void {
    this.brandsAddSubscription = this.brandsService.addBrand(this.emptyBrand).subscribe(
      (response: Brand) => {
        console.log(response);
        this.brandsService.getBrands(this.brandFilter);
        this.router.navigate(['/brands'])
      })
  }

  onUpdateBrand(): void {
    this.brandsEditSubscription = this.brandsService.updateBrand(this.emptyBrand).subscribe(
      data => {
        console.log(data);
        this.brandsService.getBrands(this.brandFilter);
        this.router.navigate(['/brands']);
      })
  }

  getBrand(id: number): void {
    this.brandSubscription = this.brandsService.getBrand(id).subscribe(
      (response: Brand) => {
        console.log(response);
        this.emptyBrand = response;
      })
  }

}
