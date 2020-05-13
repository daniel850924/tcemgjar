package gov.mg.tce.api.rest.model;

import javax.persistence.Transient;

import com.google.gson.annotations.Expose;


public abstract class Mensagem implements Cloneable{
	
	@Expose
	@Transient
	protected String mensagem;
	
	@Expose
	@Transient
	protected String jsonParametro;
	
	@Expose
	@Transient
	protected String nomePropriedade;
	
	@Expose
	@Transient
	protected String detalheErro;
	
	public abstract String obterMensagemBase();
	
	@Override
    public Mensagem clone() throws CloneNotSupportedException {
        return (Mensagem) super.clone();
    }

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getJsonParametro() {
		return jsonParametro;
	}

	public void setJsonParametro(String jsonParametro) {
		this.jsonParametro = jsonParametro;
	}

	public String getNomePropriedade() {
		return nomePropriedade;
	}

	public void setNomePropriedade(String nomePropriedade) {
		this.nomePropriedade = nomePropriedade;
	}

	public String getDetalheErro() {
		return detalheErro;
	}

	public void setDetalheErro(String detalheErro) {
		this.detalheErro = detalheErro;
	}

}
