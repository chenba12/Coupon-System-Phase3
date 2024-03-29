package com.project.CouponSystem.services;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.project.CouponSystem.beans.ClientType;
import com.project.CouponSystem.beans.Company;
import com.project.CouponSystem.beans.Customer;
import com.project.CouponSystem.repo.CompanyRepo;
import com.project.CouponSystem.repo.CustomerRepo;

@Service
@Transactional
public class AdminService implements CouponClient {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private IncomeService incomeService;

	private Map<String, Long> tokens = new Hashtable<>();

	@Override
	public ResponseEntity<?> login(String name, String password, ClientType clientType) {
		if (name.equalsIgnoreCase("admin")) {
			if (password.equalsIgnoreCase("1234")) {
				String token = UUID.randomUUID().toString();
				tokens.put(token, 1L);
				return ResponseEntity.ok(token);
			}
		}
		return ResponseEntity.badRequest().body("User name or password incorrect");
	}

	@Override
	public ResponseEntity<?> logout(String token) {
		if (tokens.containsKey(token)) {
			tokens.remove(token);
			return ResponseEntity.ok("Logged out successfully");
		}
		return ResponseEntity.badRequest().body("Token Doesnt exist");
	}

	public ResponseEntity<Object> getAllCompanies(String token) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyRepo.findAll());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyRepo.findCompanyById(companyId));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> addCompany(String token, Company company) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(companyRepo.save(company));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> deleteCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			Optional<Company> cOptional = companyRepo.findById(companyId);
			if (cOptional.isPresent()) {
				companyRepo.deleteById(companyId);
				return ResponseEntity.ok("Company with id " + companyId + " have been deleted");
			}
			return ResponseEntity.badRequest().body("No company with id " + companyId + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> updateCompany(String token, Company company) {
		if (tokens.containsKey(token)) {
			Optional<Company> cOptional = companyRepo.findById(company.getId());
			if (cOptional.isPresent()) {
				company.setCouponsCollection(cOptional.get().getCouponsCollection());
				return ResponseEntity.ok(companyRepo.save(company));
			}
			return ResponseEntity.badRequest().body("No company with id " + company.getId() + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getAllCustomers(String token) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(customerRepo.findAll());
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> getCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			Customer customer=customerRepo.findCustomerById(customerId);
			if (customer!=null) {
				return ResponseEntity.ok(customer);
			} else {
				return ResponseEntity.badRequest().body("No customer found");
			}
			
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> addCustomer(String token, Customer customer) {
		if (tokens.containsKey(token)) {
			return ResponseEntity.ok(customerRepo.save(customer));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> deleteCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			Optional<Customer> cOptional = customerRepo.findById(customerId);
			if (cOptional.isPresent()) {
				customerRepo.deleteById(customerId);
				return ResponseEntity.ok("Customer with id " + customerId + " have been deleted");
			}
			return ResponseEntity.badRequest().body("No customer with id " + customerId + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<Object> updateCustomer(String token, Customer customer) {
		if (tokens.containsKey(token)) {
			Optional<Customer> cOptional = customerRepo.findById(customer.getId());
			if (cOptional.isPresent()) {
				customer.setCouponsCollection(cOptional.get().getCouponsCollection());
				return ResponseEntity.ok(customerRepo.save(customer));
			}
			return ResponseEntity.badRequest().body("No customer with id " + customer.getId() + " found");
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewAllIncome(String token) {
		if (tokens.containsKey(token)) {
			return incomeService.viewAllIncome();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewIncomeByCustomer(String token, long customerId) {
		if (tokens.containsKey(token)) {
			return incomeService.viewIncomeByCustomer(customerId);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

	public ResponseEntity<?> viewIncomeByCompany(String token, long companyId) {
		if (tokens.containsKey(token)) {
			return incomeService.viewIncomeByCompany(companyId);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please login");
	}

}
