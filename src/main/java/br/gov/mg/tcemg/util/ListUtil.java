package br.gov.mg.tcemg.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

	
	public static final List<List> particionarLista(List listaEP, int tamanhoParticionamentoLista) {

		List<List> particionamento = new ArrayList<List>();
		Integer inicio = 0;
		Integer fim = listaEP.size() < tamanhoParticionamentoLista ? listaEP.size() : tamanhoParticionamentoLista;
		
		boolean partionamentoCompleto = false;
		do {
			
			particionamento.add(listaEP.subList(inicio, fim));
			if(partionamentoCompleto){
				break;
			}
			
			inicio = fim;
			fim += tamanhoParticionamentoLista;
			if(listaEP.size() < fim){
				if ((listaEP.size() > tamanhoParticionamentoLista) && (listaEP.size() % tamanhoParticionamentoLista != 0)) { // Adiciona o resto.
					particionamento.add(listaEP.subList(inicio, listaEP.size()));
				}

				fim =  listaEP.size() + 1;
				partionamentoCompleto = true;
			}
			
		} while (fim <= listaEP.size());
		
		return particionamento;
	}
}
