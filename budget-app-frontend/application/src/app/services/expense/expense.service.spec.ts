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

  it('should return proper Expense based on params', () =>{
    let tags = [ { name: 'someNewTagName' }];
    let value = 19.90;

    let expectedResponseFromData = {
      tags: tags,
      value: value
    };

    let expenseFromData = service.getExpenseFromData(tags, value);
    expect(expectedResponseFromData.value).toBe(expenseFromData.value);
  });  


});
