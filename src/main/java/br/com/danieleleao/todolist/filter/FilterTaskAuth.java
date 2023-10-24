package br.com.danieleleao.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.danieleleao.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{//Serve pra autenticar o usuário

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		var servletPath = request.getServletPath();
		if (servletPath.startsWith("/tasks/")) {//verificar se é o tasks que está sendo chamado
			
		
		//Pegar autenticação(usuário e senha)
		var authorization = request.getHeader("Authorization");
		
		
		//Substring funciona começando do primeiro caractere até o segundo, quando dessa forma abaixo:
		//authorization.substring(0, 0);
		//Abaixo ela colocou o tamanho do basic + o remover espaço com o trim
		var authEncoded = authorization.substring("basic".length()).trim();
		
		byte[] authDecode = Base64.getDecoder().decode(authEncoded);
		
		var authString = new String(authDecode);
		
		//isso separa o string na parte que tem o : e coloca eles em linhas diferentes 
		String[] credentials = authString.split(":");
		String username = credentials[0];
		String password = credentials[1];
		
		//Validar usuário
			var user = this.userRepository.findByUsername(username);
			if (user == null) {
				response.sendError(401);
			}
			else {
		//Validar senha
		var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
		if (passwordVerify.verified) {
			request.setAttribute("idUser", user.getId());
			filterChain.doFilter(request, response);
		}
		else {
			response.sendError(401);
		}
		//Continuar
		filterChain.doFilter(request, response);
			}
		}
		else {
			filterChain.doFilter(request, response);
		}
	}
}
	

