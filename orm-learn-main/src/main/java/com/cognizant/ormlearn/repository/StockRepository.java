
package com.cognizant.ormlearn.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
	//TODO: Get all Stock Details of Facebook in the month of october 2018
	List<Stock> findByDateBefore(Date date);
}