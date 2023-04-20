import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignupPatientComponent} from "./pages/signup-patient/signup-patient.component";
import {SignupMedicComponent} from "./pages/signup-medic/signup-medic.component";

const routes: Routes = [
  {
      path:'signup-patient',
      component:SignupPatientComponent,
      pathMatch:'full'
  },
  {
      path:'signup-medic',
      component:SignupMedicComponent,
      pathMatch:'full'
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
