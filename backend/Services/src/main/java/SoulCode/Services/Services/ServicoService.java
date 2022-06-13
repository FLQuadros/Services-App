package SoulCode.Services.Services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SoulCode.Services.Models.Funcionario;
import SoulCode.Services.Models.Servico;
import SoulCode.Services.Models.StatusServico;
import SoulCode.Services.Repositories.FuncionarioRepository;
import SoulCode.Services.Repositories.ServicoRepository;

@Service
public class ServicoService {
	
	// injeção de dependência
	@Autowired
	ServicoRepository servicoRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	public List<Servico> mostrarTodosOsServicos() {
		return servicoRepository.findAll();
	}
	
	public Servico mostrarUmServico(Integer idServico) {
		Optional<Servico> servico = servicoRepository.findById(idServico);
		return servico.orElseThrow();
	}
	
	public List<Servico> mostrarTodosOsServicosDeUmFuncionario(Integer idFuncionario) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
		return servicoRepository.findByFuncionario(funcionario);
	}
	
	public List<Servico> mostrarTodosOsServicosNaData(Date dataEntrada) {
		return servicoRepository.findByDataEntrada(dataEntrada);
	}
	
	public List<Servico> mostrarTodoOsServicosEntreDuasDatas(Date data1, Date data2) {
		return servicoRepository.findByIntervaloData(data1, data2);
	}
	
	public List<Servico> mostrarTodoOsServicosPeloStatus(String status) {
		return servicoRepository.findByStatusDoServico(status);
	}
	
	public List<Servico> mostrarTodosOsServicosSemFuncionarioAtribuido() {
		return servicoRepository.findByIdFuncionarioNull();
	}
	
	public Servico cadastrarServico(Servico servico) {
		servico.setIdServico(null);
		servico.setStatus(StatusServico.RECEBIDO);
		servico.setFuncionario(null);
		return servicoRepository.save(servico);
		
	}
	
	public Servico atribuirFuncionarioAUmServico(Integer idServico, Integer idFuncionario) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
		Servico servico = this.mostrarUmServico(idServico);
		
		if (servico.getFuncionario() != null) {
			servico.setIdServico(idServico);
			servico.setFuncionario(funcionario.get());
			servico.setStatus(StatusServico.ATRIBUIDO);
		}
		return servicoRepository.save(servico);
	}
	
	public Servico concluirServico(Integer idServico) {
		Servico servico = this.mostrarUmServico(idServico);
		servico.setIdServico(idServico);
		
		if (servico.getFuncionario() != null) {
			servico.setStatus(StatusServico.CONCLUIDO);
		}
		return servicoRepository.save(servico);
	}
	
	public Servico editarServico(Servico servico, Integer idFuncionario) {
		this.mostrarUmServico(servico.getIdServico());
		Funcionario funcionario = funcionarioRepository.getById(idFuncionario);
		servico.setFuncionario(funcionario);
		return servicoRepository.save(servico);
	}

	public void excluirServico(Integer idServico) {
		this.mostrarUmServico(idServico);
		servicoRepository.deleteById(idServico);
	}
	
	
}
