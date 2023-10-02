package com.securityapi.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {
	
	@GetMapping("/demo-controller")
	  public ResponseEntity<String> sayHello() {
	    return ResponseEntity.ok("Hello from secured endpoint");
	  }

	@GetMapping("/welcome")
	public ResponseEntity<String> sayWelcome() {
		return ResponseEntity.ok("Hi, it's a welcome response");
	}

	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<String> sayUserRole() {
		return ResponseEntity.ok("Hi, it's a Aditya with USER role access");
	}

	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> sayAdminARole() {
		return ResponseEntity.ok("Hi, it's a Aditya with Admin role access");
	}

}
