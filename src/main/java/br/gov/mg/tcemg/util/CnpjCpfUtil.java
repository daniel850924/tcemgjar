package br.gov.mg.tcemg.util;

import java.io.Serializable;

public class CnpjCpfUtil implements Serializable {

	private static final long serialVersionUID = 6896531803933025171L;

	public static String removerMascaraCnpj(String cnpj) {

		StringBuilder cnpjFormat = new StringBuilder();
		if (cnpj != null && !cnpj.isEmpty()) {
			cnpjFormat.append(cnpj);

			int indexOf = cnpjFormat.indexOf(".");
			while (indexOf != -1) {
				cnpjFormat.replace(indexOf, indexOf + 1, "");
				indexOf = cnpjFormat.indexOf(".");
			}
			
			indexOf = cnpjFormat.indexOf("-");
            if (indexOf != -1) {
            	cnpjFormat.replace(indexOf, indexOf + 1, "");
            }
            
            indexOf = cnpjFormat.indexOf("/");
            if (indexOf != -1) {
            	cnpjFormat.replace(indexOf, indexOf + 1, "");
            }
		}

		return cnpjFormat.toString();
	}
	
	public static String removerMascaraCpf(String cpf) {
		
		StringBuilder cpfFormat = new StringBuilder();
		if(cpf != null && !cpf.isEmpty()){
			cpfFormat.append(cpf);
			
            int indexOf = cpfFormat.indexOf(".");
            while (indexOf != -1) {
            	cpfFormat.replace(indexOf, indexOf + 1, "");
                indexOf = cpfFormat.indexOf(".");
            }
            
            indexOf = cpfFormat.indexOf("-");
            if (indexOf != -1) {
            	cpfFormat.replace(indexOf, indexOf + 1, "");
            }
		}
		
		return cpfFormat.toString();
	}
	
	public static String addMascaraCpf(String cpf){
		return StringUtil.formatar(cpf, "###.###.###-##");
	}
}
