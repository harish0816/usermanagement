package com.demo.api.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.api.usermanagement.model.UserDetails;

/**
 * JPA class for User Entity
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, String> {

}
