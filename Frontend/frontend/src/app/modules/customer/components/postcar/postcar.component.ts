import { Component, NgModule } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { StorageService } from '../../../../auth/services/storage.service';

@Component({
  selector: 'app-postcar',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule,FormsModule],
  templateUrl: './postcar.component.html',
  styleUrl: './postcar.component.scss'
})
export class PostcarComponent {
  listOfBrands=["Audi","BMW"]
  lisOfType=["Petrol","Diesel"]
  listOfColors=["Red","Green","Yellow"]
  listOfTransmission=['Manual','Automated']
  postCarForm!: FormGroup
  isSpinning=false
  selectedFile!:File|null
  imagePreview!:string|ArrayBuffer|null

  constructor(private service:CustomerService,
    private fb:FormBuilder,private router:Router,
){}

    ngOnInit(){
      this.postCarForm=this.fb.group({
        brand:[null,[Validators.required]],
        type:[null,[Validators.required]],
        color:[null,[Validators.required]],
        transmission:[null,[Validators.required]],
        price:[null,[Validators.required,Validators.pattern('^[0-9]+$')]],
        name:[null,[Validators.required]],
        description:[null,[Validators.required]]

      })
    }

    postCar() {
      this.isSpinning = true;
      const formData = new FormData();
      formData.append('brand', this.postCarForm.value.brand);
      formData.append('type', this.postCarForm.value.type);
      formData.append('color', this.postCarForm.value.color);
      formData.append('transmission', this.postCarForm.value.transmission);
      formData.append('price', this.postCarForm.value.price);
      formData.append('name', this.postCarForm.value.name);
      formData.append('description', this.postCarForm.value.description);
      if (this.selectedFile) {
        formData.append('img', this.selectedFile); // Ensure field name matches CarDto
      }
      formData.append('userId', StorageService.getUserId());
    
      this.service.postCar(formData).subscribe(res => {
        console.log("success");
        this.router.navigate(['/customer/dashboard']);
        this.isSpinning = false;
      }, error => {
        console.error("Error posting car:", error);
        this.isSpinning = false;
      });
    }
    
    onFileSelected(event: any) {
      this.selectedFile = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.imagePreview = e.target.result;
      };
      if (this.selectedFile) {
        reader.readAsDataURL(this.selectedFile);
      }
    }
    
}
