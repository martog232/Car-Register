import { Component } from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Vehicle Register';

  constructor(private translate: TranslateService) {
    translate.setDefaultLang('EN')};

    useLanguage(language: string): void {
      this.translate.use(language);
    }
}
