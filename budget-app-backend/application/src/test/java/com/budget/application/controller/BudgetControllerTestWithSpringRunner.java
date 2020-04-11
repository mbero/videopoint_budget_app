package com.budget.application.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.budget.application.model.Expense;
import com.budget.application.model.Tag;
import com.budget.application.response.provider.ExpensesList;
import com.budget.application.response.provider.TagsList;
import com.budget.application.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BudgetControllerTestWithSpringRunner {
	

	@LocalServerPort
	private int port;
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private HttpHeaders headers = new HttpHeaders();
	private TestUtils testUtils = new TestUtils();
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@Test
	public void testAddExpense() {
		Expense testExpenseToAdd = testUtils.generateTestExpense(1, null);
		HttpEntity<Expense> entity = new HttpEntity<Expense>(testExpenseToAdd, headers);
		ResponseEntity<ExpensesList> response = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, entity, ExpensesList.class);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(response.getBody().getExpenses().size()>0);
	}
	
	@Test
	public void testGetExpenses() {
		Expense testExpenseToAdd = testUtils.generateTestExpense(1, null);
		HttpEntity<Expense> createExpenseEntity = new HttpEntity<Expense>(testExpenseToAdd, headers);
		ResponseEntity<ExpensesList> createExpenseResponse = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, createExpenseEntity, ExpensesList.class);
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<ExpensesList> response = restTemplate.exchange(createURLWithPort("/expenses"), HttpMethod.GET, entity, ExpensesList.class);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(response.getBody().getExpenses().size()>0);
	}
	
	@Test
	public void testAddNewTag() {
		Tag generatedTag = testUtils.generateTestTags(1, false).get(0);
		HttpEntity<String> entity = new HttpEntity<String>(generatedTag.getName(), headers);
		ResponseEntity<TagsList> response = restTemplate.exchange(createURLWithPort("/tag"), HttpMethod.POST, entity, TagsList.class);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		assertTrue(response.getBody().getTags().size()>0);
	}
	
	@Test
	public void testDeleteTag() {
		Tag generatedTag = testUtils.generateTestTags(1, false).get(0);
		HttpEntity<String> generatedTagEntity = new HttpEntity<String>(generatedTag.getName(), headers);
		ResponseEntity<TagsList> generatedTagResponse = restTemplate.exchange(createURLWithPort("/tag"), HttpMethod.POST, generatedTagEntity, TagsList.class);
		Long generatedTagId = generatedTagResponse.getBody().getTags().get(0).getId();
		
		ResponseEntity<TagsList> deleteTagResponse = restTemplate.exchange(createURLWithPort("/tag/" + generatedTagId ), HttpMethod.DELETE, generatedTagEntity, TagsList.class);
		assertTrue(deleteTagResponse.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void testDeleteExpense() {
		Expense testExpenseToAdd = testUtils.generateTestExpense(1, null);
		HttpEntity<Expense> entity = new HttpEntity<Expense>(testExpenseToAdd, headers);
		ResponseEntity<ExpensesList> generatedExpenseResponse = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, entity, ExpensesList.class);
		Long generatedExpenseId = generatedExpenseResponse.getBody().getExpenses().get(0).getId();
		
		ResponseEntity<ExpensesList> deleteTagResponse = restTemplate.exchange(createURLWithPort("/expense/" + generatedExpenseId ), HttpMethod.DELETE, entity, ExpensesList.class);
		assertTrue(deleteTagResponse.getStatusCode().equals(HttpStatus.OK));
	}
	
	@Test
	public void testGetTags() {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<TagsList> response = restTemplate.exchange(createURLWithPort("/tags"), HttpMethod.GET, entity, TagsList.class);
		TagsList responseBody = response.getBody();
		
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(responseBody.getTags().size()>0);
	}
	
	@Test
	public void testGetExpensesBySearchCriteriaWithTagNamesSettedOnly() throws Exception {
		//Creating new expenses
		Expense testExpensesToAdd = testUtils.generateTestExpense(20, null);
		HttpEntity<Expense> entity = new HttpEntity<Expense>(testExpensesToAdd, headers);
		ResponseEntity<ExpensesList> generatedExpensesResponse = restTemplate.exchange(createURLWithPort("/expense"), HttpMethod.POST, entity, ExpensesList.class);
		//Creating request url based on params
		String tagName = generatedExpensesResponse.getBody().getExpenses().get(0).getTags().get(0).getName();
		String requestURL = createURLWithPort("/expenses/criteria?tagNames="+tagName+"&fromDate=&toDate=");
		//Executing request
		HttpEntity<String> getExpensesByCriteriaEntity = new HttpEntity<String>(null, headers);
		ResponseEntity<ExpensesList> getExpensesByCriteriaResponse = restTemplate.exchange(requestURL, HttpMethod.GET, getExpensesByCriteriaEntity, ExpensesList.class);
		
		assertTrue(getExpensesByCriteriaResponse.getStatusCode().equals(HttpStatus.OK));
		assertTrue(getExpensesByCriteriaResponse.getBody().getExpenses().size()>0);
	}

}
