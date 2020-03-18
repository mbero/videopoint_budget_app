package com.budget.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.repository.ExpenseRepository;

@Service
public class ExpensesServiceImpl implements ExpensesService{

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Override
	public Expense createExpense(Expense expense) {
		return expenseRepository.save(expense);
	}
	
	@Override
	public Optional<List<Expense>> getExpensesBySearchCriteria(ExpensesSearchCriteria criteria) {
		List<Expense> filteredExpenses = new ArrayList<Expense>();
		List<Expense> allExpenses = expenseRepository.findAll();
		
		if(criteria.getTagNames()!=null) {
			filteredExpenses = allExpenses.stream()
					.filter(expense -> expense.getTags().stream()
							.anyMatch(tag -> criteria.getTagNames().contains(tag.getName())))
					.collect(Collectors.toList());
		}
		
		if(criteria.getFromDate()!=null) {
			filteredExpenses = filteredExpenses.stream()
					.filter(expense -> expense.getCreationDate().isAfter(criteria.getFromDate().toLocalDateTime()))
					.collect(Collectors.toList());
		}
		
		if(criteria.getToDate()!=null) {
			filteredExpenses = allExpenses.stream()
					.filter(expense -> expense.getCreationDate().isBefore(criteria.getToDate().toLocalDateTime()))
					.collect(Collectors.toList());
		}
		
		
		return Optional.of(filteredExpenses);
	}

	@Override
	public void deleteExpense(Long expenseId) {
		expenseRepository.deleteById(expenseId);
	}

	@Override
	public Optional<List<Expense>> getAllExpenses() {
		return Optional.of(expenseRepository.findAll());
	}

	

}
