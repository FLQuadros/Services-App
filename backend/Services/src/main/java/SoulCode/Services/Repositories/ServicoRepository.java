package SoulCode.Services.Repositories;


import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SoulCode.Services.Models.Funcionario;
import SoulCode.Services.Models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
	
	List<Servico> findByFuncionario(Optional<Funcionario> funcionario);
	
	List<Servico> findByDataEntrada(Date dataEntrada);
	
	@Query(value = "SELECT * FROM servico WHERE data_entrada BETWEEN :data1 AND :data2", nativeQuery = true)
	List<Servico> findByIntervaloData(Date data1, Date data2);
	
	@Query(value = "SELECT * FROM servico WHERE status = :status", nativeQuery = true)
	List<Servico> findByStatusDoServico(String status);
	
	@Query(value = "SELECT * FROM servico WHERE id_funcionario IS NULL", nativeQuery = true)
	List<Servico> findByIdFuncionarioNull();
	
}
