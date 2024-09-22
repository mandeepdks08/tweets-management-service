package com.convo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.convo.datamodel.Tweet;

public interface TweetsRepository extends JpaRepository<Tweet, Long> {

}
