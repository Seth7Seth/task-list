package br.com.danieleleao.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private IUserRepository userRepository;
	
	
	@PostMapping("/")
	public ResponseEntity create(@RequestBody UserModel userModel) {
		var user = this.userRepository.findByUsername(userModel.getUsername());
		
		if (user != null) {
			System.out.println("Usuário já existe.");
			// Menságem de erro
			// Status Code
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário ja existe");
		}
		//só pra criptografar a senha
		var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
		
		userModel.setPassword(passwordHashed);
		//vai até aqui
		var userCreated = this.userRepository.save(userModel);//Quando o usuário for cadastrado com sucesso.
		return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
	}
}
