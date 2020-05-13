package br.gov.mg.tcemg.exception;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "ValidationException")
public class TceFault {

	@XmlAttribute
	public Long id;
	@XmlAttribute
	public String mensagem;
	@XmlAttribute
	public String parametrosEntrada;
	@XmlAttribute
	public String detalhamento;
	
	
	public void carregar(Long id, String detalhamento) {
		this.id = id;
		this.detalhamento = detalhamento;
	}
	
	
	
}
