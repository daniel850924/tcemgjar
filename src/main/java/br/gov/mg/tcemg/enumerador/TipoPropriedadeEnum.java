package br.gov.mg.tcemg.enumerador;

public enum TipoPropriedadeEnum {

	ARRAY("ARRAY"),
	OBJETO("OBJETO"),
	BOOLEAN("BOOLEAN"),
	DOUBLE("DOUBLE"),
	LONG("LONG"),
	STRING("STRING");
	
	private String label;
	
	TipoPropriedadeEnum(String label){
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
