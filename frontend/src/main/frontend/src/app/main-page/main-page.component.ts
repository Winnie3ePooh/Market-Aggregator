import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';

import { MainService } from './main.service';
import { PageService } from '../page.service';

import 'rxjs/add/operator/map';

import {EbayItem} from '../models/EbayItem';
import {Shop} from '../models/Shop';

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
  private shopList: Shop[];
  pager: any = {};
  private totalElements;

  constructor(
    private mainService: MainService,
    private activateRoute: ActivatedRoute,
    private pagerService: PageService ) { this.route = activateRoute;
  }

  setItems(res): void {
    this.foundItems = res.content;
    this.totalElements = res.totalElements;
    this.setPage(res.number + 1);
  }

  findIt(): void {
    this.mainService.findByKeyword(this.forFinding)
      .then(res => this.setItems(res));
  };

  getNextPageResults(page: number): void {
    console.log(this.forFinding);
    if (typeof this.forFinding === 'undefined') {
      this.mainService.getRandomItems(page).then(res => this.setItems(res));
    } else {
      this.mainService.findByKeyword(this.forFinding, page)
        .then(res => this.setItems(res));
    }
  }

  ngOnInit(): void {
    this.route.params
      .map(params => params.location)
      .subscribe((location) => {
        this.location = location;
      });
    this.mainService.getShops(this.location).then(res => this.shopList = res);
    console.log(this.shopList);
    this.mainService.getRandomItems().then(res => this.setItems(res));
  }

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalPages) {
      return;
    }
    // get pager object from service
    this.pager = this.pagerService.getPager(this.totalElements, page);
  }
}
