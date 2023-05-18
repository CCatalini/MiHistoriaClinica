import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { RouterModule } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { HttpClientModule } from "@angular/common/http";
import { HomePatientComponent } from "./pages/patient/home-patient/home-patient.component";
import { SignupMedicComponent } from './pages/medic/signup-medic/signup-medic.component';
import { SignupPatientComponent } from './pages/patient/signup-patient/signup-patient.component';
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { MatToolbarModule } from "@angular/material/toolbar";
import { AppRoutingModule } from "./app-routing.module";
import { LoginPatientComponent } from './pages/patient/login-patient/login-patient.component';
import { LoginMedicComponent } from './pages/medic/login-medic/login-medic.component';
import { AddMedicineComponent } from './pages/medic/add-medicine/add-medicine.component';
import { AddAnalysisComponent } from './pages/medic/add-analysis/add-analysis.component';
import { CreateMedicalHistoryComponent } from './pages/medic/create-medical-history/create-medical-history.component';
import { HomeMedicComponent } from './pages/medic/home-medic/home-medic.component';
import { MatIconModule } from '@angular/material/icon';
import { HomeComponent } from './pages/home/home.component';
import { AttendPatientComponent } from './pages/medic/attend-patient/attend-patient.component';
import { PatientsListComponent } from './pages/lists/patients-list/patients-list.component';
import { MedicalAppointmentComponent } from './pages/medic/medical-appointment/medical-appointment.component';
import { LinkPatientComponent } from './pages/medic/link-patient/link-patient.component';
import { MedicsListComponent } from './pages/lists/medics-list/medics-list.component';
import { MedicinesListComponent } from './pages/lists/medicines-list/medicines-list.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    SignupPatientComponent,
    HomePatientComponent,
    SignupMedicComponent,
    LoginPatientComponent,
    LoginMedicComponent,
    AddMedicineComponent,
    AddAnalysisComponent,
    CreateMedicalHistoryComponent,
    HomeMedicComponent,
    HomeComponent,
    AttendPatientComponent,
    PatientsListComponent,
    MedicalAppointmentComponent,
    LinkPatientComponent,
    MedicsListComponent,
    MedicinesListComponent,
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
        AppRoutingModule,
        MatIconModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
