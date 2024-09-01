import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from '../../../auth/services/storage.service';
import { Observable } from 'rxjs';
const BASE_URL='http://localhost:8080/'
@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http:HttpClient) { }
  createAuthorizationHeader():HttpHeaders{
    let authHeaders:HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization','Bearer ' + StorageService.getToken()
    )
  }

  getAllCars():Observable<any>{
    return this.http.get(BASE_URL+"api/admin/cars",{
      headers:this.createAuthorizationHeader()
    })
  }
}
