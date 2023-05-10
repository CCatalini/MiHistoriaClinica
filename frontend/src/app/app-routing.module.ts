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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
