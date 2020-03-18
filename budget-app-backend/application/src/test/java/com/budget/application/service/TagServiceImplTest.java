package com.budget.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class TagServiceImplTest {

	@InjectMocks
	private TagServiceImpl tagService;
	
	@Mock
	private TagRepository tagRepository;
	
	private TestUtils testUtils;
	
	private List<Tag> allGeneratedTestTags;
	
	private String newTagName;
	
	@BeforeEach
	void setUp() throws Exception {
		testUtils = new TestUtils();
		allGeneratedTestTags = testUtils.generateTestTags(10, true);
		
		Tag generatedTag = allGeneratedTestTags.get(0);
		newTagName = generatedTag.getName();
		Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(generatedTag);
		Mockito.when(tagRepository.findAll()).thenReturn(allGeneratedTestTags);
		Mockito.when(tagRepository.findByName(newTagName)).thenReturn(java.util.Arrays.asList(generatedTag));
	
	}

	@Test
	void testCreateTag() {
		Tag createdTag = tagService.createTag(newTagName);
		assertNotNull(createdTag);
		assertEquals(createdTag.getName(), newTagName);
	}

	@Test
	void testGetAllTags() {
		List<Tag> allTags = tagService.getAllTags().get();
		assertEquals(allTags.size(), allGeneratedTestTags.size());
	}

	@Test
	void testDeleteTag() {
		Tag tag = tagService.getAllTags().get().get(0);
		tagService.deleteTag(tag.getId());
		Optional<Tag> tagWhichShouldNotExist = tagRepository.findById(tag.getId());
		assertFalse(tagWhichShouldNotExist.isPresent());
	}

	@Test
	void testGetTagByName() {
		Tag retreivedTagByName = tagService.getTagByName(newTagName).get();
		assertEquals(retreivedTagByName.getName(), newTagName);
	}

}
