import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthServiceService } from '../auth-service.service';
import { SignUpRequest } from '../sign-up-request';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  form: any = {
    name: null,
    userName: null,
    emailId: null,
    password: null,
    author: false,
    reader: false
  };
  signUpreq=new SignUpRequest();
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  role:string[]=[];
  constructor(private authService: AuthServiceService) {
    AppComponent.isInitialHome=false;
   }

  ngOnInit(): void {
  }
  onSubmit(): void {
    const { name, userName, emailId, password, author, reader } = this.form;
    console.log("name is :"+name);
    this.signUpreq.emailId=emailId;
    this.signUpreq.name=name;
    this.signUpreq.userName=userName;
    this.signUpreq.password=password
    this.signUpreq.role=(author && reader) ? ["author","reader"]:author?["author"]:reader?["reader"]:[];
    console.log("sihnup :"+this.signUpreq.role);
    this.authService.register(name, userName, emailId, password, this.role).subscribe(
     
      response => {
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      error => {
        this.errorMessage = error.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

}
