package br.com.precredenciamento.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_externo")
public class UsuarioExterno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String cpf;
	private String sexo;
	private String nomeCompleto;
	private String nomeSocial;
	private LocalDate dataNascimento;
	private String estadoCivil;
	private String grauInstrucao;
	private String rg;
	private String orgaoEmissor;
	private String naturalidade;
	private String nacionalidade;
	private String profissao;
	private String numeroCarteiraTrabalho;
	private String nomePai;
	private String nomeMae;
	private String telefoneCelular;
	private String telefoneResidencial;
	private String telefoneComercial;
	private String email;
	private String senha;
	private LocalDate dataAdmisao;
	private String cnpjEmpregador;
	private String nomeEmpresa;
	
	@Column(length = 300)
	private String observacao;
	private double renda;
	private boolean carteiraRegistrada;
	private String tipoDocEmpregador;

	@OneToMany(mappedBy = "titular")
	private List<Dependente> dependentes;

	@br.com.precredenciamento.anotacao.Arquivo
	@OneToOne(cascade = CascadeType.ALL)
	private Arquivo fotoPerfil;

	@br.com.precredenciamento.anotacao.Arquivo
	@OneToOne(cascade = CascadeType.ALL)
	private Arquivo fotoCpf;

	@br.com.precredenciamento.anotacao.Arquivo
	@OneToOne(cascade = CascadeType.ALL)
	private Arquivo fotoRG;

	@br.com.precredenciamento.anotacao.Arquivo
	@OneToOne(cascade = CascadeType.ALL)
	private Arquivo fotoCarteiraTrabalho;

	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;

	@JoinTable(name = "usuario_externo_arquivos_anexos", joinColumns = @JoinColumn(name = "usuario_externo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "arquivo_id", referencedColumnName = "id"))
	@OneToMany(cascade = CascadeType.ALL)
	private List<Arquivo> anexos;

	@OneToOne(cascade = CascadeType.ALL)
	private UsuarioExternoNomeSocial cadastroNomeSocial;

	@br.com.precredenciamento.anotacao.Arquivo
	@OneToOne(cascade = CascadeType.ALL)
	private Arquivo comprovanteNomeSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeSocial() {
		return nomeSocial;
	}

	public void setNomeSocial(String nomeSocial) {
		this.nomeSocial = nomeSocial;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getGrauInstrucao() {
		return grauInstrucao;
	}

	public void setGrauInstrucao(String grauInstrucao) {
		this.grauInstrucao = grauInstrucao;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoEmissor() {
		return orgaoEmissor;
	}

	public void setOrgaoEmissor(String orgaoEmissor) {
		this.orgaoEmissor = orgaoEmissor;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getProfissao() {
		return profissao;
	}

	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}

	public String getNumeroCarteiraTrabalho() {
		return numeroCarteiraTrabalho;
	}

	public void setNumeroCarteiraTrabalho(String numeroCarteiraTrabalho) {
		this.numeroCarteiraTrabalho = numeroCarteiraTrabalho;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataAdmisao() {
		return dataAdmisao;
	}

	public void setDataAdmisao(LocalDate dataAdmisao) {
		this.dataAdmisao = dataAdmisao;
	}

	public String getCnpjEmpregador() {
		return cnpjEmpregador;
	}

	public void setCnpjEmpregador(String cnpjEmpregador) {
		this.cnpjEmpregador = cnpjEmpregador;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public double getRenda() {
		return renda;
	}

	public void setRenda(double renda) {
		this.renda = renda;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Arquivo getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(Arquivo fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Arquivo getFotoCpf() {
		return fotoCpf;
	}

	public void setFotoCpf(Arquivo fotoCpf) {
		this.fotoCpf = fotoCpf;
	}

	public Arquivo getFotoRG() {
		return fotoRG;
	}

	public void setFotoRG(Arquivo fotoRG) {
		this.fotoRG = fotoRG;
	}

	public Arquivo getFotoCarteiraTrabalho() {
		return fotoCarteiraTrabalho;
	}

	public void setFotoCarteiraTrabalho(Arquivo fotoCarteriaTrabalho) {
		this.fotoCarteiraTrabalho = fotoCarteriaTrabalho;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public boolean isCarteiraRegistrada() {
		return carteiraRegistrada;
	}

	public String getTipoDocEmpregador() {
		return tipoDocEmpregador;
	}

	public void setCarteiraRegistrada(boolean carteiraRegistrada) {
		this.carteiraRegistrada = carteiraRegistrada;
	}

	public void setTipoDocEmpregador(String tipoDocEmpregador) {
		this.tipoDocEmpregador = tipoDocEmpregador;
	}

	public List<Arquivo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Arquivo> anexos) {
		this.anexos = anexos;
	}

	public void adicionarAnexo(Arquivo anexo) {
		this.anexos.add(anexo);
	}

	public UsuarioExternoNomeSocial getCadastroNomeSocial() {
		return cadastroNomeSocial;
	}

	public void setCadastroNomeSocial(UsuarioExternoNomeSocial cadastroNomeSocial) {
		this.cadastroNomeSocial = cadastroNomeSocial;
	}

	public Arquivo getComprovanteNomeSocial() {
		return comprovanteNomeSocial;
	}

	public void setComprovanteNomeSocial(Arquivo comprovanteNomeSocial) {
		this.comprovanteNomeSocial = comprovanteNomeSocial;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
