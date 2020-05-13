package br.gov.mg.tcemg.base;



public interface BaseEnum {

	/*
	 * Exemplo mapeamento
	  @Type(type = "br.gov.mg.tce.sightweb.type.BaseEnumType", parameters = {@Parameter(name = "BaseEnum", value = "br.gov.mg.tce.sightweb.enumerator.IndSimNaoEnum")} )
	  @Column(name = "IND_GRUPO_PORTAL")
	  private IndSimNaoEnum indGrupoPortal;
	 * 
	 * Todos os enums devem ter a implementacao do bloco estatico e do metodo getPorId
	 * 
	 * private static Map<String, IndSimNaoEnum> relations;
	 * 	static {
	 *		relations = new HashMap<String, IndSimNaoEnum>();
	 *		for (final IndSimNaoEnum indSimNaoEnum : values()) {
	 *			relations.put(indSimNaoEnum.getId(), indSimNaoEnum);
	 *		}
	 *	}
     *
	 *	public static IndSimNaoEnum getPorId(final String id) {
	 *		return relations.get(id);
	 *	}
	 */
	
	
	public String getId();

	public String getLabel();

	public boolean igual(Object obj);

}
