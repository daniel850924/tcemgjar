package br.gov.mg.tcemg.util;

import java.util.ArrayList;

import br.gov.mg.tcemg.enumerador.TipoPropriedadeEnum;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

	public static Object obterValorPropriedade(String json, String propriedade, TipoPropriedadeEnum tipoPropriedade){
		
		return obterValorPropriedade(new JsonParser().parse(json).getAsJsonObject(), 
										carregarListaPropridades(propriedade), 
										tipoPropriedade);
	}
	
	public static Object obterValorPropriedade(JsonObject json, ArrayList<String> listaPropriedade, TipoPropriedadeEnum tipoPropriedade){
		
		Object valor = null;
		String propriedade = null;
		
		propriedade = listaPropriedade.get(0);
		listaPropriedade.remove(0);
		
		if(listaPropriedade.size() > 0){
			json = json.get(propriedade).getAsJsonObject();
			return obterValorPropriedade(json, listaPropriedade, tipoPropriedade);
		}
		else{
	
			if(tipoPropriedade.equals(TipoPropriedadeEnum.BOOLEAN)){
				valor = json.get(propriedade).getAsBoolean();
			}
			else if(tipoPropriedade.equals(TipoPropriedadeEnum.STRING)){
				valor = json.get(propriedade).getAsString();
			}
			else if(tipoPropriedade.equals(TipoPropriedadeEnum.DOUBLE)){
				valor = json.get(propriedade).getAsDouble();
			}
			else if(tipoPropriedade.equals(TipoPropriedadeEnum.LONG)){
				valor = json.get(propriedade).getAsLong();
			}
			else if(tipoPropriedade.equals(TipoPropriedadeEnum.ARRAY)){
				valor = json.get(propriedade).getAsJsonArray();
			}
			else if(tipoPropriedade.equals(TipoPropriedadeEnum.OBJETO)){
				valor = json.get(propriedade);
			}
		
		}

		return valor;
	}
	
	private static ArrayList<String> carregarListaPropridades(String propriedade){
		ArrayList<String> listaPropriedade = null;
		String[] arrayPropriedade = null;
		
		listaPropriedade = new ArrayList<String>();
		propriedade = propriedade.replace(".", "<!>");
		arrayPropriedade = propriedade.split("<!>");
		
		for (String prop : arrayPropriedade) {
			listaPropriedade.add(prop);
		}
		
		return listaPropriedade;
	}
}
