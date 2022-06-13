package SoulCode.Services.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SoulCode.Services.Controllers.FuncionarioController;
import SoulCode.Services.Models.Funcionario;
import SoulCode.Services.Models.Servico;
import SoulCode.Services.Repositories.FuncionarioRepository;
import SoulCode.Services.Repositories.ServicoRepository;


@Service
public class FuncionarioService {
	
	//injeção de dependência
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	ServicoRepository servicoRepository;
	
	// serviços para buscar todos os funcionários cadastrados
	public List<Funcionario> mostrarTodosOsFuncionarios() {
		return funcionarioRepository.findAll();
	}
	
	// findByID - busca um funcionário específico pelo seu id
	public Funcionario mostrarUmFuncionario(Integer idFuncionario) {
		Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
		return funcionario.orElseThrow();
	}
	
	// findBYEmail - busca um funcionário específico pelo seu email
	public Funcionario mostrarUmFuncionarioPeloEmail(String email) {
		Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
		return funcionario.orElseThrow();		
	}
	
	// findByNome - busca um funcionario pelo seu nome
	public Funcionario mostrarUmFuncionarioPeloNome(String nome) {
		Optional<Funcionario> funcionario = funcionarioRepository.findByNome(nome);
		return funcionario.orElseThrow();
	}
	
	public Funcionario mostrarUmFuncioanrioPeloNomeEEmail(String nome, String email) {
		Optional<Funcionario> funcionario = funcionarioRepository.findByNomeAndEmail(nome, email);
		return funcionario.orElseThrow(); 
	}
	
	// save = insere um novo registro na tabela do nosso banco de dados
	public Funcionario cadastrarFuncionario(Funcionario funcionario) {
		// por precaução camo limpar o campo de id do funcionario
		funcionario.setIdFuncionario(null);
		return funcionarioRepository.save(funcionario);
	}
	
	//editar um funcionario já cadastrado
	public Funcionario editarFuncionario(Funcionario funcionario) {
		mostrarUmFuncionario(funcionario.getIdFuncionario());
		return funcionarioRepository.save(funcionario);
	}
	
	//deleteById - excluir um funcionário pelo seu id
	public void excluirFuncionario(Integer idFuncionario) {
		mostrarUmFuncionario(idFuncionario);
		funcionarioRepository.deleteById(idFuncionario);
	}
	
	public Funcionario salvarFoto(Integer idFuncionario, String caminhoFoto) {
		Funcionario funcionario = mostrarUmFuncionario(idFuncionario);
		funcionario.setFoto(caminhoFoto);
		return funcionarioRepository.save(funcionario);
	}
	
	
	
	
	
	
	

}
