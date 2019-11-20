package br.com.infobtc.controller.dto;

import br.com.infobtc.controller.vo.UsuarioPerfilVo;

public class UsuarioPerfilDto {

	private String email_usuario;
	private String nome_usuario;
	private double porcentagem_perfil;

	public UsuarioPerfilDto(UsuarioPerfilVo usuarioPerfilCustomDto) {
		this.email_usuario = usuarioPerfilCustomDto.getEmailUsuario();
		this.nome_usuario = usuarioPerfilCustomDto.getNomeUsuario();
		this.porcentagem_perfil = usuarioPerfilCustomDto.getPorcentagemPerfil();
	}

	public String getEmail_usuario() {
		return email_usuario;
	}

	public String getNome_usuario() {
		return nome_usuario;
	}

	public double getPorcentagem_perfil() {
		return porcentagem_perfil;
	}

}
