import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SignupPatientComponent} from "./pages/signup-patient/signup-patient.component";
import {HomeComponent} from "./pages/home/home.component";

const routes: Routes = [
  {
      path:'',
      component:HomeComponent,
      pathMatch:'full'
  },
  {
      path:'signup-pacient',
      component:SignupPatientComponent,
      pathMatch:'full'
  },

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
