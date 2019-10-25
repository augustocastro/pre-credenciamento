package br.com.infobtc.dao.result;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY)
public class UsuarioPerfilCustomResult {

	private String nomePerfil;
	private String emailUsuario;
	private String nomeUsuario;
	private double porcentagemOerfil;

	public UsuarioPerfilCustomResult() {

	}

	public UsuarioPerfilCustomResult(String nomePerfil, String emailConsultor, String nomeConsultor, double porcentagemOerfil) {
		this.nomePerfil = nomePerfil;
		this.emailUsuario = emailConsultor;
		this.nomeUsuario = nomeConsultor;
		this.porcentagemOerfil = porcentagemOerfil;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public double getPorcentagemOerfil() {
		return porcentagemOerfil;
	}

}
