import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Expense } from 'src/app/models/Expense';
import { ExpenseEditOperation } from 'src/app/models/ExpenseEditOperation';

@Component({
  selector: 'app-edit-expense-modal',
  templateUrl: './edit-expense-modal.component.html',
  styleUrls: ['./edit-expense-modal.component.scss']
})
export class EditExpenseModalComponent implements OnInit {

  public operationType: String;

  constructor(
    public dialogRef : MatDialogRef<EditExpenseModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ExpenseEditOperation
  ) { 
      this.operationType = data.operationType;
  }

  ngOnInit(): void {

  }

  public okCLick(){
    this.dialogRef.close();
  }

}
