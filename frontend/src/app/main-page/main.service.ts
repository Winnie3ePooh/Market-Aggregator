import { Injectable } from '@angular/core';
import { Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import { EbayItem } from '../models/EbayItem';

@Injectable()
export class MainService {

  resl: string;
  ebayUrl = 'http://svcs.ebay.com/services/search/FindingService/v1';
  test = {};
  constructor(private http: Http) { }

  getResults(forFinding: string, nextPage: number, location: string): Promise<EbayItem[]> {
    this.ebayUrl += '?OPERATION-NAME=findItemsByKeywords';
    this.ebayUrl += '&SERVICE-VERSION=1.0.0';
    this.ebayUrl += '&SECURITY-APPNAME=MaximSte-Salesagg-PRD-5132041a0-a837000d';
    // this.ebayUrl += '&GLOBAL-ID=EBAY-US';
    this.ebayUrl += '&RESPONSE-DATA-FORMAT=JSON';
    // this.ebayUrl += '&callback=_cb_findItemsByKeywords';
    this.ebayUrl += '&REST-PAYLOAD';
    this.ebayUrl += '&keywords=' + forFinding.split(' ').join('%20');
    this.ebayUrl += '&paginationInput.entriesPerPage=15';
    this.ebayUrl += '&paginationInput.pageNumber=' + nextPage;
    return this.http.get(this.ebayUrl)
      .toPromise()
      .then(response => response.json().findItemsByKeywordsResponse[0].searchResult[0].item)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
