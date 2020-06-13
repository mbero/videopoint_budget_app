import { TestBed } from '@angular/core/testing';

import { ExpenseService } from './expense.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';

describe('ExpenseService', () => {
  let service: ExpenseService;
  let httpTestingController: HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ExpenseService
      ]
    });
    service = TestBed.inject(ExpenseService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();    
  });



});
