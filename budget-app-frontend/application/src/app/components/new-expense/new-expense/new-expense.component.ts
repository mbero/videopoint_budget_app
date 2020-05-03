import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, Subscription } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { Tag } from 'src/app/models/Tag';
import { TagService } from 'src/app/services/tag/tag.service';

@Component({
  selector: 'app-new-expense',
  templateUrl: './new-expense.component.html',
  styleUrls: ['./new-expense.component.scss']
})
export class NewExpenseComponent implements OnInit{
  @ViewChild('tagsInput', {static:true}) tagsInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto', {static: true}) auto: MatAutocomplete;

  public readonly separatorKeyCodes: number[] = [13,188];
  public addOnBlur = true;
  public selectedTags: Tag[] = [];
  public allTags: Tag[] = [];
  public filteredTags : Observable<Tag[]>;

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

  constructor(private tagService: TagService){

  }
  ngOnInit(): void {
    this.tagService.getAllTags().subscribe(response =>{
      this.allTags = response;
    });

    this.filteredTags = this.tagsControl.valueChanges.pipe(
      map((val: any | null) => val ? this.filterTags(val) : this.allTags.slice())
    );
  }

  private filterTags(tag: any):Tag[]{
    let filterValue = '';
    if(typeof(tag)=="object"){
      filterValue = tag.name.toLowerCase();
    }else{
      filterValue = tag.toLowerCase();
    }

    return this.allTags.filter(val => val.name.toLowerCase().includes(filterValue));
  }  


  public add(event: any){
  
  }

  public addExpenseClickHandler(){
    console.log('addExpenseClickHandler invoked');
  }

  public selected(event: MatAutocompleteSelectedEvent){
    let selectedTag = event.option.value;
    this.selectedTags.push(selectedTag);
    this.tagsInput.nativeElement.value = '';
  }

  public submitExpense(event: any){
    console.log('submitExpense invoked');
  }
}
