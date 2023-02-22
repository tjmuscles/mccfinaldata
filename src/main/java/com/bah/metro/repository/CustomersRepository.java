package com.bah.metro.repository;

import org.springframework.data.repository.CrudRepository;

import com.bah.metro.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

}
