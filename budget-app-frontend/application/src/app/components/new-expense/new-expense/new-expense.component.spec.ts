import { async, ComponentFixture, TestBed, tick, fakeAsync, inject } from '@angular/core/testing';

import { Location } from "@angular/common";
import { NewExpenseComponent } from './new-expense.component';
import { HttpClientModule } from '@angular/common/http';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { ExpensesTableComponent } from '../../expenses-table/expenses-table/expenses-table.component';
import { RouterTestingModule } from '@angular/router/testing';
import { Router, RouterModule } from '@angular/router';
import { HttpTestingController, HttpClientTestingModule } from '@angular/common/http/testing';
import { ExpenseService } from 'src/app/services/expense/expense.service';
import { routes, AppRoutingModule } from 'src/app/app-routing.module';

describe('NewExpenseComponent', () => {
  let component: NewExpenseComponent;
  let fixture: ComponentFixture<NewExpenseComponent>;
  let location: Location;
  let router: Router;
  let httpTestingController: HttpTestingController;
  let expensesService: ExpenseService;


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NewExpenseComponent],
      imports: [HttpClientModule, HttpClientTestingModule, MatDialogModule, MatAutocompleteModule,
         RouterTestingModule.withRoutes(routes)],
      providers: [
        { provide: MatDialogRef, useValue: {} },
        { provide: MAT_DIALOG_DATA, useValue: [] },
        ExpenseService
      ]
    })
      .compileComponents();
  }));

  beforeEach(async(() => {
    expensesService = TestBed.get(ExpenseService);
    router = TestBed.get(Router);
    fixture = TestBed.createComponent(NewExpenseComponent);
    location = TestBed.get(Location);
    component = fixture.componentInstance;
    router.initialNavigation();
    httpTestingController = TestBed.get(HttpTestingController);

  }));

  afterEach(() => {
    fixture.destroy();
    component = null;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should redirect to expenses-table component', fakeAsync(() => {
    let expensesTablePath = "/expenses-table";
    let button = fixture.debugElement.nativeElement.querySelector("#showExpensesButton");
    fixture.detectChanges();
    if (button) {
      button.click();
      //We are waiting for all pending promises to be resolved
      tick();

      fixture.whenStable().then(() => {
        console.log('retreive location somehow');
        let path = location.path();
        expect(expensesTablePath).toBe(path);
      });
    }
  }));




});
