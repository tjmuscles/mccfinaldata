package com.bah.metro.repository;

import org.springframework.data.repository.CrudRepository;

import com.bah.metro.domain.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {

}
