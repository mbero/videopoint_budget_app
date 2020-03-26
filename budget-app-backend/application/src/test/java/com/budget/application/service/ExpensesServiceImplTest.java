package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Expense;
import com.budget.application.model.ExpensesSearchCriteria;
import com.budget.application.model.Tag;
import com.budget.application.repository.ExpenseRepository;
import com.budget.application.utils.CommonTools;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class ExpensesServiceImplTest {
	
	@InjectMocks
	private ExpensesServiceImpl expensesService;
	
	@Mock
	private ExpenseRepository expenseRepository;
	
	private TestUtils testUtils;
	
	@Mock
	private CommonTools tools;
	
	private java.sql.Timestamp fromDate;

	private java.sql.Timestamp toDate;
	
	private List<Expense> generatedExpenses;
	
	private Expense testExpense;
	
	@BeforeEach
	void setUp() throws Exception {
		testUtils = new TestUtils();
		generatedExpenses = testUtils.generateGivenAmounOfTestExpenseObjects(1, 1, Timestamp.valueOf("2018-11-12 01:00:00.123456789"));
		fromDate = Timestamp.valueOf("2018-11-09 01:02:03.123456789");
		toDate = Timestamp.valueOf("2018-11-12 01:02:03.123456789");
		testExpense = testUtils.generateGivenAmounOfTestExpenseObjects(1, 1, Timestamp.valueOf("2018-11-10 01:02:03.123456789")).get(0);
		
		Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(testExpense);
		Mockito.when(expenseRepository.findAll()).thenReturn(generatedExpenses);
		
	}

	@Test
	void testCreateExpense() {
		Expense createdExpense = null;
		createdExpense = expensesService.createExpense(testExpense);
		assertNotNull(createdExpense);
		assertEquals(createdExpense.getValue(), testExpense.getValue());
	}

	@Test
	void testGetExpensesByCriteriaWithTagsSettedOnly() {
		ExpensesSearchCriteria expenseSearchCriteria = new ExpensesSearchCriteria();
		List<String> tagNames = generatedExpenses.get(0).getTags().stream().map(Tag::getName).collect(Collectors.toList());
		expenseSearchCriteria.setTagNames(tagNames);
		Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expenseSearchCriteria);
		assertTrue(foundExpenses.get().size()>0);
	}
	
	@Test
	void testGetExpensesByCriteriaWithFromDateSettedOnly() {
		ExpensesSearchCriteria expenseSearchCriteria = new ExpensesSearchCriteria();
		expenseSearchCriteria.setFromDate(fromDate);
		Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expenseSearchCriteria);
		assertTrue(foundExpenses.get().size()>0);
	}
	
	@Test
	void testGetExpensesByCriteriaWithToDateSettedOnly() {
		ExpensesSearchCriteria expenseSearchCriteria = new ExpensesSearchCriteria();
		expenseSearchCriteria.setToDate(toDate);
		Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expenseSearchCriteria);
		assertTrue(foundExpenses.get().size()>0);
	}
	
	@Test
	void testGetExpensesByCriteriaWithBothDateSetted() {
		ExpensesSearchCriteria expenseSearchCriteria = new ExpensesSearchCriteria();
		expenseSearchCriteria.setFromDate(fromDate);
		expenseSearchCriteria.setToDate(toDate);
		Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expenseSearchCriteria);
		assertTrue(foundExpenses.get().size()>0);
	}
	
	@Test
	void testGetExpensesByCriteriaWithBothDateAndTagsSetted() {
		ExpensesSearchCriteria expenseSearchCriteria = new ExpensesSearchCriteria();
		expenseSearchCriteria.setFromDate(fromDate);
		expenseSearchCriteria.setToDate(toDate);
		List<String> tagNames = generatedExpenses.get(0).getTags().stream().map(Tag::getName).collect(Collectors.toList());
		expenseSearchCriteria.setTagNames(tagNames);
		Optional<List<Expense>> foundExpenses = expensesService.getExpensesBySearchCriteria(expenseSearchCriteria);
		assertTrue(foundExpenses.get().size()>0);
	}
	

	@Test
	void testDeleteExpense() {
		Expense expense = expenseRepository.findAll().get(0);
		expensesService.deleteExpense(expense.getId());
		Optional<Expense> foundByIdAfterDelete = expenseRepository.findById(expense.getId());
		assertFalse(foundByIdAfterDelete.isPresent());
	}

	@Test
	void testGetAllExpenses() {
		Optional<List<Expense>> allExpenses = expensesService.getAllExpenses();
		assertEquals(allExpenses.get().size(), generatedExpenses.size());
	}
}
