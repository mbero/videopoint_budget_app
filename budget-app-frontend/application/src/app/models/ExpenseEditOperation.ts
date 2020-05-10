import { Expense } from './Expense';

export interface ExpenseEditOperation{
    operationType: String,
    expense: Expense
}