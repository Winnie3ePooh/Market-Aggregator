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
  forFinding: string;
  foundItems: EbayItem[] = [];
  location: string;
  pageNumber = 1;
  private route;

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
