package com.cognizant.ormlearn.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	EmployeeRepository employeeRepository;

	

	@Transactional
	public Employee get(int id) {
		LOGGER.info("Start!!");
		return employeeRepository.findById(id);
	}

	@Transactional
	public void save(Employee employee) {
		LOGGER.info("Start");
		employeeRepository.save(employee);
		LOGGER.info("End");
	}

	@Transactional
	public List<Employee> getAllPermanentEmployees() {
		LOGGER.info("Start!!");
		return employeeRepository.getAllPermanentEmployees();
	}

	@Transactional
	public double getAverageSalary() {
		LOGGER.info("Start!!");
		return employeeRepository.getAverageSalary(3);
	}

	@Transactional
	public List<Employee> getAllEmployeesNative() {
		LOGGER.info("Start!!");
		return employeeRepository.getAllEmployeesNative();
	}
}