import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-new-expense',
  templateUrl: './new-expense.component.html',
  styleUrls: ['./new-expense.component.scss']
})
export class NewExpenseComponent{

  public expenseForm = new FormGroup({
    tags: new FormControl(undefined),
    value: new FormControl(undefined, Validators.required)
  });

  get tagsControl(): FormControl{
    return this.expenseForm.get('tags') as FormControl
  }

  get valueControl() :FormControl{
    return this.expenseForm.get('value') as FormControl
  }

  constructor(){

  }

  public submitExpense(event: any){
    console.log('submitExpense invoked');
  }
}
