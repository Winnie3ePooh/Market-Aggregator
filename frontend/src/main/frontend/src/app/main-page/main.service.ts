import { Injectable } from '@angular/core';
import { Http, Response, Headers, Jsonp} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {Shop} from '../models/Shop';
import {Category} from '../models/Category';

@Injectable()
export class MainService {

  private items;

  constructor(private http: Http, private  jsonp: Jsonp) { }

  getShops(region: string) {
    return this.http.get('/api/getShops?region=' + region).toPromise()
      .then(
        res => {
          return res.json();
        });
  };

  findByKeyword(category: string = '', keyword: string = '', page = 0) {
    return this.http.get('/api/findGoodsBySubcategory?category=' + category
    + '&keyword=' + keyword
    + '&page=' + page).toPromise()
      .then(
        res => {
          this.items = res.json().content;
          return res.json();
        }
      )
  }

  getRandomItems(category: string = 'All', page = 0) {
    return this.http.get('/api/getAll?page=' + page + '&category=' + category).toPromise()
      .then(
        res => {
          this.items = res.json().content;
          return res.json();
        }
      );
  }

  getItem(id: number) {
    return this.items.find(item => item.id == id);
  }

  setLocalPage(page: string) {
    localStorage.setItem('page', page );
  }

  getLocalPage() {
    if ( +localStorage.getItem('page') === 0 ) {
      return 1;
    } else return localStorage.getItem('page');
  }

  setLocalKeyword(keyword: string) {
    localStorage.setItem('keyword', keyword );
  }

  getLocalKeyword() {
    return localStorage.getItem('keyword');
  }

  getCategories() {
    return this.http.get('/api/getAllCategories').toPromise()
      .then( res => res.json() as Category[] );
  }

  setSelectedShops(shopsIds: number[]) {
    this.http.post('/api/setShops', shopsIds).toPromise()
      .then(res => console.log(''));
  }

  addNewRequest(newRequest) {
    this.http.post('/api/requests/addNewRequest', newRequest).toPromise()
      .then(res => console.log(res))
  };

}
