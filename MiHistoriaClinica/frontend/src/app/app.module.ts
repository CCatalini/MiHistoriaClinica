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
import { MedicinesListPatientComponent } from './pages/lists/medicines-list-patient/medicines-list-patient.component';
import { MatListModule } from '@angular/material/list';
import { GenerateLinkCodeComponent} from "./pages/patient/generate-link-code/generate-link-code.component";
import { MedicinesListMedicComponent } from './pages/lists/medicines-list-medic/medicines-list-medic.component';
import { AnalysisListPatientComponent } from './pages/lists/analysis-list-patient/analysis-list-patient.component';
import { MedicalHistoryListComponent } from "./pages/lists/medical-history-list-patient/medical-history-list.component";
import { MedicalHistoryListMedicComponent } from './pages/lists/medical-history-list-medic/medical-history-list-medic.component';
import { AnalysisListMedicComponent } from './pages/lists/analysis-list-medic/analysis-list-medic.component';
import { AppointmentsListMedicComponent } from './pages/lists/appointments-list-medic/appointments-list-medic.component';
import { AppointmentsListPatientComponent } from './pages/lists/appointments-list-patient/appointments-list-patient.component';
import { EditMedicalHistoryComponent } from './pages/medic/edit-medical-history/edit-medical-history.component';
import { AddTurnoComponent } from './pages/patient/add-turno/add-turno.component';
import { AllMedicsListComponent } from './pages/lists/all-medics-list/all-medics-list.component';
import { TurnosListComponent } from './pages/lists/turnos-list/turnos-list.component';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MedicCalendarComponent } from './pages/medic/medic-calendar/medic-calendar.component';
import { FullCalendarModule } from '@fullcalendar/angular';
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
    MedicinesListPatientComponent,
    GenerateLinkCodeComponent,
    MedicinesListMedicComponent,
    AnalysisListPatientComponent,
    MedicalHistoryListComponent,
    MedicalHistoryListMedicComponent,
    AnalysisListMedicComponent,
    AppointmentsListMedicComponent,
    AppointmentsListPatientComponent,
    EditMedicalHistoryComponent,
    AddTurnoComponent,
    AllMedicsListComponent,
    TurnosListComponent,
    MedicCalendarComponent,
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
        MatIconModule,
        MatListModule,
        MatSelectModule,
        MatOptionModule,
        FullCalendarModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
