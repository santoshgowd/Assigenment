package com.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
