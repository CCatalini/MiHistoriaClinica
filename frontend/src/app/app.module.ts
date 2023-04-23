import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {HttpClientModule} from "@angular/common/http";
import {HomeComponent} from "./pages/home/home.component";
import {SignupMedicComponent} from './pages/medic/signup-medic/signup-medic.component';
import {SignupPatientComponent} from './pages/patient/signup-patient/signup-patient.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatToolbarModule} from "@angular/material/toolbar";
import {AppRoutingModule} from "./app-routing.module";
import { LoginPatientComponent } from './pages/patient/login-patient/login-patient.component';
import { LoginMedicComponent } from './pages/medic/login-medic/login-medic.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SignupPatientComponent,
    HomeComponent,
    SignupMedicComponent,
    LoginPatientComponent,
    LoginMedicComponent,
  ],
    imports: [
        BrowserModule,
        RouterModule,
        FormsModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatToolbarModule,
        AppRoutingModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
