import { Component, OnInit } from '@angular/core';
import { AppComponent } from '../app.component';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  currentUser: any;

  constructor(private token: TokenStorageService) {
    AppComponent.isInitialHome=false;
   }

  ngOnInit(): void {
    
    this.currentUser = this.token.getUser();
  }

}
