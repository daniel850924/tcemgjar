package br.gov.mg.tcemg.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadUtil {

	/**
	 * Executa a lista de threads limintando a quantidade executada em paralelo
	 * @param threads Lista de threads que seram executadas
	 * @param maximoThreadSimultaneas Numero maximo de threads que podem ser executadas simultaneamente
	 */
	public static void executarThreads(List<? extends Thread> threads, int maximoThreadSimultaneas) {
		
		try {
			List<Thread> listaThreadLote = new ArrayList<Thread>();
			int i = 0;
			int contadorLote = 0;
			
			for (Thread processoThread : threads) {
				
				processoThread.start();
				listaThreadLote.add(processoThread);
				i++;
				contadorLote++;
				
				if ((contadorLote >= maximoThreadSimultaneas) || (i == threads.size())) {
					for (Thread processoThreadLote : listaThreadLote) {
						try {
							processoThreadLote.join();
						} catch (InterruptedException ie) {
							ie.printStackTrace();
						}
					}
					contadorLote = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
