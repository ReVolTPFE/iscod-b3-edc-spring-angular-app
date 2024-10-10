import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ProjectComponent } from './pages/project/project.component';
import {HttpClientModule} from "@angular/common/http";
import {HomeComponent} from "./pages/home/home.component";
import {ErrorComponent} from "./pages/error/error.component";
import {LoginTemplateDrivenComponent} from "./pages/login-template-driven/login-template-driven.component";
import { HeaderComponent } from './components/header/header.component';
import { ProjectDetailComponent } from './pages/project-detail/project-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    ProjectComponent,
    HomeComponent,
    ErrorComponent,
    LoginTemplateDrivenComponent,
    HeaderComponent,
    ProjectDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
