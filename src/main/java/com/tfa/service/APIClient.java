package com.tfa.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tfa.dto.DepartementDto;

//@FeignClient(url = "http://localhost:8081",value = "DEPARTEMENT-SERVICE")
@FeignClient(name = "SERVICE-DEPARTEMENT")
public interface APIClient {

	@GetMapping("api/departements/code/{code}")
	DepartementDto obtenirDepartement(@PathVariable("code") String code) ;

}
