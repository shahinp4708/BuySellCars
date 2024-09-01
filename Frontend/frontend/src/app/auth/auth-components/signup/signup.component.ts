import { FormBuilder, FormControl, FormGroup,FormsModule,ReactiveFormsModule,Validators } from '@angular/forms';
import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,RouterModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {
  signupForm!:FormGroup
  isSpinning: boolean = false

  constructor(private fb:FormBuilder,
    private authSerivce:AuthService,
    private router:Router
  ){
    this.signupForm=this.fb.group({
      name:[null,[Validators.required]],
      email:[null,[Validators.required]],
      password: [null, [Validators.required, Validators.minLength(8)]],
      confirmPassword: [null, [Validators.required, Validators.minLength(8), this.confirmValidator.bind(this)]],
    });

  }
  confirmValidator =  (control: FormControl) : { [s:string]:boolean | null}=>{
    if(!control.value)
      return {required:true};
    else if(control.value!==this.signupForm.controls["password"].value)
      return {confirm:true,error:true};
    return {};
  }
  signup(){
    
    this.authSerivce.register(this.signupForm.value).subscribe(res=>
      console.log(res)
    )
    this.router.navigate(['/login']);
    console.log(this.signupForm.value);
  }

}
