package com.tweets.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tweets.datamodel.Tweet;
import com.tweets.repository.TweetsRepository;
import com.tweets.restmodel.ListTweetsResponse;

@Service
public class ListTweetsHandler {

	@Autowired
	private TweetsRepository tweetsRepo;

	public ListTweetsResponse listTweets(Integer offset) {
		Pageable pageable = PageRequest.of(offset, 100, Sort.by("createdOn").descending());
		Page<Tweet> tweetsPage = tweetsRepo.findAll(pageable);
		List<Tweet> tweets = tweetsPage.stream().collect(Collectors.toList());
		return ListTweetsResponse.builder().offset(offset).tweets(tweets).build();
	}
}
