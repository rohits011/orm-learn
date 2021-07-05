package com.cognizant.ormlearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
	// Include a static reference to CountryService in OrmLearnApplication class
	private static CountryService countryService;

	@Autowired
	StockRepository stockRepository;

	private static EmployeeService employeeService;
	private static DepartmentService departmentService;
	private static SkillService skillService;

	public static void main(String[] args) throws Exception {

		ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
		// Include logs for verifying if main() is called..
		LOGGER.info("Inside main!!");

		countryService = context.getBean(CountryService.class);
		employeeService = context.getBean(EmployeeService.class);
		departmentService = context.getBean(DepartmentService.class);
		skillService = context.getBean(SkillService.class);

		testGetAllCountries();

		getAllCountriesTest();

		testAddCountry();

		testUpdateCountry();

		testDeleteCountry();

		testFindCountryNameLike();

		testFindAllByNameLikeAsc();

		testFindByNameStartingWith();

		testGetEmployee();

		testAddEmployee();

		testUpdateEmployee();

		testGetDepartment();

		testAddSkillToEmployee();

		testGetAllPermanentEmployees();

		testGetAverageSalary();

		testGetAllEmployeesNative();
	}

	private static void testGetAllCountries() {
		LOGGER.info("Start");
		List<Country> countries = countryService.getAllCountries();
		LOGGER.debug("countries={}", countries);
		LOGGER.info("End");
	}

	private static void getAllCountriesTest() throws CountryNotFoundException {
		Country country = countryService.findCountryByCode("YE");
		LOGGER.debug("Country:{}", country);
		LOGGER.info("End");
	}

	private static void testAddCountry() throws CountryNotFoundException {
		Country country = new Country();
		country.setCode("IN");
		country.setName("INDIA");
		countryService.addCountry(country);
		countryService.findCountryByCode("IN");
	}

	private static void testUpdateCountry() {
		countryService.updateCountry("IND", "IN");
	}

	private static void testDeleteCountry() {
		countryService.removeCountry("AS");
	}

	private static void testFindCountryNameLike() {
		LOGGER.info("Start!!");
		List<Country> countries = countryService.findByNameLike("%ou%");
		LOGGER.debug("Countries:{}", countries);
	}

	private static void testFindAllByNameLikeAsc() {
		LOGGER.info("Start!!");
		List<Country> countries = countryService.findAllByNameLikeOrderByNameAsc("%ou%");
		LOGGER.debug("Countries:{}", countries);
	}

	private static void testFindByNameStartingWith() {
		LOGGER.info("Start!!");
		List<Country> countries = countryService.findByNameStartsWith("Z");
		LOGGER.debug("Countries:{}", countries);
	}

	private void testFindByDateBefore() throws ParseException {
		LOGGER.info("Start!!");
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-11-01");
		
		LOGGER.debug("Stock: {}", stockRepository);
		List<Stock> stocks = stockRepository.findByDateBefore(date);
		LOGGER.debug("Stocks:{}", stocks);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(stockRepository);

	}

	private static void testGetEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(1);
		LOGGER.debug("Employee:{}", employee);
		LOGGER.debug("Department:{}", employee.getDepartment());
		LOGGER.debug("Skills:{}", employee.getSkillList());
		LOGGER.info("End");
	}

	private static void testAddEmployee() throws ParseException {
		Employee employee = new Employee();
		employee.setId(5);
		employee.setName("Arpit");
		employee.setSalary(28500);
		employee.setPermanent(true);
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-08-15");
		employee.setDateOfBirth(date);
		employee.setDepartment(employeeService.get(1).getDepartment());
		employeeService.save(employee);
		Employee employee1 = employeeService.get(5);
		LOGGER.debug("Employee:{}", employee1);
		LOGGER.info("End!!");
	}

	private static void testUpdateEmployee() {
		LOGGER.info("Start");
		Employee employee = employeeService.get(1);
		Department department = departmentService.get(3);
		employee.setDepartment(department);
		employeeService.save(employee);
		LOGGER.debug("Employee:{}", employee);
		LOGGER.debug("Department:{}", employee.getDepartment());
		LOGGER.info("End");
	}

	private static void testGetDepartment() {
		LOGGER.info("Start!!");

		Department department = departmentService.get(3);

		LOGGER.debug("Department:{}", department);

		LOGGER.debug("Department:{}", department.getEmployeeList());
		LOGGER.info("End!!!");
	}

	private static void testAddSkillToEmployee() {
		LOGGER.info("Start!!");
		Employee employee = employeeService.get(1);
		Skill skill = skillService.get(2);

		employee.getSkillList().add(skill);

		employeeService.save(employee);
		LOGGER.debug("Skills:{}", employee.getSkillList());
		LOGGER.info("End...");
	}

	public static void testGetAllPermanentEmployees() {
		LOGGER.info("Start");
		List<Employee> employees = employeeService.getAllPermanentEmployees();
		LOGGER.debug("Permanent Employees:{}", employees);
		employees.forEach(e -> LOGGER.debug("Skills:{}", e.getSkillList()));
		LOGGER.info("End");
	}

	private static void testGetAverageSalary() {
		LOGGER.debug("Average Salary:{}", employeeService.getAverageSalary());
		LOGGER.info("End!!!");
	}

	private static void testGetAllEmployeesNative() {
		LOGGER.debug("Employees: {}", employeeService.getAllEmployeesNative());
		LOGGER.info("End!!!");
	}
}