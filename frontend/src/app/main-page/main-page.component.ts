import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

import { MainService } from './main.service';

import 'rxjs/add/operator/map';

import {EbayItem} from '../models/EbayItem';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
  private forFinding: string;
  private foundItems: EbayItem[] = [];
  private location: string;
  private pageNumber = 1;
  private route;
  currCategory = 1;
  categories = [
    { 'name' : 'All',
      'id' : 1 },
    { 'name' : 'Electronics',
      'id' : 2 },
  ];
  filterarray = [
    {'name': 'MaxPrice',
      'value': '25',
      'paramName': 'Currency',
      'paramValue': 'USD'},
    {'name': 'FreeShippingOnly',
      'value': 'true',
      'paramName': '',
      'paramValue': ''},
    {'name': 'ListingType',
      'value': ['AuctionWithBIN', 'FixedPrice'],
      'paramName': '',
      'paramValue': ''},
  ];

  constructor(
    private mainService: MainService,
    private activateRoute: ActivatedRoute) { this.route = activateRoute; }

  findIt(): void {
    this.foundItems = [];
    this.mainService.getResults(this.forFinding, this.pageNumber, this.location)
      .subscribe((data) => this.foundItems = data);
  }

  loadMore(): void {
    this.pageNumber += 1;
    this.mainService.getResults(this.forFinding, this.pageNumber, this.location)
      .subscribe((data) => this.foundItems.push.apply(this.foundItems, data));
  }

  ngOnInit(): void {
    this.route.params
      .map(params => params.location)
      .subscribe((location) => {
        this.location = location;
      });
  }
}
