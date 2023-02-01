package com.tfa.service.Impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.tfa.dto.ApiResponseDto;
import com.tfa.dto.DepartementDto;
import com.tfa.dto.EmployeDto;
import com.tfa.entite.Employe;
import com.tfa.repository.EmployeRepository;
import com.tfa.service.APIClient;
import com.tfa.service.EmployeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeServiceImpl implements EmployeService {

	private final EmployeRepository repo;
	private final ModelMapper mapper;
	private final RestTemplate restTemplate;
	private final WebClient client;
	private final APIClient apiClient;
	
	@Override
	public EmployeDto creationEmploye(EmployeDto dto) {
		
		log.info("Creation d'un employé...");
		Employe employe = mapper.map(dto, Employe.class);
		
		Employe employeSaved = repo.save(employe);
		log.info("Employé créé avec succès!");
		
		return mapper.map(employeSaved, EmployeDto.class);
	}

	@Override
	public EmployeDto misajourEmployeParMail(EmployeDto dto, String email) {
		Objects.requireNonNull(email);
		Employe employe = repo.findByEmail(email);
		
		if(employe == null) {
			 return null;
		}
		modificationEntite(employe, dto);
		Employe emp = repo.save(employe);
		log.info("Employé avec l'email {} mis à jour avec succès!",email);
		return mapper.map(emp, EmployeDto.class);
	}

	@Override
	public EmployeDto misajourEmployeParMatricule(EmployeDto dto, String matricule) {
		Objects.requireNonNull(matricule);
		Employe employe = repo.findByMatricule(matricule);
		
		if(employe == null) {
			 return null;
		}
		modificationEntite(employe, dto);
		Employe emp = repo.save(employe);
		log.info("Employé {} mis à jour avec succès!",matricule);
		return mapper.map(emp, EmployeDto.class);
	}

	private void modificationEntite(Employe employe,EmployeDto dto) {
		employe.setNom(StringUtils.isNotBlank(dto.getNom()) ? dto.getNom() : employe.getNom());
		employe.setPrenom(StringUtils.isNotBlank(dto.getPrenom()) ? dto.getPrenom() : employe.getPrenom());
		employe.setEmail(StringUtils.isNotBlank(dto.getEmail()) ? dto.getEmail() : employe.getEmail());
		employe.setMatricule(StringUtils.isNotBlank(dto.getMatricule()) ? dto.getMatricule() : employe.getMatricule());
		employe.setDepartementCode(StringUtils.isNotBlank(dto.getDepartementCode()) ? dto.getMatricule() : employe.getDepartementCode());
	}

	@Override
	public EmployeDto obtenirEmployeParMail(String email) {
		Objects.requireNonNull(email);
		Employe employe = repo.findByEmail(email);
		
		if(employe == null) {
			 return null;
		}
	
		return mapper.map(employe, EmployeDto.class);
	}

	@Override
	public ApiResponseDto obtenirEmployeParMatricule(String matricule) {
		Objects.requireNonNull(matricule);
		Employe employe = repo.findByMatricule(matricule);

		if (employe == null) {
			return null;
		}
		EmployeDto dto = mapper.map(employe, EmployeDto.class);
		// ResponseEntity<DepartementDto> depart =
		// restTemplate.getForEntity("http://localhost:8081/api/departements/code/"+dto.getDepartementCode(),DepartementDto.class);
		/*
		 * DepartementDto depart = client.get()
		 * .uri("http://localhost:8081/api/departements/code/" +
		 * dto.getDepartementCode()) .retrieve() .bodyToMono(DepartementDto.class)
		 * .block();
		 */
		DepartementDto depart = apiClient.obtenirDepartement(dto.getDepartementCode());
		return ApiResponseDto.builder().employe(dto).departement(depart).build();
	}

	@Override
	public List<EmployeDto> obtenirEmployeParNom(String nom) {

		Objects.requireNonNull(nom);
		
		List<Employe> employes = repo.findByNom(nom);
		if(CollectionUtils.isEmpty(employes)) {
			return Collections.emptyList();
		}
		
		return employes.stream().map(emp -> mapper.map(emp, EmployeDto.class)).toList();
	}

	@Override
	public List<EmployeDto> obtenirEmployes() {
		List<Employe> employes = repo.findAll();
		if(CollectionUtils.isEmpty(employes)) {
			return Collections.emptyList();
		}
		
		return employes.stream().map(emp -> mapper.map(emp, EmployeDto.class)).toList();
	}

	@Override
	public boolean supprimerEmployeParMatricule(String matricule) {
		Objects.requireNonNull(matricule);
		Employe employe = repo.findByMatricule(matricule);
		
		if(employe == null) {
			 return false;
		}
		repo.delete(employe);
		log.info("Employé {} supprimé avec succès!",matricule);
		return true;
	}

}
