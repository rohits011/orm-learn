package com.cognizant.ormlearn.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import com.cognizant.ormlearn.model.Country;

@Service
public class CountryService {
	@Autowired
	private CountryRepository repo;

	@Transactional
	public List<Country> getAllCountries() {
		return repo.findAll();
	}

	@Transactional
	public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
		Optional<Country> result = repo.findById(countryCode);
		if (!result.isPresent()) {
			throw new CountryNotFoundException("Could not find the country");
		}
		Country country = result.get();
		return country;
	}

	@Transactional
	public void addCountry(Country c) {
		repo.save(c);
	}

	@Transactional
	public void updateCountry(String countryName, String countryCode) {
		Optional<Country> op = repo.findById(countryCode);
		op.get().setName(countryName);
		repo.save(op.get());

	}

	@Transactional
	public void removeCountry(String cid) {
		repo.deleteById(cid);

	}
	
	@Transactional
	public List<Country> findByNameLike(String countryNameSubString) {
		return repo.findAllByNameLike(countryNameSubString);
	}
	
	
	@Transactional
	public List<Country> findAllByNameLikeOrderByNameAsc(String countryNameSubString) {
		return repo.findAllByNameLikeOrderByNameAsc(countryNameSubString);
	}
	
	@Transactional
	public List<Country> findByNameStartsWith(String alphabet) {
		return repo.findByNameStartsWith(alphabet);
	}

}
