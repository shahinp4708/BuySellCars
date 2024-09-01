import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-my-cars',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './my-cars.component.html',
  styleUrl: './my-cars.component.scss'
})
export class MyCarsComponent {
  cars:any=[]
  constructor(private service:CustomerService){}

  ngOnInit(){
    this.getCars();
  }
  getCars(){
    this.service.getMyCars().subscribe(res=>{
      this.cars=res;
    })
  }

  deleteCar(id:number){
    this.service.deleteCar(id).subscribe(res=>{
      this.getCars();
    })
  }

}
