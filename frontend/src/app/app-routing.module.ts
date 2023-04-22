import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupPatientComponent} from "./pages/signup-patient/signup-patient.component";
import {SignupMedicComponent} from "./pages/signup-medic/signup-medic.component";
import {LoginPatientComponent} from "./pages/login-patient/login-patient.component";
import {LoginMedicComponent} from "./pages/login-medic/login-medic.component";

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
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
