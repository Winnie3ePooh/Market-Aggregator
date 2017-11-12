import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from '../app.component';
import { CountryPickerComponent } from '../country-picker/country-picker.component';
import {MainPageComponent} from '../main-page/main-page.component';

export const routes: Routes = [
  {
    path: '', redirectTo: '/pick', pathMatch: 'full'
  },
  {
    path: 'pick', component: CountryPickerComponent
  },
  {
    path: ':location', component: MainPageComponent
  }
];


@NgModule({
  imports: [ RouterModule.forRoot(routes, { enableTracing: true }) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
