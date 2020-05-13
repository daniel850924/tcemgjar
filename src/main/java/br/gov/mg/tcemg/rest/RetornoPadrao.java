package br.gov.mg.tcemg.rest;

public class RetornoPadrao {

	private Boolean sucesso;
	private String msg;
	private String codErro;
	private Object retorno;

	public RetornoPadrao(Boolean sucesso, String msg, Object retorno) {
		super();
		this.sucesso = sucesso;
		this.msg = msg;
		this.retorno = retorno;
	}

	public RetornoPadrao(Boolean sucesso, String msg, String codErro, Object retorno) {
		super();
		this.sucesso = sucesso;
		this.msg = msg;
		this.codErro = codErro;
		this.retorno = retorno;
	}

	public RetornoPadrao() {
		super();
	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCodErro() {
		return codErro;
	}

	public void setCodErro(String codErro) {
		this.codErro = codErro;
	}

	public Object getRetorno() {
		return retorno;
	}

	public void setRetorno(Object retorno) {
		this.retorno = retorno;
	}

}