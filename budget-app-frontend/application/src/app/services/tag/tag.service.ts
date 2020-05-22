import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Tag } from 'src/app/models/Tag';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  public allTagsResults: Observable<Tag[]>;

  constructor(private http: HttpClient) {

   }

  public getAllTags(): Observable<Tag[]>{
    
    this.allTagsResults = new Observable( observer => {
      this.http.get('url').subscribe(response =>{
        //TODO - process response
        
      });
    });

    return this.allTagsResults;
  }

}
