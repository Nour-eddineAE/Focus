import { ErrorHandler, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { OverlayModule } from '@angular/cdk/overlay';
import { CdkMenuModule } from '@angular/cdk/menu';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import * as Sentry from '@sentry/angular';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidenavComponent } from './sidenav/sidenav.component';
import { BodyComponent } from './body/body.component';
import { MediaComponent } from './media/media.component';
import { PagesComponent } from './pages/pages.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SublevelMenuComponent } from './sidenav/sublevel-menu.component';
import { HeaderComponent } from './header/header.component';
import { ResetPasswordComponent } from './authentication/reset-password/reset-password.component';
import { LoginComponent } from './authentication/login/login.component';
import { SignupComponent } from './authentication/signup/signup.component';
import { UserTemplateComponent } from './user-template/user-template.component';

@NgModule({
  declarations: [
    AppComponent,
    SidenavComponent,
    BodyComponent,
    MediaComponent,
    PagesComponent,
    StatisticsComponent,
    DashboardComponent,
    SublevelMenuComponent,
    HeaderComponent,
    LoginComponent,
    SignupComponent,
    UserTemplateComponent,
    ResetPasswordComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    OverlayModule,
    CdkMenuModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [
    {
      provide: ErrorHandler,
      useValue: Sentry.createErrorHandler({
        showDialog: true,
      }),
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
