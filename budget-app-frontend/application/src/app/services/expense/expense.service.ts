import { Injectable } from '@angular/core';
import { Expense } from 'src/app/models/Expense';
import { of } from 'rxjs';
import { Tag } from 'src/app/models/Tag';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {

  constructor() { }

  public getExpenseFromData(tags: Tag[], value: number){
    let expenseDate = new Date().toISOString();
    let expense: Expense = {
      tags: tags,
      value: value,
      date: expenseDate
    }
    return expense;
  }

  public addExpense(expense: Expense){
    return of(expense);
  }
}
