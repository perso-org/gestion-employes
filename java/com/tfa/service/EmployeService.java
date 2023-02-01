package com.tfa.service;

import java.util.List;

import com.tfa.dto.ApiResponseDto;
import com.tfa.dto.EmployeDto;


public interface EmployeService {

	EmployeDto creationEmploye(EmployeDto dto);
	EmployeDto misajourEmployeParMail(EmployeDto dto,String email);
	EmployeDto misajourEmployeParMatricule(EmployeDto dto,String matricule);
	EmployeDto obtenirEmployeParMail(String email);
	ApiResponseDto obtenirEmployeParMatricule(String matricule);
	List<EmployeDto> obtenirEmployeParNom(String nom);
	List<EmployeDto> obtenirEmployes();
	boolean supprimerEmployeParMatricule(String matricule);
}
