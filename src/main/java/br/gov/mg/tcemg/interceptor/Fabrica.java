package br.gov.mg.tcemg.interceptor;

import java.lang.reflect.Constructor;

import javax.persistence.EntityManager;

import br.gov.mg.tcemg.dao.TCEMGDao;

/**
 * Essa Factory gera instancias automatica do <code>DAO</code>.
 * Utilizando o <code>DAOAnnotation</code> nos atributos dos EJBs, pega qual a
 * classe do <code>DAO</code> gera uma nova instancia.
 */
public class Fabrica {

  /**
   * Construtor privado.
   */
  public Fabrica() {
  }

  public TCEMGDao instanciarDAO(EntityManager entityManager, Class<? extends TCEMGDao> classDAO) throws Exception {
	    /* Usando Reflection para pegar o construtor do DAO que recebe um EntityManager como parametro. */
	    Constructor construtor = classDAO.getConstructor(EntityManager.class);
	    //Cria uma instancia do DAO passando o EntityManager como par�metro.
	    return (TCEMGDao) construtor.newInstance(entityManager);
  }
}
