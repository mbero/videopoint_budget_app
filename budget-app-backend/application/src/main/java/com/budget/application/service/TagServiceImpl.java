package com.budget.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.application.model.Tag;
import com.budget.application.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService{
	
	@Autowired
	private TagRepository tagRepository;
	
	@Override
	public Tag createTag(String tagName) {
		List<Tag> foundByName = tagRepository.findByName(tagName);
		if(foundByName.isEmpty()!=true) {
			return foundByName.get(0);
		}else {
			return tagRepository.save(new Tag(tagName));
		}
	}

	@Override
	public Optional<List<Tag>> getAllTags() {
		Iterable<Tag> tagResult = tagRepository.findAll();
		List<Tag> tags = new ArrayList<Tag>();
		tagResult.iterator().forEachRemaining(tags::add);
		return Optional.of(tags);
	}
	
	@Override
	public void deleteTag(Long tagId) {
		tagRepository.deleteById(tagId);
	}

	
	@Override
	public Optional<Tag> getTagByName(String tagName) {
		Tag foundTag = null;
		List<Tag> foundByName = tagRepository.findByName(tagName);
		if(foundByName.size()>0) {
			foundTag = foundByName.get(0);
		}
		return Optional.of(foundTag);
	}

	

	

}
