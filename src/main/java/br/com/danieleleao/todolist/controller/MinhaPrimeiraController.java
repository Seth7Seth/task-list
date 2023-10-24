package br.com.danieleleao.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primeiraRota")
//http://localhost:8080/
public class MinhaPrimeiraController {

    /**
	     
	GET buscar uma informação
	POST adicionar um dado
	 
	PUT alterar um dado
	DELETE remover um dado
	PATCH alterar somente uma parte da informacao/dado
	*/
	
	@GetMapping("/primeiroMetodo")
	public String primeiraMensagem() {
	    return "<h1>Funcionou</h1>";
	}

}