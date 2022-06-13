package SoulCode.Services.Controllers;


import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import SoulCode.Services.Models.Servico;
import SoulCode.Services.Repositories.ServicoRepository;
import SoulCode.Services.Services.ServicoService;

@RestController
@RequestMapping("servicos")
@CrossOrigin
public class ServicoController {
	
	@Autowired
	ServicoService servicoService;
	
	@GetMapping("/servicos")
	public List<Servico> mostrarTodosOServicos() {
		List<Servico> servicos = servicoService.mostrarTodosOsServicos();
		return servicos;	
	}
	
	@GetMapping("/servicos/{idServico}")
	public ResponseEntity<Servico> mostrarUmServico(@PathVariable Integer idServico) {
		Servico servico = servicoService.mostrarUmServico(idServico);
		return ResponseEntity.ok().body(servico);		
	}
	
	@GetMapping("/servicos/funcionario/{idFuncionario}")
	public List<Servico> mostrarTodosOsServicosDeUmFuncionario(@PathVariable Integer idFuncionario) {
		List<Servico> servicos = servicoService.mostrarTodosOsServicosDeUmFuncionario(idFuncionario);
		return servicos;
	}
	
	/*@GetMapping("/servicos/data")
	public List<Servico> mostrarTodosOsServicosNaData(@RequestParam("dataEntrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataEntrada) {
		List<Servico> servicos = servicoService.mostrarTodosOsServicosNaData(dataEntrada);
		return servicos;
	}*/
	
	@GetMapping("/servicos/data/{dataEntrada}")
	public List<Servico> mostrarTodosOsServicosNaData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataEntrada) {
		List<Servico> servicos = servicoService.mostrarTodosOsServicosNaData(dataEntrada);
		return servicos;
	}
	
	@GetMapping("/servicos/datas")
	public List<Servico> mostrarTodoOsServicosEntreDuasDatas(@RequestParam("data1") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data1, @RequestParam("data2") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data2) {
		List<Servico> servicos = servicoService.mostrarTodoOsServicosEntreDuasDatas(data1, data2);
		return servicos;
	}
	
	@GetMapping("/servicos/status")
	public List<Servico> mostrarTodoOsServicosPeloStatus(@RequestParam("status") String status) {
		List<Servico> servicos = servicoService.mostrarTodoOsServicosPeloStatus(status);
		return servicos;
	}
	
	@GetMapping("/servicos/servicosSemFuncionario")
	public List<Servico> mostrarTodosOsServicosSemFuncionarioAtribuido() {
		List<Servico> servicos = servicoService.mostrarTodosOsServicosSemFuncionarioAtribuido();
		return servicos;
	}
	
	@PostMapping("/servico")
	public ResponseEntity<Servico> cadastrarServico(@RequestBody Servico servico) {
		servico = servicoService.cadastrarServico(servico);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getIdServico()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("servico/atribuirServico/{idServico}/{idFuncionario}")
	public ResponseEntity<Servico> atribuirFuncionarioAUmServico(@PathVariable Integer idServico, @PathVariable Integer idFuncionario) {
		servicoService.atribuirFuncionarioAUmServico(idServico, idFuncionario);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("servico/servicoConcluido/{idServico}")
	public ResponseEntity<Servico> concluirServico(@PathVariable Integer idServico) {
		servicoService.concluirServico(idServico);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("servicos/{idServico}/{idFuncionario}")
	public ResponseEntity<Servico> editarServico(@PathVariable Integer idServico, @PathVariable Integer idFuncionario, @RequestBody Servico servico) {
		servico.setIdServico(idServico);
		servico = servicoService.editarServico(servico, idFuncionario);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("servicos/{idServico}")
	public ResponseEntity<Void> excluirServico(@PathVariable Integer idServico) {
		servicoService.excluirServico(idServico);
		return ResponseEntity.noContent().build();
	}
	

}
