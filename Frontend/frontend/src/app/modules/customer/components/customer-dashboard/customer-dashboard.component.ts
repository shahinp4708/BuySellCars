import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {
  cars:any=[]
  constructor(private service:CustomerService){}

  ngOnInit(){
    this.getCars();
  }
  getCars(){
    this.service.getAllCars().subscribe(res=>{
      this.cars=res;
    })
  }

}
