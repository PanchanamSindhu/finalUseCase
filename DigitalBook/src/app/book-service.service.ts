import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Book, BookCategory } from './book';
import { TokenStorageService } from './token-storage.service';
const API_URL = 'http://userservice-env.eba-24nxyxit.ap-northeast-1.elasticbeanstalk.com/reg';
//http://userservice-env.eba-24nxyxit.ap-northeast-1.elasticbeanstalk.com/
const API_RED_URL='http://localhost:8086/reader';

@Injectable({
  providedIn: 'root'
})
export class BookServiceService {
  book:Book= new Book('DigitalBook1book1_url', 'DigitalBook1', BookCategory.ADVENTURE, 1, '', '', 'XYZ Publisher', new Date(), 'Digitalbook1 content', true);
  public book1: any;
  public user: any;
  constructor(public client: HttpClient, private tokenStorageService: TokenStorageService,  private router: Router) {
    this.user = this.tokenStorageService.getUser();
   }

  saveBook(title: string, logo: string, category: BookCategory, price: number, authorUserName: string, authorName: string, publisher: string, publishedDate: Date, content: string, active: Boolean){
    const authorId = this.user.id;
    this.book = new Book(logo, title, category, price, authorUserName, authorName, publisher, publishedDate, content, active);
    return this.client.post(API_URL + "/author/" + authorId + "/books", this.book);
  }

  searchBooks(title: String, category: String, author: String){
    return this.client.get(API_URL + "/search?title=" + title + "&category=" + category + 
    "&author=" + author);
  }
  updateBook(title: string, logo: string, category: BookCategory, price: number, authorUserName: string, authorName: string, publisher: string, publishedDate: Date, content: string, active: Boolean){
    const authorId = this.user.id;
    this.book = new Book(logo, title, category, price, authorUserName, authorName, publisher, publishedDate, content, active);
    return this.client.put(API_URL + "/author/" + authorId + "/books", this.book);
  }
  getAllAuthorBooks() {
    const authorId = this.user.id;
    return this.client.get(API_URL + "/author/" + authorId + "/allbooks");
  }

  subscribeBook(id:string){
    console.log("inside subcribe"+id);
       return this.client.get(API_URL+ "/book/"+id + "/subscribe");
  }

  redirectTologin(){
    this.tokenStorageService.signOut();
    this.router.navigate(['/login']).then(() => {
      window.location.reload();
    });
  }

}
