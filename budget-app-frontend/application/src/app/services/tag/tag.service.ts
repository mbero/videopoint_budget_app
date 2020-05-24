import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Tag } from 'src/app/models/Tag';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  public allTagsResults: Observable<Tag[]>;
  public hostAdress: String = "http://localhost:8083/"

  constructor(private http: HttpClient) {

  }

  public getAllTags(): Observable<Tag[]> {
    let url = this.hostAdress.concat('tags');
    this.allTagsResults = new Observable(observer => {
      this.http.get(url).subscribe(response => {
        let tagsFromResponse = response['tags'];
        observer.next(tagsFromResponse);
      });
    });
    return this.allTagsResults;
  }

}
