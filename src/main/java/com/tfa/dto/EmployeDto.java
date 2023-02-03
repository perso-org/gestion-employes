package com.tfa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeDto {

	private Integer id;
	private String nom;
	private String prenom;
	private String matricule;
	private String email;
	private String departementCode;
	private String organisationCode;
}
