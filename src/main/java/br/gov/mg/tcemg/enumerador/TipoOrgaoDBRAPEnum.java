package br.gov.mg.tcemg.enumerador;

public enum TipoOrgaoDBRAPEnum {
	ENTIDADE_ESTADUAL("ENTIDADE_ESTADUAL","EE"),
	ENTIDADE_MUNICIPAL("ENTIDADE_MUNICIPAL","EM"),
	PREFEITURA_MUNICIPAL("PREFEITURA_MUNICIPAL","PF"),
	CAMARA_MUNICIPAL("CAMARA_MUNICIPAL","CM"),
	FORNECEDOR_SOFTWARE("FORNECEDOR_SOFTWARE","FS"),
	CONSORCIO_PUBLICO("CONSORCIO_PUBLICO","CP");
	
	
	
	private String name;
	private String sigla;
	
	TipoOrgaoDBRAPEnum(String name, String sigla) {

		this.name = name;
		this.sigla = sigla;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

}