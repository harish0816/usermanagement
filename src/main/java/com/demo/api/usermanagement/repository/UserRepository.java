package com.demo.api.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.api.usermanagement.model.User;

/**
 * JPA class for User Entity
 */
public interface UserRepository extends JpaRepository<User, String> {

}
