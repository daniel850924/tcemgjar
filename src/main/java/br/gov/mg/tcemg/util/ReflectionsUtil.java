package br.gov.mg.tcemg.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionsUtil {

	public static Class<?> getTipoGenerico(Class<?> classe) {
		ParameterizedType tipo = (ParameterizedType) classe.getGenericSuperclass();
		Type[] parametro = tipo.getActualTypeArguments();
		Class tipoGenerico = null;

		if ((parametro != null) && (parametro.length > 0)) {
			tipoGenerico = (Class) parametro[0];
		}

		return tipoGenerico;
	}
}
