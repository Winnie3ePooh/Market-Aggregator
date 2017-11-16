import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CountryPickerComponent } from './country-picker/country-picker.component';
import { CountryService } from './country-picker/country.service';
import { MainService } from './main-page/main.service';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { MainPageComponent } from './main-page/main-page.component';

import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { JsonpModule } from '@angular/http';
import {PageService} from './page.service';
import { ItemDetailsComponent } from './item-details/item-details.component';
import {NgxGalleryModule} from 'ngx-gallery';


@NgModule({
  declarations: [
    AppComponent,
    CountryPickerComponent,
    MainPageComponent,
    ItemDetailsComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    AppRoutingModule,
    InfiniteScrollModule,
    NgxGalleryModule
  ],
  providers: [
    CountryService,
    MainService,
    PageService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
