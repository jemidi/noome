import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { NooMeSharedModule } from 'app/shared/shared.module';
import { NooMeCoreModule } from 'app/core/core.module';
import { NooMeAppRoutingModule } from './app-routing.module';
import { NooMeHomeModule } from './home/home.module';
import { NooMeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    NooMeSharedModule,
    NooMeCoreModule,
    NooMeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    NooMeEntityModule,
    NooMeAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class NooMeAppModule {}
