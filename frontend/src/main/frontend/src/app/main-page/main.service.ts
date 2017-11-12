import { Injectable } from '@angular/core';
import { Http, Response, Headers, Jsonp} from '@angular/http';

import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {Shop} from '../models/Shop';

@Injectable()
export class MainService {

  constructor(private http: Http, private  jsonp: Jsonp) { }

  getShops(region: string) {
    return this.http.get('/api/getShops?region=' + region).toPromise()
      .then(
        res => res.json() as Shop[]
      );
  };

  findByKeyword(keyword: string, page = 0) {
    return this.http.get('/api/findByKeyword?keyword=' + keyword.split(' ').join('%20')
    + '&page=' + page).toPromise()
      .then(
        res => res.json()
      )
  }

  getRandomItems(page = 0) {
    return this.http.get('/api/getAll?page=' + page).toPromise()
      .then(
        res => res.json()
      );
  }

}
