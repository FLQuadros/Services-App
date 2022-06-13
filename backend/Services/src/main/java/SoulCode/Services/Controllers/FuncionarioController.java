package SoulCode.Services.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import SoulCode.Services.Models.Funcionario;
import SoulCode.Services.Services.FuncionarioService;



@CrossOrigin
@RestController
@RequestMapping("servicos")
public class FuncionarioController {
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@GetMapping("/funcionarios")
	public List<Funcionario> mostrarTodosOsFuncionarios() {
		List<Funcionario> funcionarios = funcionarioService.mostrarTodosOsFuncionarios();
		return funcionarios;
	}
	
	@GetMapping("/funcionarios/{idFuncionario}")
	public ResponseEntity<Funcionario> mostrarUmFuncionario(@PathVariable Integer idFuncionario) {
		Funcionario funcionario = funcionarioService.mostrarUmFuncionario(idFuncionario);
		return ResponseEntity.ok().body(funcionario);
	}
	
	@GetMapping("/funcionarios/email/{email}")
	public ResponseEntity<Funcionario> mostraUmFuncionarioPeloEmail(@PathVariable String email) {
		Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloEmail(email);
		return ResponseEntity.ok().body(funcionario);
	}
	
	@GetMapping("/funcionarios/nome/{nome}")
	public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloNome(@PathVariable String nome) {
		Funcionario funcionario = funcionarioService.mostrarUmFuncionarioPeloNome(nome);
		return ResponseEntity.ok().body(funcionario);
	}
	
	@GetMapping("/funcionarios/nome&email/{nome}/{email}")
	public ResponseEntity<Funcionario> mostrarUmFuncionarioPeloNomeEEmail(@PathVariable String nome, @PathVariable String email) {
		Funcionario funcionario = funcionarioService.mostrarUmFuncioanrioPeloNomeEEmail(nome, email);
		return ResponseEntity.ok().body(funcionario); 
	}
	
	@PostMapping("/funcionarios")
	public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
		funcionario = funcionarioService.cadastrarFuncionario(funcionario);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(funcionario.getIdFuncionario()).toUri();
		return ResponseEntity.created(uri).body(funcionario);
	}
	
	@PutMapping("/funcionarios/{idFuncionario}")
	public ResponseEntity<Funcionario> editarFuncionario(@PathVariable Integer idFuncionario, @RequestBody Funcionario funcionario) {
		funcionario.setIdFuncionario(idFuncionario);
		funcionario = funcionarioService.editarFuncionario(funcionario);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/funcionarios/{idFuncionario}")
	public ResponseEntity<Void> excluirFuncionario(@PathVariable Integer idFuncionario) {
		funcionarioService.excluirFuncionario(idFuncionario);
		return ResponseEntity.noContent().build();
	}
}
