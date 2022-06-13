package SoulCode.Services.Models;

public enum StatusServico {
	
	RECEBIDO("Recebido"),
	ATRIBUIDO("Atribuído"),
	CONCLUIDO("Concluído"),
	ARQUIVADO("Arquivado");
	
	private String descricao;

	private StatusServico(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	

}
