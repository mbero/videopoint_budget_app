import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Expense } from 'src/app/models/Expense';
import { ExpenseEditOperation } from 'src/app/models/ExpenseEditOperation';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-edit-expense-modal',
  templateUrl: './edit-expense-modal.component.html',
  styleUrls: ['./edit-expense-modal.component.scss']
})
export class EditExpenseModalComponent implements OnInit {

  public operationType: String;
  public editForm: FormGroup;

  public get dateCtrl(): FormControl{
    return this.editForm.get('date') as FormControl;
  }

  constructor(
    public dialogRef : MatDialogRef<EditExpenseModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ExpenseEditOperation
  ) { 
      this.operationType = data.operationType;

      this.editForm = new FormGroup({
        date: new FormControl(data.expense.date, Validators.compose([Validators.required])),
        value: new FormControl(data.expense.value, Validators.compose([Validators.required, Validators.pattern('^(?:0|[1-9][0-9]*)\.[0-9]+$')])),
        tags: new FormControl(data.expense.tags.map(el=> el.name), Validators.compose([Validators.required]))
      });
  }

  ngOnInit(): void {

  }

  public cancelClickHandler(){
    this.dialogRef.close();
  }

}
