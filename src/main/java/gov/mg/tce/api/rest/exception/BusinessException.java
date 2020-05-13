package gov.mg.tce.api.rest.exception;

import gov.mg.tce.api.rest.model.Mensagem;

import com.google.gson.Gson;


public class BusinessException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8063594047483829891L;
	
	private Mensagem mensagemErro = null;

	public BusinessException(Mensagem mensagemErro){
		super(mensagemErro.getMensagem());
		this.mensagemErro = mensagemErro;
	}
	
	public BusinessException(Mensagem mensagemErro, Object parametro, String nomePropriedade) throws Exception{
		super(mensagemErro.getMensagem());
		
		String param = null;
		
		param = new Gson().toJson(parametro);
		if(parametro instanceof String){
			param = param.replace("\"", "");
		}
		
		this.mensagemErro = mensagemErro.clone();
		this.mensagemErro.setJsonParametro(param);
		this.mensagemErro.setNomePropriedade(nomePropriedade);
	}

	public Mensagem getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(Mensagem mensagemErro) {
		this.mensagemErro = mensagemErro;
	}
}
