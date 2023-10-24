package br.com.danieleleao.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "td_users")
public class UserModel {
	//Tudo que tiver aqui, ele vai entender como o nome de uma tabela
	@Id
	@GeneratedValue(generator = "UUID")
	private UUID id;
	
	@Column(unique = true)//define as colunas abaixo, como sendo de um valor que não se repete nunca
	private String username;
	private String name;
	private String password;
	
	@CreationTimestamp
	
	private LocalDateTime createdAt; 
}
