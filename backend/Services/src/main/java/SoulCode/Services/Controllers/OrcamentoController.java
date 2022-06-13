package SoulCode.Services.Controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
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

import SoulCode.Services.Models.Orcamento;
import SoulCode.Services.Services.OrcamentoService;

@RestController
@RequestMapping("servicos")
@CrossOrigin
public class OrcamentoController {
	
	@Autowired
	OrcamentoService orcamentoService;
	
	@GetMapping("/orcamentos")
	public List<Orcamento> mostrarTodosOsOrcamentos() {
		List<Orcamento> orcamentos = orcamentoService.mostrarTodosOsOrcamentos();
		return orcamentos;
	}
	
	@GetMapping("/orcamentos/{idOrcamento}")
	public ResponseEntity<Orcamento> mostrarUmOrcamento(@PathVariable Integer idOrcamento) {
		Orcamento orcamento = orcamentoService.mostrarUmOrcamento(idOrcamento);
		return ResponseEntity.ok().body(orcamento);
	}
	
	@GetMapping("/orcamentos/status")
	public List<Orcamento> mostrarTodosOsOrcamentosPeloStatus(@RequestParam("status") String status) {
		List<Orcamento> orcamentos = orcamentoService.mostrarTodosOsOrcamentosPeloStatus(status);
		return orcamentos;
	}
	
	@PostMapping("/orcamentos/orcamento/{idServico}")
	public ResponseEntity<Orcamento> cadastrarUmOrcamento(@PathVariable Integer idServico, @RequestBody Orcamento orcamento) {
		orcamento = orcamentoService.cadastrarUmOrcamento(orcamento, idServico);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orcamento.getIdOrcamento()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PostMapping("/orcamentos/quitacao/{idOrcamento}")
	public ResponseEntity<Void> statusOrcamentoQuitad(@PathVariable Integer idOrcamento) {
		orcamentoService.statusOrcamentoQuitado(idOrcamento);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/orcamentos/{idOrcamento}")
	public ResponseEntity<Void> excluirOrcamento(@PathVariable Integer idOrcamento) {
		orcamentoService.exluirOrcamento(idOrcamento);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/orcamentos/{idOrcamento}")
	public ResponseEntity<Orcamento> editarOrcamento(@PathVariable Integer idOrcamento, @RequestBody Orcamento orcamento) {
		orcamento.setIdOrcamento(idOrcamento);
		orcamentoService.editarOrcamento(orcamento, idOrcamento);
		return ResponseEntity.ok().body(orcamento);
	}

}
