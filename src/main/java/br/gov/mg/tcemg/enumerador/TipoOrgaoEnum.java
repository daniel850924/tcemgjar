package br.gov.mg.tcemg.enumerador;

public enum TipoOrgaoEnum {
	AUTARQUIA("AUTARQUIA","AU",1),
	EMPRESA_PUBLICA("EMPRESA_PUBLICA","EP",2),
	SOCIEDADE_ECONOMIA_MISTA("SOCIEDADE_ECONOMIA_MISTA","SM",3),
	FUNDACAO_PUBLICA("FUNDACAO_PUBLICA","FP", 5),	
	FUNDO("FUNDO","FU",6),
	CONSORCIO("CONSORCIO","CO",7),
	
	ASSEMBLEIA("ASSEMBLEIA","AS", null), 
	CAMARA_MUNICIPAL("CAMARA_MUNICIPAL","CM", null),
	GOVERNADORIA("GOVERNADORIA","GO", null),
	ORGAO_AUTONOMO("ORGAO_AUTONOMO","OA", null),	
	ORGAOS_MUNICIPAIS("ORGAOS_MUNICIPAIS","OM", null),
	PROCURADORIA_GERAL("PROCURADORIA_GERAL","PG", null),
	SECRETARIA_ESTADUAL("SECRETARIA_ESTADUAL","SE", null),	
	TRIBUNAL_CONTAS("TRIBUNAL_CONTAS","TC", null),
	VICE_GOVERNADORIA("VICE_GOVERNADORIA","VG",null),	
	PREFEITURA_MUNICIPAL("PREFEITURA_MUNICIPAL","PM",0),
	FORNECEDOR_SOFTWARE("FORNECEDOR_SOFTWARE","FS",null),
	UNIDADE_ORCAMENTARIA("UNIDADE_ORCAMENTARIA","UO",null),
	ADVOCACIA_GERAL("ADVOCACIA_GERAL","AG",null),
	
	JUDICIARIO("JUDICIARIO","JU", null),
	CONTABILIDADE("CONTABILIDADE", "COT", null),
	MINISTERIO_PUBLICO("MINISTERIO_PUBLICO", "MP", null),
	ORGAO_DE_FORA("ORGAO_DE_FORA", "OF", null);
	
	//FUNDACAO_DIREITO_PRIVADO 4
	//FUNDACAO_DIREITO_PUBLICO 5
	
	private String name;
	private String sigla;
	private Integer id;
	
	TipoOrgaoEnum(String name, String sigla, Integer id) {

		this.name = name;
		this.sigla = sigla;
		this.id = id;
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
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

	