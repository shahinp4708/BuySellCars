import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {
  cars:any=[]
  constructor(private service:AdminService){}

  ngOnInit(){
    this.getCars();
  }
  getCars(){
    this.service.getAllCars().subscribe(res=>{
      this.cars=res;
    })
  }

}
