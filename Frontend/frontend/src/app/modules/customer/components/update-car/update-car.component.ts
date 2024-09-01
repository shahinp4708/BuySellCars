import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { StorageService } from '../../../../auth/services/storage.service';

@Component({
  selector: 'app-update-car',
  standalone: true,
  imports: [],
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  id!: number;
  car: any;
  existingImage:string|null=null;
  listOfBrands=["Audi","BMW"]
  lisOfType=["Petrol","Diesel"]
  listOfColors=["Red","Green","Yellow"]
  listOfTransmission=['Manual','Automated']
  updateCarForm!: FormGroup
  selectedFile!:File|null
  imagePreview!:string|ArrayBuffer|null
  imgChanged!:boolean
  isSpinning=false

  constructor(
    private service: CustomerService,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    private router:Router
  ) {}

  ngOnInit() {
    this.updateCarForm=this.fb.group({
      brand:[null,[Validators.required]],
      type:[null,[Validators.required]],
      color:[null,[Validators.required]],
      transmission:[null,[Validators.required]],
      price:[null,[Validators.required,Validators.pattern('^[0-9]+$')]],
      year:[null,[Validators.required,Validators.pattern('^[0-9]{4}$')]],
      name:[null,[Validators.required,Validators.pattern('^[0-9]+$')]],
      description:[null,[Validators.required]]

    })
    this.id = this.activatedRoute.snapshot.params['id']; // Access the 'id' param here
    this.getCar();
  }
  getCar(){
    this.service.getCarById(this.id).subscribe(res=>{
      this.car=res;
      this.existingImage='data:image/jpeg;base64,'+res.returnedImg;
      this.updateCarForm.patchValue(res);
    })
  }

  updateCar(id:number){
    this.isSpinning=true
    console.log(this.updateCarForm)
    console.log(this.selectedFile)
    const formData:FormData= new FormData();
    formData.append('brand',this.updateCarForm.value.brand)
    formData.append('type',this.updateCarForm.value.type)
    formData.append('color',this.updateCarForm.value.color)
    formData.append('transmission',this.updateCarForm.value.transmission)
    formData.append('price',this.updateCarForm.value.price)
    formData.append('year',this.updateCarForm.value.year)
    formData.append('name',this.updateCarForm.value.name)
    formData.append('description',this.updateCarForm.value.description)
    if(this.selectedFile){
      formData.append('image',this.selectedFile)
    }
    formData.append("user",StorageService.getUserId())
    this.service.updateCar(this.id,formData).subscribe(res=>{
      console.log("success")
      this.router.navigate(['/customer/dashboard'])
      this.isSpinning=false
    })
  }
  onFileSelected(event:any){
    this.selectedFile=event.target.files[0]
    const reader=new FileReader()
    reader.onload=(e:any)=>{
      this.imagePreview=e.target.result
    }
    if(this.selectedFile)
    reader.readAsDataURL(this.selectedFile)
    this.imgChanged=true;
    this.existingImage=null;


  }
}
