import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts} from 'angular-2-dropdown-multiselect';

import { MainService } from './main.service';
import { PageService } from '../page.service';

declare var jquery: any;
declare var $: any;

import 'rxjs/add/operator/map';

import {Category} from 'app/models/Category';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})

export class MainPageComponent implements OnInit {
  private forFinding: string;
  private foundItems = [1,];
  private location: string;
  private pageNumber = 1;
  private route;
  private shopList: IMultiSelectOption[];
  private subcategories;
  pager: any = {};
  private totalElements;
  private categories: Category[];
  private selectedCategory: string;
  private selectedShops: number[];
  private newRequest = {email: '', name: '', keyword: ''};

  mySettings: IMultiSelectSettings = {
    fixedTitle: true,
    containerClasses: 'col-2 no-padding'
  };

  myTexts: IMultiSelectTexts = {
    defaultTitle: 'Shops'
  };

  constructor(
    private mainService: MainService,
    private activateRoute: ActivatedRoute,
    private pagerService: PageService ) { this.route = activateRoute;
  }

  setItems(res): void {
    this.foundItems = res.content;
    console.log(this.foundItems);
    this.totalElements = res.totalElements;
    this.setPage(res.number + 1);
  }

  getCategories() {
    this.mainService.getCategories()
      .then(res => this.categories = res);
  }

  findIt(): void {
    this.mainService.setLocalKeyword(this.forFinding);
    this.mainService.findByKeyword(this.selectedCategory, this.forFinding)
      .then(res => this.setItems(res));
  };

  getNextPageResults(page: number): void {
    if (typeof this.forFinding === 'undefined') {
      this.mainService.getRandomItems(page, this.selectedCategory).then(res => this.setItems(res));
    } else {
      this.mainService.findByKeyword(this.selectedCategory, this.forFinding, page)
        .then(res => this.setItems(res));
    }
  }

  setSelectedShops() {
    this.mainService.setSelectedShops(this.selectedShops);
  }

  onChange() {
    this.setSelectedShops();
  }

  setPage(page: number) {
    if (page < 1 || page > this.pager.totalPages) {
      return;
    }
    this.pager = this.pagerService.getPager(this.totalElements, page);
    this.mainService.setLocalPage(this.pager.currentPage);
  }

  openPopup() {
    $('.btn-circle, .btn-circle i.fa').toggleClass('active');
  }

  addNewRequest() {
    this.newRequest.name = $('#name').val();
    this.newRequest.email = $('#email').val();
    this.newRequest.keyword = this.forFinding;
    // this.mainService.addNewRequest(this.newRequest);
  };

  ngOnInit(): void {
    this.route.params
      .map(params => params.location)
      .subscribe((location) => {
        this.location = location;
      });
    console.log('asdasdasdasd');
    console.log(this.location);
    this.mainService.getShops(this.location).then(res => {
      this.shopList = res;
    });
    console.log(this.shopList);
    this.mainService.getRandomItems(+(this.mainService.getLocalPage()) - 1,
      this.selectedCategory,
      this.mainService.getLocalKeyword()
    )
      .then(res => this.setItems(res));
    this.getCategories();
  }
}
