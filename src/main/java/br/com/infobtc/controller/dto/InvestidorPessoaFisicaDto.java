package br.com.infobtc.controller.dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.infobtc.model.EstadoCivil;
import br.com.infobtc.model.InvestidorPessoaFisica;
import br.com.infobtc.model.Sexo;

public class InvestidorPessoaFisicaDto {

	private Long id;
	private String nome;
	private String email;
	private String telefone;
	private String tipo;
	private String cpf;
	private String profissao;
	private String documento;
	private String orgao_emissor_uf;
	private String regime_bens;
	private String nacionalidade;
	private String dt_nascimento;
	private Sexo sexo;
	private EstadoCivil estado_civil;
	private EnderecoDto endereco;
	private List<String> arquivos_url = new ArrayList<String>();

	public InvestidorPessoaFisicaDto() {
	}

	public InvestidorPessoaFisicaDto(InvestidorPessoaFisica investidor) {
		this.id = investidor.getId();
		this.nome = investidor.getNome();
		this.email = investidor.getEmail();
		this.telefone = investidor.getTelefone();
		this.tipo = investidor.getTipo();
		this.cpf = investidor.getCpf();
		this.profissao = investidor.getProfissao();
		this.documento = investidor.getDocumento();
		this.orgao_emissor_uf = investidor.getOrgao_emissor_uf();
		this.regime_bens = investidor.getRegime_bens();
		this.nacionalidade = investidor.getNacionalidade();
		this.dt_nascimento = investidor.getDt_nascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.sexo = investidor.getSexo();
		this.estado_civil = investidor.getEstadoCivil();
		this.endereco = new EnderecoDto(investidor.getEndereco());
		this.arquivos_url = investidor.getArquivosUrl();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public String getCpf() {
		return cpf;
	}

	public String getProfissao() {
		return profissao;
	}

	public String getDocumento() {
		return documento;
	}

	public String getOrgao_emissor_uf() {
		return orgao_emissor_uf;
	}

	public String getRegime_bens() {
		return regime_bens;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public String getDt_nascimento() {
		return dt_nascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public EstadoCivil getEstado_civil() {
		return estado_civil;
	}

	public EnderecoDto getEndereco() {
		return endereco;
	}
	
	public List<String> getArquivos_url() {
		return arquivos_url;
	}

	public List<InvestidorPessoaFisicaDto> converter(List<InvestidorPessoaFisica> investidores) {
		return investidores.stream().map(InvestidorPessoaFisicaDto::new).collect(Collectors.toList());
	}

}
