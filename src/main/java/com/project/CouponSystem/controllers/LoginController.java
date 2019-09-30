package com.project.CouponSystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.CouponSystem.beans.ClientType;
import com.project.CouponSystem.services.AdminService;
import com.project.CouponSystem.services.CompanyService;
import com.project.CouponSystem.services.CustomerService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	public ResponseEntity<?> login(@RequestParam String userName, @RequestParam String password,
			@RequestParam ClientType clientType) {
		switch (clientType) {
		case ADMIN:
			return adminService.login(userName, password, clientType);

		case COMPANY:
			return companyService.login(userName, password, clientType);

		case CUSTOMER:
			return customerService.login(userName, password, clientType);
		}
		return ResponseEntity.badRequest().body("Something went wrong");
	}
	
	public ResponseEntity<?> logout(@RequestParam String token,@RequestParam ClientType clientType) {
		switch (clientType) {
		case ADMIN:
			return adminService.logout(null);

		case COMPANY:
			return companyService.logout(null);

		case CUSTOMER:
			return customerService.logout(null);
		}
		return ResponseEntity.badRequest().body("Something went wrong");
	}

}