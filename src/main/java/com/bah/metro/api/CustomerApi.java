package com.bah.metro.api;

import java.net.URI;
import java.util.Iterator;
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

import com.bah.metro.domain.Customer;
import com.bah.metro.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerApi {
	
	@Autowired
	CustomersRepository repo;
	
	@GetMapping
	public Iterable<Customer> getAll() {
		return repo.findAll();
	}

	@GetMapping("/{customerId}")
	public Optional<Customer> getCustomerById(@PathVariable("customerId") long id) {
		return repo.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
		if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getPassword() == null) { 
			return ResponseEntity.badRequest().build();
		}
		newCustomer = repo.save(newCustomer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newCustomer.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}

	//lookupCustomerByName GET
	@GetMapping("/byname/{username}")
	public ResponseEntity<?> lookupCustomerByNameGet(@PathVariable("username") String username,
			UriComponentsBuilder uri) {
		Customer customer = null;
		Iterator<Customer> customers = repo.findAll().iterator();
		
		while(customers.hasNext()) {
			customer = customers.next();
			if(customer.getName().equalsIgnoreCase(username)) {
				return ResponseEntity.ok(customer);
			}
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();	
	}
	
	//lookupCustomerByName POST
	@PostMapping("/byname")
	public ResponseEntity<?> lookupCustomerByNamePost(@RequestBody Customer custReq, UriComponentsBuilder uri) {
		Customer customer = null;
		Iterator<Customer> customers = repo.findAll().iterator();
		
		while(customers.hasNext()) {
			customer = customers.next();
			if(customer.getName().equals(custReq.getName())) {
				return ResponseEntity.ok(customer);
			}
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}	
	
	
	@PutMapping("/{customerId}")
	public ResponseEntity<?> putCustomer(@RequestBody Customer newCustomer,
			@PathVariable("customerId") long customerId) {
		
		if (newCustomer.getId()!=customerId
			|| newCustomer.getName()==null
			|| newCustomer.getPassword()==null
			|| newCustomer.getEmail() == null) {
			return ResponseEntity.badRequest().build();
		}
		
		newCustomer=repo.save(newCustomer);
		return ResponseEntity.ok().build();
	}	
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") long id) {

		repo.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}	
	
	
	
	
	
	//PROJECT DAY 1
//	ArrayList<Customer> customers = new ArrayList<Customer>() {
//		{
//			add(new Customer(1, "Tim", "Tim@bah.com", "password"));
//			add(new Customer(2, "Haniah", "Haniah@bah.com", "password"));
//			add(new Customer(3, "Mario", "Mario@bah.com", "password"));
//			add(new Customer(4, "Luigi", "Luigi@bah.com", "password"));
//			add(new Customer(5, "StarFox", "Starfox@bah.com", "password"));
//		}
//	};
//
//	@GetMapping
//	public List<Customer> getAllCustomers() {
//		return customers;
//	}
//	
//	@GetMapping("/{id}")
//	public Customer getCustomerByID(@PathVariable("id") int id) {
//		return customers.stream().filter(customer -> customer.getId()==id).findFirst().orElse(null);
//	}
	
	
}
