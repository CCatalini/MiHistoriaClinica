import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupPatientComponent} from "./pages/patient/signup-patient/signup-patient.component";
import {SignupMedicComponent} from "./pages/medic/signup-medic/signup-medic.component";
import {LoginPatientComponent} from "./pages/patient/login-patient/login-patient.component";
import {LoginMedicComponent} from "./pages/medic/login-medic/login-medic.component";
import {HomeComponent} from "./pages/home/home.component";
import {AddMedicineComponent} from "./pages/medic/add-medicine/add-medicine.component";
import {AddAnalysisComponent} from "./pages/medic/add-analysis/add-analysis.component";

const routes: Routes = [
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
      path:'home',
      component:HomeComponent,
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
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
