import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Brand } from '../../app/_models/brand.model'
import { BrandFilter } from '../_models/brand-filter.model'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class BrandsService {

  private brandUrl = 'http://localhost:8080/vehicle-register-ws/brands';

  constructor(private http: HttpClient) { }

  public getBrands(brandFilter: BrandFilter): Observable<any> {
    let params = new HttpParams()
      .set('name', brandFilter.name)
      .set('countryName', brandFilter.countryName)
      .set('isValid', brandFilter.isValid)
      .set('page', brandFilter.page)
      .set('size', brandFilter.size)
      .set('sortBy', brandFilter.sortBy)
      .set('sortDirection', brandFilter.sortDirection);
    return this.http.get<any>(this.brandUrl + '/paging', { params: params })
  }

  public getListOfBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(this.brandUrl);
  }

  public getBrand(id: number): Observable<Brand> {
    return this.http.get<Brand>(`${this.brandUrl}/${id}`)
  }

  public addBrand(brand: Brand): Observable<Brand> {
    return this.http.post<Brand>(this.brandUrl, brand);
  }

  public updateBrand(brand: Brand): Observable<Brand> {
    return this.http.put<Brand>(`${this.brandUrl}/${brand.id}`, brand)
  }

  changeIsValid(brand: Brand): Observable<any> {
    return this.http.put(`${this.brandUrl}/change-is-valid/${brand.id}`, brand);
  }
}
