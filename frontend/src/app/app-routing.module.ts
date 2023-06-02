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
import {MedicinesListComponent} from "./pages/lists/medicines-list/medicines-list.component";
import {GenerateLinkCodeComponent} from "./pages/patient/generate-link-code/generate-link-code.component";
import {MedicalHistoryListComponent} from "./pages/lists/medical-history-list/medical-history-list.component";

const routes: Routes = [
    {
        path:'',
        component:HomeComponent,
        pathMatch:'full'
    },
    {
      path:'home-patient',
      component:HomePatientComponent,
      pathMatch:'full'
  },
  {
      path:'home-medic',
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
      path:'medicine/getMedicinesList',
      component:MedicinesListComponent,
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
  { //todo ver
      path:'patient//medicines/:id/edit',
      component:MedicalHistoryListComponent,
      pathMatch:'full'
  },
  { //todo ver
      path:'patient/medicalHistoryList',
      component:MedicalHistoryListComponent,
      pathMatch:'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
