import { Component, NgModule } from '@angular/core';
import { Router, RouterModule, RouterOutlet } from '@angular/router';

import { StorageService } from './auth/services/storage.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,RouterModule,CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'buysellcars';

  isAdminLoggedIn:boolean=StorageService.isAdminLoggedIn()
  isCustomerLoggedIn:boolean=StorageService.isCustomerLoggedIn()

  constructor(private router:Router){}

  ngOnInit(){
    this.router.events.subscribe(event => {
      if(event.constructor.name==='NavigationEnd'){
        this.isAdminLoggedIn=StorageService.isAdminLoggedIn()
        this.isCustomerLoggedIn=StorageService.isCustomerLoggedIn()
        console.log(this.isAdminLoggedIn,this.isCustomerLoggedIn)
      }
    })
  }

  logout(){
    StorageService.signout()
    this.router.navigateByUrl('/login')
  }
}
