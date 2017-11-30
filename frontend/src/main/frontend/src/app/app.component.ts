import {Component, OnInit} from '@angular/core';
import {CountryService} from './country-picker/country.service';
import {Router} from '@angular/router';
import {MainService} from './main-page/main.service';
import {Country} from './models/Country';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  countries = [];
  selectedCountry: Country;
  title = 'app';

  constructor(
    private countryService: CountryService,
    private router: Router,
    private mainService: MainService
  ) {}

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
    this.router.navigate(['', this.selectedCountry.name])
  }

  ngOnInit(): void {
    this.getCountries();
  }

}
