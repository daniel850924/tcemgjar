package br.gov.mg.tcemg.util;


public class JPAQLUtil {


	private static final String SIMBOLO_PORCENTAGEM = "%";

	public static String getConcat(String valor1, String valor2) {
		if(valor1.equals("")){
			return valor2;
		}else{
			return "CONCAT("+valor1+","+valor2+")";
		}
	}
	
	
	public static String montaCriterioPorAproximacao(final String parametro){

		return parametro.replaceAll(" ", SIMBOLO_PORCENTAGEM);
	}
	
	public static String montaParametroLikePorAproximacao(final String parametro) {
		return SIMBOLO_PORCENTAGEM.concat(JPAQLUtil.montaCriterioPorAproximacao(parametro)).concat(SIMBOLO_PORCENTAGEM);
	}
	
	
}
