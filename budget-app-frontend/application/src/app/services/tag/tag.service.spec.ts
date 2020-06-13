import { TestBed } from '@angular/core/testing';

import { TagService } from './tag.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('TagService', () => {
  let service: TagService;
  let httpTestingController: HttpTestingController; 

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        TagService
      ]
    });
    service = TestBed.inject(TagService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
