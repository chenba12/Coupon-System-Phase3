package com.project.CouponSystem.controllers;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.CouponSystem.beans.Coupon;
import com.project.CouponSystem.beans.CouponType;
import com.project.CouponSystem.services.CompanyService;

@RestController
@CrossOrigin(origins ="http://localhost:4200")
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;

	@PostMapping("/createCoupon")
	public ResponseEntity<Object> createCoupon(@RequestParam String token, @RequestBody Coupon coupon) {
		return companyService.createCoupon(token, coupon);
	}

	@DeleteMapping("/deleteCoupon")
	public ResponseEntity<Object> deleteCoupon(@RequestParam String token, @RequestParam long couponId) {
		return companyService.deleteCoupon(token, couponId);
	}

	@PutMapping("/updateCoupon")
	public ResponseEntity<Object> updateCoupon(@RequestParam String token, @RequestBody Coupon coupon) {
		return companyService.updateCoupon(token, coupon);
	}

	@GetMapping("/getCompany")
	public ResponseEntity<Object> getCompany(@RequestParam String token) {
		return companyService.getCompany(token);
	}
	@GetMapping("/getAllCoupon")
	public ResponseEntity<Object> getAllCoupon(@RequestParam String token) {
		return companyService.getAllCoupon(token);
	}

	@GetMapping("/getCouponByType")
	public ResponseEntity<Object> getCouponByType(@RequestParam String token,@RequestParam CouponType couponType) {
		return companyService.getCouponByType(token, couponType);
	}

	@GetMapping("/getCouponByPrice")
	public ResponseEntity<Object> getCouponByPrice(@RequestParam String token,@RequestParam double price) {
		return companyService.getCouponByPrice(token, price);
	}

	@GetMapping("/getCouponByDate")
	public ResponseEntity<Object> getCouponByDate(@RequestParam String token,@RequestParam Date date) {
		return companyService.getCouponByDate(token, date);
	}
	
	@GetMapping("/viewIncome")
	public ResponseEntity<?> viewIncome(@RequestParam String token) {
		return companyService.viewIncome(token);
	}
}
