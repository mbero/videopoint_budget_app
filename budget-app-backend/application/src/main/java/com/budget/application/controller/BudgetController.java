package com.budget.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.budget.application.model.Expense;
import com.budget.application.response.provider.ExpenseResponseEntity;
import com.budget.application.response.provider.ExpenseResponseProvider;
import com.budget.application.response.provider.TagResponseEntity;
import com.budget.application.response.provider.TagResponseProvider;

@RestController
public class BudgetController {

	@Autowired
	private ExpenseResponseProvider expenseResponseProvider;
	
	@Autowired 
	private TagResponseProvider tagResponseProvider;
	
	@CrossOrigin
	@RequestMapping(value = "/expense", method = RequestMethod.POST)
	public ExpenseResponseEntity saveExpense(@RequestBody Expense expense) {
		return expenseResponseProvider.saveExpense(expense);
	}

	@CrossOrigin
	@RequestMapping(value = "/expense/{expenseId}", method = RequestMethod.DELETE)
	public ExpenseResponseEntity deleteExpense(@PathVariable("expenseId") Long expenseId) {
		return expenseResponseProvider.deleteExpense(expenseId);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/expenses", method = RequestMethod.GET)
	public ExpenseResponseEntity getAllExpenses() {
		return expenseResponseProvider.getAllExpenses();
	}

	@CrossOrigin
	@RequestMapping(value = "expense/criteria", method = RequestMethod.GET)
	public ExpenseResponseEntity getExpensesBySearchCriteria(@RequestParam(value = "tagNames") List<String> tagNames,
			@RequestParam(value = "fromDate") String fromDate, @RequestParam(value = "toDate") String toDate) {
		return expenseResponseProvider.getExpensesBySearchCriteria(tagNames, fromDate, toDate);
	}

	@CrossOrigin
	@RequestMapping(value = "tags", method = RequestMethod.GET)
	public TagResponseEntity getAllTags() {
		return tagResponseProvider.getAllTags();
	}

	@CrossOrigin
	@RequestMapping(value = "tag", method = RequestMethod.POST)
	public TagResponseEntity addNewTag(@RequestBody String tagName) {
		return tagResponseProvider.createTag(tagName);
	}
	
	@CrossOrigin
	@RequestMapping(value = "tag/{tagId}", method = RequestMethod.DELETE)
	public TagResponseEntity deleteTag(@PathVariable("tagId") Long tagId) {
		return tagResponseProvider.deleteTag(tagId);
	}
}
