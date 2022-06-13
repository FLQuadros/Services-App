package SoulCode.Services.Services;

import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import SoulCode.Services.Models.Orcamento;
import SoulCode.Services.Models.Servico;
import SoulCode.Services.Models.StatusOrcamento;
import SoulCode.Services.Models.StatusServico;
import SoulCode.Services.Repositories.OrcamentoRepository;
import SoulCode.Services.Repositories.ServicoRepository;

@Service
public class OrcamentoService {
	
	@Autowired
	OrcamentoRepository orcamentoRepository;
	
	@Autowired
	ServicoRepository servicoRepository;
	
	public List<Orcamento> mostrarTodosOsOrcamentos() {
		return orcamentoRepository.findAll();
	}
	
	public Orcamento mostrarUmOrcamento(Integer idOrcamento) {
		Optional<Orcamento> orcamento = orcamentoRepository.findById(idOrcamento);
		return orcamento.orElseThrow();
	}
	
	public List<Orcamento> mostrarTodosOsOrcamentosPeloStatus(String status) {
		return orcamentoRepository.findByStatusDoOrcamento(status);	
	}
	
	public Orcamento cadastrarUmOrcamento(Orcamento orcamento, Integer idServico) {
		Servico servico = servicoRepository.getById(idServico);
		orcamento.setIdOrcamento(idServico);
		orcamento.setStatus(StatusOrcamento.EMITIDO);
		orcamentoRepository.save(orcamento);
		servico.setStatus(StatusServico.RECEBIDO);
		servico.setOrcamento(orcamento);
		servicoRepository.save(servico);
		return orcamento;			
	}
	
	public Orcamento statusOrcamentoQuitado(Integer idOrcamento) {
		Orcamento orcamento = mostrarUmOrcamento(idOrcamento);
		orcamento.setStatus(StatusOrcamento.QUITADO);
		return orcamentoRepository.save(orcamento);
	}
	
	public void exluirOrcamento(Integer idOrcamento) {
		Servico servico = servicoRepository.getById(idOrcamento);
		servico.setOrcamento(null);
		servico.setStatus(StatusServico.ARQUIVADO);
		servicoRepository.save(servico);
		orcamentoRepository.deleteById(idOrcamento);		
	}
	
	public Orcamento editarOrcamento(Orcamento orcamento, Integer idOrcamento) {
		mostrarUmOrcamento(orcamento.getIdOrcamento());
		return orcamentoRepository.save(orcamento);
	}

}
