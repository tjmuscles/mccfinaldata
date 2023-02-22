package com.bah.metro.repository;

import org.springframework.data.repository.CrudRepository;

import com.bah.metro.domain.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

}
