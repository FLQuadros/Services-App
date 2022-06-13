package SoulCode.Services.Services;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import SoulCode.Services.Data.DetailsUsuarioData;
import SoulCode.Services.Models.UsuarioJWT;
import SoulCode.Services.Repositories.UsuarioJWTRepository;

@Service
public class DetailsUsuarioServiceImpl implements UserDetailsService {
	
	private final UsuarioJWTRepository usuarioJWTRepository;
	
	public DetailsUsuarioServiceImpl(UsuarioJWTRepository usuarioJWTRepository) {
		this.usuarioJWTRepository = usuarioJWTRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioJWT> usuario = usuarioJWTRepository.findByLogin(username);
		if ( usuario.isEmpty() ) {
			throw new UsernameNotFoundException("Usuário não cadastrado.");
			
		}
		return new DetailsUsuarioData(usuario);
	}

}
