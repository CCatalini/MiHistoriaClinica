import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SignupPacientComponent} from "./pages/signup-pacient/signup-pacient.component";
import {HomeComponent} from "./pages/home/home.component";

const routes: Routes = [
  {
      path:'',
      component:HomeComponent,
      pathMatch:'full'
  },
  {
      path:'signup-pacient',
      component:SignupPacientComponent,
      pathMatch:'full'
  },

];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
