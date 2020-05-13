package br.gov.mg.tcemg.util;

import br.gov.mg.tcemg.util.Paginacao;

import com.google.gson.Gson;

public class PaginacaoUtil {

	public static Paginacao obterPaginacao(String paginacao) {
		Paginacao paginacaoAPI = new Gson().fromJson(paginacao, Paginacao.class);
		
		paginacaoAPI.init();
//		PaginacaoRest paginacaoConsulta = new PaginacaoRest((paramPaginacao.getPage() - 1) * paramPaginacao.getItemsPerPage(), paramPaginacao.getItemsPerPage());
		return paginacaoAPI;
	}

}

