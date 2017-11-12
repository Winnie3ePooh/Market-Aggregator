import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Country } from '../models/Country';
import { CountryService } from './country.service';

@Component({
  selector: 'app-country-picker',
  templateUrl: './country-picker.component.html',
  styleUrls: ['./country-picker.component.css']
})
export class CountryPickerComponent implements OnInit {

  term: string;
  countries: Country[];
  selectedCountry: string;

  constructor(
    private countryService: CountryService,
    private router: Router) { }

  getCountries(): void {
    this.countryService
      .getCountries()
      .then(countries => this.countries = countries);
  }

  goToMainPage(): void {
    console.log((this.selectedCountry))
    if (typeof this.selectedCountry === 'undefined') {
      this.selectedCountry = 'US';
    };
    this.router.navigate(['', this.selectedCountry]);
  }

  ngOnInit() {
    this.getCountries();
  }

}
