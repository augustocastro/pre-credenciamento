package br.com.precredenciamento.controller.dto;

import java.time.LocalDate;

import br.com.precredenciamento.model.UsuarioExterno;

public class UsuarioExternoDto {
	
	public Long id;
	public String cpf;
	public String sexo;
	public String nomeCompleto;
	public String nomeSocial;
	public LocalDate dataNascimento;
	public String estadoCivil;
	public String grauInstrucao;
	public String rg;
	public String orgaoEmissor;
	public String naturalidade;
	public String nacionalidade;
	public String profissao;
	public String numeroCarteiraTrabalho;
	public String nomePai;
	public String nomeMae;
	public String telefoneCelular;
	public String telefoneResidencial;
	public String telefoneComercial;
	public String email;
	public LocalDate dataAdmisao;
	public String cnpjEmpregador;
	public String nomeEmpresa;
	public double renda;
	public EnderecoDto endereco;
	public ArquivoDto fotoPerfil;
	
	public UsuarioExternoDto converter(UsuarioExterno usuario) {
		id = usuario.getId();
		cpf = usuario.getCpf();
		sexo = usuario.getSexo();
		nomeCompleto = usuario.getNomeCompleto();
		nomeSocial = usuario.getNomeSocial();
		dataNascimento = usuario.getDataNascimento();
		estadoCivil = usuario.getEstadoCivil();
		grauInstrucao = usuario.getGrauInstrucao();
		rg = usuario.getRg();
		orgaoEmissor = usuario.getOrgaoEmissor();
		naturalidade = usuario.getNaturalidade();
		nacionalidade = usuario.getNacionalidade();
		profissao = usuario.getProfissao();
		numeroCarteiraTrabalho = usuario.getNumeroCarteiraTrabalho();
		nomePai = usuario.getNomePai();
		nomeMae = usuario.getNomeMae();
		telefoneCelular = usuario.getTelefoneCelular();
		telefoneResidencial = usuario.getTelefoneResidencial();
		telefoneComercial = usuario.getTelefoneComercial();
		email = usuario.getEmail();
		dataAdmisao = usuario.getDataAdmisao();
		cnpjEmpregador = usuario.getCnpjEmpregador();
		nomeEmpresa = usuario.getNomeEmpresa();
		renda = usuario.getRenda();
		fotoPerfil = new ArquivoDto(usuario.getFotoPerfil());
		endereco = new EnderecoDto(usuario.getEndereco());
		return this;
	}
}
