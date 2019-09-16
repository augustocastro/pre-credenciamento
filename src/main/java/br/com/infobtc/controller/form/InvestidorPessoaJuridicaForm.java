package br.com.infobtc.controller.form;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.infobtc.model.InvestidorPessoaJuridica;
import br.com.infobtc.repository.EnderecoRepository;
import br.com.infobtc.repository.InvestidorPessoaJuridicaRepository;

public class InvestidorPessoaJuridicaForm {

	@NotNull
	@NotEmpty
	private String cnpj;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	private String telefone;
	
	
	@NotNull
	@NotEmpty
	private String inscricao;
	
	@Valid
	@NotNull
	private EnderecoForm endereco;
	
	private String facebook;
	
	private String instagram;
	
	public String getCnpj() {
		return cnpj;
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

	public String getInscricao() {
		return inscricao;
	}
	
	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}
	
	public EnderecoForm getEndereco() {
		return endereco;
	}
	
	public String getFacebook() {
		return facebook;
	}
	
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	
	public String getInstagram() {
		return instagram;
	}
	
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	
	public InvestidorPessoaJuridica atualizar(Long id, InvestidorPessoaJuridicaRepository pessoaJuridicaRepository, EnderecoRepository enderecoRepository) {
		InvestidorPessoaJuridica investidor = pessoaJuridicaRepository.getOne(id);
		
		this.endereco.atualizar(investidor.getEndereco().getId(), enderecoRepository);
		setarPropriedades(investidor);
		
		return investidor;
	}
	
	public void setarPropriedades(InvestidorPessoaJuridica investidor) {
		investidor.setCnpj(cnpj);
		investidor.setEmail(email);
		investidor.setNome(nome);
		investidor.setTelefone(telefone);
		investidor.setInscricao(inscricao);
		investidor.setFacebook(facebook);
		investidor.setInstagram(instagram);
	}

}
