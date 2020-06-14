import { TestBed } from '@angular/core/testing';

import { ExpenseService } from './expense.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

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

  it('should return proper Expense based on params', () => {
    let tags = [{ name: 'someNewTagName' }];
    let value = 19.90;

    let expectedResponseFromData = {
      tags: tags,
      value: value
    };

    let expenseFromData = service.getExpenseFromData(tags, value);
    expect(expectedResponseFromData.value).toBe(expenseFromData.value);
  });

  it('should return all expenses in proper format', () => {

    const expensesResponse =
    {
      "expenses": [
        {
          "id": 28,
          "creationDate": "2020-06-16T22:00:00",
          "value": 20112,
          "tags": [
            {
              "id": 1,
              "name": "tag1"
            },
            {
              "id": 29,
              "name": "weleflwlfewrlwer"
            }
          ],
          "formattedDate": "2020-06-16T22:00:00.000Z"
        }
      ]
    };

    service.getAllExpenses().subscribe(response=>{
        expect(response.length).toEqual(1);
    });

    const request = httpTestingController.expectOne('http://localhost:8083/expenses');

    expect(request.request.method).toBe('GET');

    request.flush(expensesResponse);
  });

  it('should return proper expense object as response', () => {

    const expectedResponseBody = {
      "expenses": [
        {
          "id": 32,
          "creationDate": "2020-06-05T08:09:36.9607723",
          "value": 2020,
          "tags": [
            {
              "id": 31,
              "name": "someNewTagName"
            }
          ],
          "formattedDate": null
        }
      ]
    };

    let tag = { name: "someNewTagName"};
    let exampleTags = [tag];

    let testExpenseBody = service.getExpenseFromData(exampleTags, 2020);

    service.addExpense(testExpenseBody).subscribe(response=>{
      expect(response["expenses"].length).toEqual(1);
    });

    const request = httpTestingController.expectOne('http://localhost:8083/expense');
    
    expect(request.request.method).toBe('POST');

    request.flush(expectedResponseBody);


  });

});
