package com.assignment.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.dto.UserAgeDTO;
import com.assignment.entities.User;
import com.assignment.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User savedUser = userRepository.save(user);
		return ResponseEntity.ok(savedUser);
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers(@RequestParam("limit") int limit) {
		List<User> users = userRepository.findAll(PageRequest.of(0, limit)).getContent();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/ages")
	public ResponseEntity<List<UserAgeDTO>> getUsersByAgeGreaterThan(@RequestParam("gt") int age) {
		List<User> users = userRepository.findAll();
		List<UserAgeDTO> filteredUsers = new ArrayList<>();

		for (User user : users) {
			int userAge = Period.between(user.getDateOfBirth(), LocalDate.now()).getYears();
			if (userAge > age) {
				UserAgeDTO userAgeDTO = new UserAgeDTO(user.getUsername(), userAge);
				filteredUsers.add(userAgeDTO);
			}
		}

		return ResponseEntity.ok(filteredUsers);
	}

}
