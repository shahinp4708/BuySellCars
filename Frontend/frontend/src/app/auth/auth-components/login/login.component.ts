import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { StorageService } from '../../services/storage.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm!:FormGroup
  isSpinning: boolean = false

  constructor(private fb:FormBuilder,
    private authService:AuthService,

    private router:Router
  ){
    this.loginForm=this.fb.group({
      email:[null,[Validators.required]],
      password:[null,[Validators.required]]
    });



  }
  login(){
    this.isSpinning=true;
    this.authService.login(this.loginForm.value).subscribe(res=>{
      if(res.userId!=null){
        const user={
          id:res.userId,
          role:res.userRole
        }
      
      StorageService.saveUser(user)
      StorageService.saveToken(res.jwt);
      if(StorageService.isAdminLoggedIn())
        this.router.navigateByUrl('/admin/dashboard');
      else if(StorageService.isCustomerLoggedIn())
        this.router.navigateByUrl('/customer/dashboard');
      }
      else{
       
        alert("Invalid email or password");
      }
      this.isSpinning=false;
    
  })
 


}
}
