import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { AuthServiceService } from '../auth-service.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  form: any = {
    userName: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authService: AuthServiceService, private tokenStorage: TokenStorageService) {
    AppComponent.isInitialHome=false;
   }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      console.log("token "+this.tokenStorage.getToken())
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
      console.log("islogged in:"+this.isLoggedIn);
    }
  }
  onSubmit(): void {
    const { userName, password } = this.form;
    console.log("username is:"+userName);

    this.authService.login(userName, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        console.log(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
      },
      err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }
  reloadPage(): void {
    window.location.reload();
  }

}
