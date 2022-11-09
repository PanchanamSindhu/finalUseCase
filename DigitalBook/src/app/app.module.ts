import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { BookformComponent } from './bookform/bookform.component';
import { SavebookComponent } from './savebook/savebook.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { GetallauthorbooksComponent } from './getallauthorbooks/getallauthorbooks.component';
import { ProfileComponent } from './profile/profile.component';


const routes:Routes = [
 
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'bookform', component: BookformComponent},
  {path: 'savebook', component: SavebookComponent},
  {path: 'updatebook', component: UpdatebookComponent},
  {path: 'getallauthorbooks', component: GetallauthorbooksComponent},
  {path: 'profile', component: ProfileComponent}

]

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    BookformComponent,
    SavebookComponent,
    UpdatebookComponent,
    GetallauthorbooksComponent,
    ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
   
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
