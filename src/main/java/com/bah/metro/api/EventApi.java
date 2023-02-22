package com.bah.metro.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.bah.metro.domain.Event;
import com.bah.metro.repository.CustomersRepository;
import com.bah.metro.repository.EventRepository;

@RestController
@RequestMapping("/events")
public class EventApi {

	@Autowired
	EventRepository repo;

	@GetMapping
	public Iterable<Event> getAllEvents() {
		return repo.findAll();
	}

	@GetMapping("/{eventId}")
	public Optional<Event> getEventById(@PathVariable("eventId") long id) {
		return repo.findById(id);
	}

	@PostMapping
	public ResponseEntity<?> addEvent(@RequestBody Event newEvent, UriComponentsBuilder uri) {
		if (newEvent.getId() != 0 || newEvent.getCode() == null || newEvent.getTitle() == null) {
			return ResponseEntity.badRequest().build();
		}
		newEvent = repo.save(newEvent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEvent.getId())
				.toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<?> putEvent(@RequestBody Event newEvent, @PathVariable("eventId") long eventId) {
		if (newEvent.getId() != eventId || newEvent.getCode() == null || newEvent.getTitle() == null) {
			return ResponseEntity.badRequest().build();
		}
		newEvent = repo.save(newEvent);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteEventById(@PathVariable("eventId") long id) {

		repo.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// PROJECT DAY 1
//	ArrayList<Event> events = new ArrayList<Event>() {
//		{
//		add(new Event(1, "CNF001", "All-Java Conference", "Lectures and exhibits covering all Java topics"));
//		add(new Event(2, "WKS002", "Spring Boot Workshop", "Hands-on Spring Boot Workshop"));
//		add(new Event(3, "TRN001", "Angular Training Course", "Five day introductory training in Angular"));
//		add(new Event(4, "RNR004", "Rock n Roll Concert", "BAH Employees RocknRoll Concert"));
//		}
//	};
//
//	
//	@GetMapping
//	public List<Event> getAllEvents() {
//		return events;
//	}
//	
//
//	public List<Event> getAll() {
//		return this.events;
//	}
//	
//	@GetMapping("/{eventId}")
//	public Event getCustomerById(@PathVariable("eventId") long id) {
//		return this.getAllEvents().stream().filter(event-> event.getId()==id).findFirst().orElse(null);
//	}

}
