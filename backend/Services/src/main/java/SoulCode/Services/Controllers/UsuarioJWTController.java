package SoulCode.Services.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SoulCode.Services.Models.UsuarioJWT;
import SoulCode.Services.Repositories.UsuarioJWTRepository;
import SoulCode.Services.Services.UsuarioJWTService;


@RestController
@CrossOrigin
@RequestMapping("/servicos")
public class UsuarioJWTController {
	
	@Autowired
	UsuarioJWTService usuarioJWTService;
	
	@Autowired
	UsuarioJWTRepository usuarioJWTRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	
	@GetMapping("/usuarioJWT")
	public ResponseEntity<List<UsuarioJWT>> mostrarTodosOsUsuariosJWT() {
		return ResponseEntity.ok().body(usuarioJWTService.listarUsuarioJWT());
	}
	
	@PostMapping("/usuarioJWT")
	public ResponseEntity<UsuarioJWT> cadastrarUsuarioJWT(@RequestBody UsuarioJWT usuarioJWT) {
		usuarioJWT.setPassword(encoder.encode(usuarioJWT.getPassword()));
		return ResponseEntity.ok().body(usuarioJWTService.cadastrarUsuarioJWT(usuarioJWT));
	}
}
