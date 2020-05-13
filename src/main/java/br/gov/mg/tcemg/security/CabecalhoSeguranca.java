package br.gov.mg.tcemg.security;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class CabecalhoSeguranca implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9153243141997984635L;
	
	private String tipoOrgao;
	
	/*@NotNull
	@XmlElement(required= true) */
	private String sistema;
	private String cnpj;
	
	/*@NotNull
	@XmlElement(required= true) */
	private String tipoSistema;
	
	private String login;
	private String cpfUsuarioLogado;
	
	/*@NotNull
	@XmlElement(required= true) */
	private String senhaCriptografada;
	
	
	public String getTipoOrgao() {
		return tipoOrgao;
	}

	public void setTipoOrgao(String tipoOrgao) {
		this.tipoOrgao = tipoOrgao;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTipoSistema() {
		return tipoSistema;
	}

	public void setTipoSistema(String tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCpfUsuarioLogado() {
		return cpfUsuarioLogado;
	}

	public void setCpfUsuarioLogado(String cpfUsuarioLogado) {
		this.cpfUsuarioLogado = cpfUsuarioLogado;
	}

	public String getSenhaCriptografada() {
		return senhaCriptografada;
	}

	public void setSenhaCriptografada(String senhaCriptografada) {
		this.senhaCriptografada = senhaCriptografada;
	}
}
