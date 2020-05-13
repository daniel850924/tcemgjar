package br.gov.mg.tcemg.exception;



/*@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErroInfo")*/
public class ErroInfo extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -424553649824302565L;
	
	private Long id;
	private String mensagem;
	private String parametrosEntrada;
	private String detalhamento;
	
	
	public ErroInfo(Long id,String msgErro,String parametrosEntrada, String detalhamento) {
		super(msgErro);
		this.id = id;
		this.mensagem = msgErro;
		this.parametrosEntrada = parametrosEntrada;
		this.detalhamento = detalhamento;
	}
	
	public ErroInfo(Long id,String msgErro,String parametrosEntrada) {
		super(msgErro);
		this.id = id;
		this.mensagem = msgErro;
		this.parametrosEntrada = parametrosEntrada;
	}
	
	public ErroInfo(Long id,String msgErro, Throwable cause) {
		super(msgErro, cause);
		this.id = id;
		this.mensagem = msgErro;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getParametrosEntrada() {
		return parametrosEntrada;
	}

	public void setParametrosEntrada(String parametrosEntrada) {
		this.parametrosEntrada = parametrosEntrada;
	}

	public String getDetalhamento() {
		return detalhamento;
	}

	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	

}
