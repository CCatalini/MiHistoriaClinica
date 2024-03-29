import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupPatientComponent} from "./pages/patient/signup-patient/signup-patient.component";
import {SignupMedicComponent} from "./pages/medic/signup-medic/signup-medic.component";
import {LoginPatientComponent} from "./pages/patient/login-patient/login-patient.component";
import {LoginMedicComponent} from "./pages/medic/login-medic/login-medic.component";
import {HomePatientComponent} from "./pages/patient/home-patient/home-patient.component";
import {AddMedicineComponent} from "./pages/medic/add-medicine/add-medicine.component";
import {AddAnalysisComponent} from "./pages/medic/add-analysis/add-analysis.component";
import {CreateMedicalHistoryComponent} from "./pages/medic/create-medical-history/create-medical-history.component";
import {HomeMedicComponent} from "./pages/medic/home-medic/home-medic.component";
import {HomeComponent} from "./pages/home/home.component";
import {AttendPatientComponent} from "./pages/medic/attend-patient/attend-patient.component";
import {PatientsListComponent} from "./pages/lists/patients-list/patients-list.component";
import {MedicalAppointmentComponent} from "./pages/medic/medical-appointment/medical-appointment.component";
import {LinkPatientComponent} from "./pages/medic/link-patient/link-patient.component";
import {MedicsListComponent} from "./pages/lists/medics-list/medics-list.component";
import {MedicinesListPatientComponent} from "./pages/lists/medicines-list-patient/medicines-list-patient.component";
import {GenerateLinkCodeComponent} from "./pages/patient/generate-link-code/generate-link-code.component";
import {MedicalHistoryListComponent} from "./pages/lists/medical-history-list-patient/medical-history-list.component";
import {MedicinesListMedicComponent} from "./pages/lists/medicines-list-medic/medicines-list-medic.component";
import {AnalysisListPatientComponent} from "./pages/lists/analysis-list-patient/analysis-list-patient.component";
import {MedicalHistoryListMedicComponent} from "./pages/lists/medical-history-list-medic/medical-history-list-medic.component";
import {AppointmentsListMedicComponent} from "./pages/lists/appointments-list-medic/appointments-list-medic.component";
import {AppointmentsListPatientComponent} from "./pages/lists/appointments-list-patient/appointments-list-patient.component";
import {AnalysisListMedicComponent} from "./pages/lists/analysis-list-medic/analysis-list-medic.component";
import {EditMedicalHistoryComponent} from "./pages/medic/edit-medical-history/edit-medical-history.component";
import {AllMedicsListComponent} from "./pages/lists/all-medics-list/all-medics-list.component";
import {AddTurnoComponent} from "./pages/patient/add-turno/add-turno.component";
import {TurnosListComponent} from "./pages/lists/turnos-list/turnos-list.component";
import {ConfirmationComponent} from "./pages/confirmation/confirmation.component";

const routes: Routes = [

    {
        path: 'confirmation',
        component: ConfirmationComponent
    },
    {
        path:'',
        component:HomeComponent,
        pathMatch:'full'
    },
    {
      path:'patient/home',
      component:HomePatientComponent,
      pathMatch:'full'
    },
    {
        path:'medic/home',
        component:HomeMedicComponent,
        pathMatch:'full'
    },
    {
        path:'patient/signup',
        component:SignupPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/signup',
        component:SignupMedicComponent,
        pathMatch:'full'
    },
    {
        path:'patient/login',
        component:LoginPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/login',
        component:LoginMedicComponent,
        pathMatch:'full'
    },
    {
        path:'medic/addMedicine',
        component:AddMedicineComponent,
        pathMatch:'full'
    },
    {
        path:'medic/addAnalysis',
        component:AddAnalysisComponent,
        pathMatch:'full'
    },
    {
        path:'medic/createMedicalHistory',
        component:CreateMedicalHistoryComponent,
        pathMatch:'full'
    },
    {
        path:'medic/attendPatient',
        component:AttendPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/patientsList',
        component:PatientsListComponent,
        pathMatch:'full'
    },
    {
        path:'medic/medicalAppointment',
        component:MedicalAppointmentComponent,
        pathMatch:'full'
    },
    {
        path:'medic/linkPatient',
        component:LinkPatientComponent,
        pathMatch:'full'
    },
    {
        path:'patient/medicsList',
        component:MedicsListComponent,
        pathMatch:'full'
    },
    {
        path:'patient/medicinesList',
        component:MedicinesListPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/medicinesList',
        component:MedicinesListMedicComponent,
        pathMatch:'full'
    },
    {
        path:'patient/analysisList',
        component:AnalysisListPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/analysisList',
        component:AnalysisListMedicComponent,
        pathMatch:'full'
    },
    {
        path:'patient/generateLinkCode',
        component:GenerateLinkCodeComponent,
        pathMatch:'full'
    },
    {
        path:'patient/medicalHistoryList',
        component:MedicalHistoryListComponent,
        pathMatch:'full'
    },
    {
        path:'medic/medicalHistoryList',
        component:MedicalHistoryListMedicComponent,
        pathMatch:'full'
    },
    {
        path:'medic/appointmentList',
        component:AppointmentsListMedicComponent,
        pathMatch:'full'
    },
    {
        path:'patient/appointmentList',
        component:AppointmentsListPatientComponent,
        pathMatch:'full'
    },
    {
        path:'medic/edit-medicalHistory',
        component:EditMedicalHistoryComponent,
        pathMatch:'full'
    },
    {
        path:'patient/all-medics-list',
        component:AllMedicsListComponent,
        pathMatch:'full'
    },
    {
        path:'patient/add-turno',
        component:AddTurnoComponent,
        pathMatch:'full'
    },
    {
        path:'patient/turnos-list',
        component:TurnosListComponent,
        pathMatch:'full'
    },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
