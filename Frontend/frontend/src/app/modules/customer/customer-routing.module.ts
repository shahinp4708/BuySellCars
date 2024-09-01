import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomerDashboardComponent } from './components/customer-dashboard/customer-dashboard.component';
import { PostcarComponent } from './components/postcar/postcar.component';
import { MyCarsComponent } from './components/my-cars/my-cars.component';
import { UpdateCarComponent } from './components/update-car/update-car.component';

const routes: Routes = [
  {path:'dashboard', component: CustomerDashboardComponent},
  {path:'postcar', component: PostcarComponent},
  {path:'my-cars', component: MyCarsComponent},
  {path:'updateCar', component: UpdateCarComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
