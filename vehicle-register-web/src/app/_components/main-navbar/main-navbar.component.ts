import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';


@Component({
  selector: 'app-main-navbar',
  templateUrl: './main-navbar.component.html',
  styleUrls: ['./main-navbar.component.css']
})
export class MainNavbarComponent implements OnInit {

  isMenuCollapsed: boolean = true; 

  constructor(public translate: TranslateService) { 
    translate.addLangs(['EN', 'BG']);

    const browserLang = translate.getBrowserLang();
    translate.use(browserLang.match(/EN|BG/) ? browserLang : 'EN');
  }

  ngOnInit(): void {
  }

}
