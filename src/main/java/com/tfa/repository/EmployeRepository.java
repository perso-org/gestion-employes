package com.tfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tfa.entite.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Integer>{

	Employe findByEmail(String email);
	List<Employe> findByNom(String nom);
	Employe findByMatricule(String matricule);
}
