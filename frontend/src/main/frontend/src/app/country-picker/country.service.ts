import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { Country } from '../models/Country';

@Injectable()
export class CountryService {

  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }

  // getCountries(): Promise<Country[]> {
  //   return this.http.get('assets/countries.json')
  //     .toPromise()
  //     .then(response => response.json() as Country[])
  //     .catch(this.handleError);
  // }
  //
  // private handleError(error: any): Promise<any> {
  //   console.error('An error occurred', error); // for demo purposes only
  //   return Promise.reject(error.message || error);
  // }

  getRegions() {
    return this.http.get('/api/getAllRegions').toPromise()
      .then(res => {
        return res.json()
      });
  }

}
