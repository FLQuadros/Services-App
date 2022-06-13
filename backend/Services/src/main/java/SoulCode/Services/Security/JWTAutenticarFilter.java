package SoulCode.Services.Security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import SoulCode.Services.Data.DetailsUsuarioData;
import SoulCode.Services.Models.UsuarioJWT;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {
	
	public static final int TOKEN_EXPIRACAO = 600_000;
	
	public static final String TOKEN_SENHA = "a02bc3581464789fc921acb0b038f0aef4fea52a9df93a6543f0e623d447f67f4f347b8b2afc4a2539b94e3f473dc45534d21a3980cff2eb71a8bb633c68ae7c";
	
	private final AuthenticationManager authenticationManager;
	
	public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	// O método attemptAuthentication faz a tentativa de autenticação
	// Nesse momento é verificado a autenticidade do username e de password do usuário
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		try {
			UsuarioJWT usuario = new ObjectMapper().readValue(request.getInputStream(), UsuarioJWT.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(), usuario.getPassword(), new ArrayList<>()));
		} catch ( IOException e) {
			throw new RuntimeException("Falha ao tentar autenticar o usuário", e);
		}
	}
	
	@Override
	protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
		
		DetailsUsuarioData usuarioData = (DetailsUsuarioData) authResult.getPrincipal();
		
		String token = JWT.create().withSubject(usuarioData.getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO)).sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, PATCH, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.getWriter().flush();
	}
}
