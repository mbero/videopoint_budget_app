import { Injectable } from '@angular/core';
import { Expense } from 'src/app/models/Expense';
import { of, Observable, Subject } from 'rxjs';
import { Tag } from 'src/app/models/Tag';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  public addedExpenseResult: Observable<Expense[]>;
  public hostAdress: String = "http://localhost:8083/";
  public subject = new Subject<HttpErrorResponse>();


  constructor(private httpClient: HttpClient) { }

  onErrorOccurrs(): Observable<HttpErrorResponse>{
    return this.subject.asObservable();
  }
  
  public deleteExpense(expenseId: number) {
    //TODO - implement me
    return of([]);
  }

  public saveExpense(expense: Expense) {
    this.addedExpenseResult = new Observable(observer => {
      let url = this.hostAdress + "expense";
      let httpOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        })
      };
      let tagNames: string[] = [];
      if (typeof (expense.tags) == "string") {
        let tags: String = expense.tags;
        let tagsArr = tags.split(',');
        expense.tags = [];
        tagsArr.forEach(val => expense.tags.push({ name: val }));
      }
      expense.tags.forEach(val => tagNames.push(String(val.name)));
      let objectToSend = null;
      if (expense.id) {
        objectToSend = {
          "id": expense.id,
          "value": expense.value,
          "tags": expense.tags,
          "formattedDate": expense.date
        }
      } else {
        objectToSend = {
          "value": expense.value,
          "tags": expense.tags
        }
      }

      this.httpClient.post<any>(url, objectToSend, httpOptions).subscribe(response=>{
        observer.next(response);
      }, err=> this.handleException(err));
    });


    return this.addedExpenseResult;
  }
  
  public handleException(err: HttpErrorResponse){
    this.subject.next(err);
  }

  public getAllExpenses(): Observable<Expense[]> {
    let expense1 = {
      id: 0,
      tags: [{ name: "tag1" }],
      value: 20.20,
      date: "2020-05-10"
    }
    let expense2 = {
      id: 1,
      tags: [{ name: "tag2" }],
      value: 404.20,
      date: "2020-06-10"
    }

    return of([expense1, expense2]);
  }

  public getExpenseFromData(tags: Tag[], value: number) {
    let expenseDate = new Date().toISOString();
    let expense: Expense = {
      id: 0,
      tags: tags,
      value: value,
      date: expenseDate
    }
    return expense;
  }

  public addExpense(expense: Expense) {
    return this.saveExpense(expense);
  }
}
