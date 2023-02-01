package com.tfa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tfa.dto.ApiResponseDto;
import com.tfa.dto.EmployeDto;
import com.tfa.service.EmployeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/employes")
@RequiredArgsConstructor
public class EmployeController {

	private final EmployeService service;
	
	@PostMapping("creation")
	public ResponseEntity<EmployeDto> creationEmploye(@RequestBody EmployeDto dto) {
		
		EmployeDto employeDto = service.creationEmploye(dto);
		
		return new ResponseEntity<>(employeDto,HttpStatus.CREATED);
	}
	
	@PatchMapping("mail/{email}")
	public ResponseEntity<EmployeDto> misajourEmployeParMail(@RequestBody EmployeDto dto, @PathVariable String email) {
		
		EmployeDto dto2 = service.misajourEmployeParMail(dto, email);
		if(dto2 == null)
			throw new RuntimeException(String.format("Aucun employé n'est associé au mail %s",email));
		return ResponseEntity.ok(dto2);
	}
	
	@PatchMapping("mtl/{matricule}")
	public ResponseEntity<EmployeDto>  misajourEmployeParMatricule(@RequestBody EmployeDto dto,@PathVariable String matricule) {
		EmployeDto dto2 = service.misajourEmployeParMatricule(dto, matricule);
		if(dto2 == null)
			throw new RuntimeException(String.format("Aucun employé n'est associé au matricule %s",matricule));
		return ResponseEntity.ok(dto2);
	}
	
	@GetMapping("mail/{mail}")
	public ResponseEntity<EmployeDto> obtenirEmployeParMail(@PathVariable String email) {
		EmployeDto dto2 = service.obtenirEmployeParMail(email);
		if(dto2 == null)
			throw new RuntimeException(String.format("Aucun employé n'est associé au mail %s",email));
		return ResponseEntity.ok(dto2);
	}
	
	@GetMapping("mtl/{matricule}")
	public ResponseEntity<ApiResponseDto> obtenirEmployeParMatricule(@PathVariable String matricule) {
		ApiResponseDto dto2 = service.obtenirEmployeParMatricule(matricule);
		if(dto2 == null)
			throw new RuntimeException(String.format("Aucun employé n'est associé au matricule %s",matricule));
		return ResponseEntity.ok(dto2);
	}
	
	@GetMapping("emps/{nom}")
	public ResponseEntity<List<EmployeDto>> obtenirEmployeParNom(@PathVariable String nom) {
		List<EmployeDto> dtos = service.obtenirEmployeParNom(nom);
		
		if(CollectionUtils.isEmpty(dtos))
			throw new RuntimeException(String.format("Aucun employé n'est associé au nom %s",nom));
		
		return ResponseEntity.ok(dtos);
		
	}
	
	@GetMapping
	public ResponseEntity<List<EmployeDto>> obtenirEmployes() {
		List<EmployeDto> dtos = service.obtenirEmployes();
		
		if(CollectionUtils.isEmpty(dtos))
			throw new RuntimeException("Il n'existe pas d'employés");
		
		return ResponseEntity.ok(dtos);
	}
	@DeleteMapping("delete/{matricule}")
	public ResponseEntity<String> supprimerEmployeParMatricule(@PathVariable String matricule) {
		boolean isSupprime = service.supprimerEmployeParMatricule(matricule);
		
		if(!isSupprime)
			throw new RuntimeException(String.format("Aucun employé n'est associé au matricule %s",matricule));
		return ResponseEntity.ok(String.format("L'employé %s a été supprimé avec succès!", matricule));
		
	}
}
