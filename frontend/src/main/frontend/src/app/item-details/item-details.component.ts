import { Component, OnInit } from '@angular/core';
import {EbayItem} from '../models/EbayItem';
import {MainService} from '../main-page/main.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {NgxGalleryImage, NgxGalleryOptions} from 'ngx-gallery';
import {Location} from '@angular/common';

@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.component.html',
  styleUrls: ['./item-details.component.css']
})
export class ItemDetailsComponent implements OnInit {

  item;

  galleryOptions: NgxGalleryOptions[];
  galleryImages: NgxGalleryImage[];

  constructor(
    private mainService: MainService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.route.params
      .map(params => params.id)
      .subscribe((id) => {
        this.item  = this.mainService.getItem(id);
      });
    this.galleryOptions = [
      {
        width: '100%',
        height: '400px',
        thumbnailsColumns: 4,
        imageSize: 'contain'
      }
    ];
    this.galleryImages = this.item.images.map(img => {
      return {
        small: img.imageURL,
        medium: img.imageURL,
        big: img.imageURL
      }
    });
    console.log(this.galleryOptions);
  }

  goBack() {
    this.location.back();
  }

}
