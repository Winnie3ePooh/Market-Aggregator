import { Injectable } from '@angular/core';
import { Http, Response, Headers, Jsonp} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {Shop} from '../models/Shop';

@Injectable()
export class MainService {

  private items;

  constructor(private http: Http, private  jsonp: Jsonp) { }

  getShops(region: string) {
    return this.http.get('/api/getShops?region=' + region).toPromise()
      .then(
        res => res.json() as Shop[]
      );
  };

  findByKeyword(keyword: string, page = 0) {
    return this.http.get('/api/findGoods?keyword=' + keyword.split(' ').join('%20')
    + '&page=' + page).toPromise()
      .then(
        res => {
          this.items = res.json().content;
          return res.json();
        }
      )
  }

  getRandomItems(page = 0, keyword = '') {
    return this.http.get('/api/findGoods?keyword=' + keyword + '&page=' + page).toPromise()
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
    return localStorage.getItem('page');
  }

  setLocalKeyword(keyword: string) {
    localStorage.setItem('keyword', keyword );
  }

  getLocalKeyword() {
    return localStorage.getItem('keyword');
  }

}
