package br.gov.mg.tcemg.interceptor;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import br.gov.mg.tcemg.annotation.InjectDAO;
import br.gov.mg.tcemg.dao.TCEMGDao;

/**
 * Classe usada para injetar o <code>DAO</code> nos atributos dos EJBs.
 */
public abstract class InjectInterceptorBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor.
	 */
	public InjectInterceptorBase() {

	}

	public abstract EntityManager obterEntityManager();

	/**
	 * Este método é usado após o EJB ser criado, e dentro do EJB procura os
	 * <code>BO</code>s que precisa instanciar.
	 * 
	 * @param invocationContext
	 *            - Alvo que será adicionado o <code>BO</code>.
	 * @throws Exception
	 *             - Exceção lançada caso ocorra algum problema quando adicionar
	 *             o <code>BO</code>.
	 */
	@PostConstruct
	public void postConstruct(InvocationContext invocationContext)
			throws Exception {
		// Pega o alvo
		Object target = invocationContext.getTarget();
		// Pega a classe alvo.
		Class classe = target.getClass();
		// Procura os atributos da classe.
		Field[] fields = classe.getDeclaredFields();
		// Verifica se algum dos campos da classe possui o BOAnnotation.
		for (Field field : fields) {
			if (field.isAnnotationPresent(InjectDAO.class)) {
				/*
				 * Quando encontrar algum atributo, com BOAnnotation, gera uma
				 * instancia do BO.
				 */
				this.injetaDAO(field, target, obterEntityManager());
			}
		}
	}

	/**
	 * Método usado para gerar uma instancia do <code>DAO</code> e atribui-la ao
	 * atributo.
	 * 
	 * @param field
	 *            - Atributo que vai receber o <code>DAO</code>.
	 * @param target
	 *            - Classe alvo.
	 * @param entityManager
	 *            - <code>EntityManager</code> que será usado na instância do
	 *            <code>DAO</code>.
	 * @throws Exception
	 *             - Exceção lançada caso ocorra algum problema quando adicionar
	 *             o <code>DAO</code>.
	 */
	private void injetaDAO(Field field, Object target, EntityManager entityManager) throws Exception {
		// Pega a classe do BO que sera instanciado.
		Class classDAO = field.getType();

		Fabrica fabrica = new Fabrica();

		// Gera uma instancia do DAO.
		TCEMGDao dao = fabrica.instanciarDAO(entityManager, classDAO);

		// Verifica se o atributo esta acessível.
		boolean acessivel = field.isAccessible();
		// Se o atributo nao e acessível, deixa ele como acessível.
		if (!acessivel) {
			field.setAccessible(true);
		}
		// Seta o DAO no atributo.
		field.set(target, dao);
		// Se o atributo nao e acessivel, volta ao valor original.
		if (!acessivel) {
			field.setAccessible(acessivel);
		}
	}
}
