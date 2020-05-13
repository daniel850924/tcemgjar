package gov.mg.tce.api.rest.util;

import java.io.UnsupportedEncodingException;


public class URLEncoder {

	private static final String DELIMITADOR_URL = "\\?";
	private static final String DELIMITADOR_PARAMETROS = "&";
	private static final String DELIMITADOR_VALOR_PARAMETROS = "=";
	
	public static String encodeURL(String url) throws UnsupportedEncodingException{
		
		String[] listaPartesUrl = null;
		
		listaPartesUrl = url.split(DELIMITADOR_URL);
		url = listaPartesUrl[0];
		if(listaPartesUrl.length > 1){
			url+= tratarParametros(listaPartesUrl[1]);
		}
		
		return url;
	}
	
	
	private static String tratarParametros(String parametrosUrl) throws UnsupportedEncodingException{
		
		String[] listaParametros = null;
		String retorno = "?";
		
		listaParametros = parametrosUrl.split(DELIMITADOR_PARAMETROS);
		for (int i = 0; i < listaParametros.length; i++) {
			
			retorno += getNomeParametro(listaParametros[i]) + DELIMITADOR_VALOR_PARAMETROS + getValorParametro(listaParametros[i]);
		
			if(i+1 != listaParametros.length){
				retorno += DELIMITADOR_PARAMETROS;
			}
		}
		
		return retorno;
	}
	
	private static String getNomeParametro(String parametro){
		return parametro.split(DELIMITADOR_VALOR_PARAMETROS)[0];
	}
	
	private static String getValorParametro(String parametro) throws UnsupportedEncodingException{
		return java.net.URLEncoder.encode(parametro.split(DELIMITADOR_VALOR_PARAMETROS)[1], "UTF-8");
	}
}
