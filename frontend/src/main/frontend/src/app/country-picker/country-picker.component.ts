import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Country } from '../models/Country';
import { CountryService } from './country.service';
import {MainService} from '../main-page/main.service';

@Component({
  selector: 'app-country-picker',
  templateUrl: './country-picker.component.html',
  styleUrls: ['./country-picker.component.css']
})
export class CountryPickerComponent implements OnInit {

  term: string;
  countries: Country[];
  selectedCountry: Country;

  constructor(
    private countryService: CountryService,
    private router: Router,
    private mainService: MainService) { }

  getCountries(): void {
    this.countryService
      .getRegions()
      .then(countries => this.countries = countries);
  }

  goToMainPage(): void {
    this.mainService.setLocalKeyword('');
    console.log((this.selectedCountry));
    // if (this.selectedCountry === {}) {
    //   this.selectedCountry.name = 'WW';
    // };
    this.router.navigate(['', this.selectedCountry.name]);
  }

  ngOnInit() {
    this.getCountries();
  }

}
