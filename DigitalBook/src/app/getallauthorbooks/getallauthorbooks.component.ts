import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { BookServiceService } from '../book-service.service';
import { TokenStorageService } from '../token-storage.service';

@Component({
  selector: 'app-getallauthorbooks',
  templateUrl: './getallauthorbooks.component.html',
  styleUrls: ['./getallauthorbooks.component.scss']
})
export class GetallauthorbooksComponent implements OnInit {

  books: any = [];
  message: any = "";
  displayedColumns: string[] = ['No.', 'Title', 'Logo URL', 'Category', 'Author UserName', 'Author Name', 'Price', 'Publisher', 'PublishedDate', 'Content', 'Active'];
  column: any ="";
  authorName : String = "";
  constructor(public bookService: BookServiceService, private router: Router, private tokenStorageService: TokenStorageService) { 
    AppComponent.isInitialHome=false;
  }

  ngOnInit(): void {
    const user = this.tokenStorageService.getUser();
    this.authorName = user.name;
    this.getAllAuthorBooks();
  }

  getAllAuthorBooks(){
    const observable = this.bookService.getAllAuthorBooks();
    observable.subscribe((books)=>{
      this.books = books;
      if(this.books.length == 0){
        this.message = "No books found for author " + this.authorName;
      }
      else{
        this.message = "";
      }
    },
    (error)=>{
      if(error.status == 400){
        this.bookService.redirectTologin();
      }
      this.message = "No books found for author " + this.authorName;
      this.books = [];
      this.authorName = "";
    })
  }

  tableRowClicked(book: any){
    this.bookService.book1 = book; 
    this.router.navigate(['/editbook']);
  }

}
