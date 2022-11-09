import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { BookCategory } from '../book';
import { BookServiceService } from '../book-service.service';

@Component({
  selector: 'app-bookform',
  templateUrl: './bookform.component.html',
  styleUrls: ['./bookform.component.scss']
})
export class BookformComponent implements OnInit {
  categoryList: BookCategory[] = [];
  bookCategory = BookCategory;
  books: any = [];
  id!:string;
  subscribtionID!:any;
  title: String = 'DigitalBook1';
  category: String = '';
  author: String = 'Author';
  price: number = 1;
  publisher: String = 'XYZ Publisher';
  message: any = "";
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Active'];
  column: any ="";

  constructor(public bookService: BookServiceService, private router: Router) { 
    this.categoryList.push(this.bookCategory.ACTION);
    this.categoryList.push(this.bookCategory.ADVENTURE);
    this.categoryList.push(this.bookCategory.COMEDY);
    this.categoryList.push(this.bookCategory.THRILLER);
    this.categoryList.push(this.bookCategory.ROMANTIC);
    this.categoryList.push(this.bookCategory.FICTION);
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
  }
  searchBooks(){
    if(this.price == null || this.price == undefined){
      this.price = 0;
    }
    const observable = this.bookService.searchBooks(this.title, this.category, this.author);
    observable.subscribe((books)=>{
      this.books = books;
      this.id=this.books[0].id;
      this.subscribtionID=this.books[0].bookSubscribeId;
      this.message = "";
      if(this.books.length == 0){
        this.message = "No search results found. Please verify the details and search";
        this.books = [];
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin;
      }
      this.message = "No search results found. Please verify the details and search";
      this.books = [];
    })
  }

  subscribe(){
    console.log(this.id);
    this.subscribtionID
     this.bookService.subscribeBook(this.id).subscribe(
       data => {
         if(!data){
          //this.subscribtionID=data;
         }
         console.log(data+"data is !!!!!!!!!!!!!!!!!!");
         
         alert("Subscribed Succesfully "+this.subscribtionID);
         
       },
       error => console.log(error));
    }

}
