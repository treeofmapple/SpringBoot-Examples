package com.tom.sample.auth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tom.sample.auth.dto.PasswordRequest;
import com.tom.sample.auth.dto.UpdateRequest;
import com.tom.sample.auth.dto.UserResponse;
import com.tom.sample.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("v1/user")
@PreAuthorize("hasRole('USER')")
// @Tag(name = "Anonymous") # Swagger
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	@GetMapping("/me")
    @PreAuthorize("hasAuthority('user:read')")
    public UserResponse getCurrentUser(Principal principal) {
        return service.getCurrentUser(principal);
    }
	
	@PostMapping("/edit-connected")
	@PreAuthorize("hasAuthority('user:create')")
	public ResponseEntity<String> editUser(@RequestBody UpdateRequest request, Principal connectedUser) {
		service.editUser(request, connectedUser);
		return ResponseEntity.status(HttpStatus.OK).body("User edit");
	}

	@PutMapping("/logout")
    @PreAuthorize("hasAuthority('user:update')")
	public ResponseEntity<String> logout(Principal connectedUser) {
		service.logout(connectedUser);
		return ResponseEntity.status(HttpStatus.OK).body("User logout");
	}
	
	@PutMapping("/password/connected")
	@PreAuthorize("hasAuthority('user:update')")
	public ResponseEntity<String> changePassword(@RequestBody PasswordRequest request, Principal connectedUser) {
		service.changePassword(request, connectedUser);
		return ResponseEntity.status(HttpStatus.OK).body("User password was changed");
	}
	
	@PostMapping("/find")
	@PreAuthorize("hasAuthority('user:read')")
	public ResponseEntity <List<UserResponse>> findUser(@RequestParam String userInfo, Principal connectedUser) {
		var data = service.findUser(userInfo, connectedUser);
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	// @PreAuthorize("hasAuthority('user:delete')")
	
}
