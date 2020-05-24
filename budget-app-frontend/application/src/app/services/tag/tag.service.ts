import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { Tag } from 'src/app/models/Tag';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  public allTagsResults: Observable<Tag[]>;
  public hostAdress: String = "http://localhost:8083/"
  public subject = new Subject<HttpErrorResponse>();

  constructor(private http: HttpClient) {

  }

  onErrorOccurrs(): Observable<HttpErrorResponse>{
    return this.subject.asObservable();
  }

  public getAllTags(): Observable<Tag[]> {
    let url = this.hostAdress.concat('tags');
    this.allTagsResults = new Observable(observer => {
      this.http.get(url).subscribe(response => {
        let tagsFromResponse = response['tags'];
        observer.next(tagsFromResponse);
      }, err => this.handleException(err));
    });
    return this.allTagsResults;
  }

  public handleException(err: HttpErrorResponse){
    this.subject.next(err);
  }

}
