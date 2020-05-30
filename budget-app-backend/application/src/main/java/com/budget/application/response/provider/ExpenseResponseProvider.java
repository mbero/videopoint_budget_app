package com.budget.application.response.provider;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.service.ExpensesService;
import com.budget.application.service.TagService;
import com.budget.application.utils.CommonTools;

@Service
public class ExpenseResponseProvider {

	@Autowired
	private ExpensesService expenseService;

	@Autowired
	private TagService tagService;
	
	@Autowired
	private CommonTools commonTools;

	public ExpenseResponseEntity getAllExpenses() {
		ExpenseResponseEntity response = null;
		try {
			ExpensesList expenses = new ExpensesList(expenseService.getAllExpenses().get());
			response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
		} catch (Exception e) {
			response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);
		}

		return response;
	}

	public ExpenseResponseEntity saveExpense(Expense expense) {
		ExpenseResponseEntity response = null;
		try {
			if(StringUtils.isEmpty(expense.getFormattedDate())) {
				expense.setCreationDate(LocalDateTime.now());
			}else {
				LocalDateTime ldtFromISO = commonTools.getLocalDateTimeFromISODate(expense.getFormattedDate());
				expense.setCreationDate(ldtFromISO);
			}
			List<Tag> tagsFromExpense = expense.getTags();
			List<Tag>  newTags = new ArrayList<Tag>();
			for(Tag currentTag: tagsFromExpense) {
				newTags.add(tagService.createTag(currentTag.getName()));
			}
			expense.setTags(newTags);
			ExpensesList expenses = new ExpensesList(Arrays.asList(expenseService.createExpense(expense)));
			response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
		} catch (Exception e) {
			response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return response;
	}

	public ExpenseResponseEntity deleteExpense(Long expenseId) {
		ExpenseResponseEntity response = null;
		try {
			expenseService.deleteExpense(expenseId);
			response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.OK);

		} catch (Exception e) {
			response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	public ExpenseResponseEntity getExpensesBySearchCriteria(List<String> tagNames, String fromDate, String toDate) {
		ExpensesSearchCriteria expensesSearchCriteria = new ExpensesSearchCriteria();
		if (tagNames.size() > 0) {
			expensesSearchCriteria.setTagNames(tagNames);
		}
		if (StringUtils.isEmpty(fromDate) != true) {
			Timestamp fDate = null;
			try {
				fDate = commonTools.getTimeStampFromISODate(fromDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			expensesSearchCriteria.setFromDate(fDate);
		}
		if (StringUtils.isEmpty(toDate) != true) {
			Timestamp tDate = null;
			try {
				tDate = commonTools.getTimeStampFromISODate(toDate);
			} catch (Exception e) {
				e.printStackTrace();
			}
			expensesSearchCriteria.setFromDate(tDate);
		}
		ExpenseResponseEntity response = null;
		try {
			ExpensesList expenses = new ExpensesList(expenseService.getExpensesBySearchCriteria(expensesSearchCriteria).get());
			response = new ExpenseResponseEntity(expenses, HttpStatus.OK);
		} catch (Exception e) {
			response = new ExpenseResponseEntity(new ExpensesList(), HttpStatus.NO_CONTENT);
		}

		return response;
	}
}
