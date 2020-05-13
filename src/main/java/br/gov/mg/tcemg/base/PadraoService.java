/**
 * 
 */
package br.gov.mg.tcemg.base;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.ejb.AbstractEntityManagerImpl;


/**
 * @author Douglas Adriani
 * @author Gesse Dafe
 */
public class PadraoService  implements Serializable {

	private static final long serialVersionUID = -1977548318173055676L;
	
	/**
	 * Classe base para implementação de operações de persistência
	 * 
	 * @author Gessé Dafé - <code>gesse@sysmap.com.br</code>
	 * @created 23/12/2009
	 * @version 1.0
	 */
	public static abstract class PersistenceOperation<T> {
		public abstract T execute(EntityManager manager) throws Exception;
	}
	
	/**
	 * Classe base para implementação de operações de persistência
	 * 
	 * @author Gessé Dafé - <code>gesse@sysmap.com.br</code>
	 * @created 23/12/2009
	 * @version 1.0
	 */
	public static abstract class ExecuteOperation<T> {
		public abstract T executaRelatorio(AbstractEntityManagerImpl managerConnection) throws Exception;
	}

	/**
	 * Executa uma operação de escrita Exemplo(Update e Insert)
	 * 
	 * @param operation
	 * @throws CommonSecurityException
	 * @throws CommonBusinessException
	 */
	protected <T> T executeWriteOperation(EntityManager manager,PersistenceOperation<T> operation) throws Exception {
		EntityTransaction transaction = null;

		try {
			transaction = manager.getTransaction();
			if(!transaction.isActive())
			transaction.begin();
			T t = operation.execute(manager);
			transaction.commit();

			return t;
		} catch (Exception e) {
			rollback(transaction);

			throw new Exception(e.getMessage(), e);

		} finally {
			close(manager);
		}
	}

	/**
	 * Executa uma operação de leitura Exemplo(Select)
	 * 
	 * @param operation
	 * @throws Exception
	 */
	protected <T> T executeReadOperation(EntityManager manager,PersistenceOperation<T> operation) throws Exception {

		try {
			return operation.execute(manager);
		} finally {
			close(manager);
		}
	}

	/**
	 * Fecha um entity manager
	 * 
	 * @param manager
	 */
	protected void close(EntityManager manager) {
		if (manager != null) {
			try {
				manager.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Desfaz uma transação
	 * 
	 * @param manager
	 */
	protected void rollback(EntityTransaction transaction) {
		if (transaction != null && transaction.isActive()) {
			transaction.rollback();
		}
	}
	
}
