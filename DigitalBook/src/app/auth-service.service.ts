import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8086/reg/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(private http: HttpClient) { }

  register(name: string, userName: string, emailId: string, password: string, role: any): Observable<any> {
    console.log("inside register")
    //http://userservice-env.eba-24nxyxit.ap-northeast-1.elasticbeanstalk.com
    return this.http.post('http://userservice-env.eba-24nxyxit.ap-northeast-1.elasticbeanstalk.com/reg/signup', {
      name,
      userName,
      emailId,
      password,
      role
    }, httpOptions);
  }

  login(userName: string, password: string): Observable<any> {
    console.log("inside auth service:")
    return this.http.post('http://userservice-env.eba-24nxyxit.ap-northeast-1.elasticbeanstalk.com/reg/login', {
      userName,
      password
    }, httpOptions);
  }
}
