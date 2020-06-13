import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewExpenseComponent } from './new-expense.component';
import { ExpenseService } from 'src/app/services/expense/expense.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TagService } from 'src/app/services/tag/tag.service';
import { MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

describe('NewExpenseComponent', () => {
  let component: NewExpenseComponent;
  let fixture: ComponentFixture<NewExpenseComponent>;
  let httpTestingController: HttpTestingController;
  let expenseService: ExpenseService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewExpenseComponent ],
      imports: [HttpClientTestingModule, MatDialogModule, MatAutocompleteModule],
      providers: [ExpenseService]
    })
    .compileComponents();

    expenseService = TestBed.inject(ExpenseService);
    httpTestingController = TestBed.inject(HttpTestingController);

  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewExpenseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
