package br.gov.mg.tcemg.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionImpl;

import br.gov.mg.tcemg.model.IEntidade;
import br.gov.mg.tcemg.util.JPAQLUtil;
import br.gov.mg.tcemg.util.Paginacao;
import br.gov.mg.tcemg.util.Parametro;
import br.gov.mg.tcemg.util.ReflectionsUtil;
import br.gov.mg.tcemg.util.StringUtil;

@Stateless
@SuppressWarnings("unchecked")
public class TCEMGDao<E extends IEntidade> implements DaoInterface<E> {

	private EntityManager entityManager;

	public TCEMGDao() {

	}

	public TCEMGDao(final EntityManager em) {
		this.entityManager = em;
	}

	private Session getSession() {
		return (Session) entityManager.getDelegate();
	}
	
	/**
	 * Metodo que retorna o nextval com base na sequence.
	 * 
	 * @param sequence
	 *            Nome da sequence.
	 * @return nextval.
	 * @throws Exception
	 *             Caso ocorra algum erro.
	 */
	public Long getNextVal(String sequence) throws Exception {

		Object object = null;

		try {

			Session session = getSession();

			StringBuilder sb = new StringBuilder("select ");
			sb.append(sequence);
			sb.append(".NEXTVAL from dual");

			object = session.createSQLQuery(sb.toString()).uniqueResult();

		} catch (Exception e) {
			throw new Exception();
		}

		return new Long(object.toString());
	}

	public Criteria createCriteria() {
		return getSession().createCriteria(getPersistentClass());
	}

	public EntityManager createEntityManagerProcedure() {
		return null;
	}

	/**
	 * Realiza consulta da namedquey com conceito de restricoes, onde parametros
	 * nulos removem as condicoes do where da query. Alem disso ele trata o
	 * range da busca para pesquisas paginadas.
	 * 
	 * @param startPosition
	 *            posicao inicial de busca, range inicial.
	 * @param maxResult
	 *            quantidade de valores retornados, range final relativo ao
	 *            inicial.
	 * @param namedQuery
	 *            key da namedquery mapeada
	 * @param entry
	 *            lista de parametros
	 * @return lista de objetos da entidade deste dao
	 * @throws AplicationException
	 *             banco sem conexao ou erro na montagem da query
	 */
	public List<E> pesquisarComRestricoes(Integer startPosition,
			Integer maxResult) throws Exception {

		Query query = this.entityManager.createQuery("SELECT o FROM "
				+ getPersistentClass().getSimpleName() + " o");

		// for (Entry<String,Object> single : params.entrySet()) {
		// if (single.getValue() != null) {
		// query.setParameter(single.getKey(), single.getValue());
		// }
		// }

		query.setMaxResults(maxResult);
		query.setFirstResult(startPosition);

		return query.getResultList();
	}

	public List<E> pesquisarComFiltrosRestricoes(Integer startPosition,
			Integer maxResult, String consulta, Map<String, Object> params)
			throws Exception {

		Query query = this.entityManager.createNamedQuery(consulta);
		setQueryParametros(params, query);
		// for (Entry<String,Object> single : params.entrySet()) {
		// if (single.getValue() != null) {
		// query.setParameter(single.getKey(), single.getValue());
		// }
		// }

		query.setMaxResults(maxResult);
		query.setFirstResult(startPosition);

		return query.getResultList();
	}

	public List<?> pesquisarDTOComFiltrosRestricoes(Integer startPosition,
			Integer maxResult, String consulta, Map<String, Object> params)
			throws Exception {

		Query query = this.entityManager.createQuery(consulta);
		setQueryParametros(params, query);
		query.setMaxResults(maxResult);
		query.setFirstResult(startPosition);

		return query.getResultList();
	}

	public Long countComRestricoesPorConsulta(String consulta, Map<String, Object> params)throws Exception {

		Query query = this.entityManager.createQuery(consulta);

		for (Entry<String, Object> single : params.entrySet()) {
			if (single != null) {
				query.setParameter(single.getKey(), single.getValue());
			}
		}
		try {

			return (Long) query.getSingleResult();
		} catch (Exception e) {
			throw new Exception();
		}

	}
	
	public Long countComRestricoes(String namedQuery, Map<String, Object> params)
			throws Exception {

		Query query = this.entityManager.createNamedQuery(namedQuery);

		for (Entry<String, Object> single : params.entrySet()) {
			if (single != null) {
				query.setParameter(single.getKey(), single.getValue());
			}
		}
		try {

			return (Long) query.getSingleResult();
		} catch (Exception e) {
			throw new Exception();
		}

	}

	
	public Map<Integer, Parametro> executarProcedureSistema(String procedureCall, Map<Integer, Parametro> mapeamento) throws SQLException{

		logProcedureCall(procedureCall, mapeamento);
		Map<Integer, Parametro> paramsSaida = new HashMap<Integer, Parametro>();
		Connection conn = ((SessionImpl)this.entityManager.getDelegate()).connection();
		CallableStatement cstmt = null;

		try {

			cstmt = conn.prepareCall(procedureCall);
			Set<Integer> chaves = mapeamento.keySet();

			for (Integer chave : chaves) {
				Parametro value = mapeamento.get(chave);
				if (value.isParamEntrada()) {
					cstmt.setObject(chave, value.getObject());
				} else {
					cstmt.registerOutParameter(chave, (Integer) value.getObject());
				}
			}

			cstmt.execute();
			for (Integer chave : chaves) {
				if (!mapeamento.get(chave).isParamEntrada()) {
					paramsSaida.put(chave, new Parametro(cstmt.getObject(chave), false));
				}
			}

			return paramsSaida;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			cstmt.close();
			cstmt = null;
//			conn.close();
//			conn = null;
		}
	}
			
	public Map<Integer, Parametro> executarProcedure(String procedureCall,
			Map<Integer, Parametro> mapeamento) throws SQLException {

		logProcedureCall(procedureCall, mapeamento);
		Map<Integer, Parametro> paramsSaida = new HashMap<Integer, Parametro>();
		EntityManager entityManagerSGAP = createEntityManagerProcedure();
		Session unwrap = entityManagerSGAP.unwrap(Session.class);
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) unwrap.getSessionFactory();
		Connection conn = sessionFactory.getConnectionProvider().getConnection();

		CallableStatement cstmt = null;

		try {

			cstmt = conn.prepareCall(procedureCall);
			Set<Integer> chaves = mapeamento.keySet();

			for (Integer chave : chaves) {
				Parametro value = mapeamento.get(chave);
				if (value.isParamEntrada()) {
					cstmt.setObject(chave, value.getObject());
				} else {
					cstmt.registerOutParameter(chave,
							(Integer) value.getObject());
				}
			}

			cstmt.execute();
			for (Integer chave : chaves) {
				if (!mapeamento.get(chave).isParamEntrada()) {
					paramsSaida.put(chave, new Parametro(
							cstmt.getObject(chave), false));
				}
			}

			return paramsSaida;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		} finally {
			cstmt.close();
			cstmt = null;
			conn.close();
			conn = null;
		}
	}
	
	private void logProcedureCall(String procedureCall, Map<Integer, Parametro> paramsPrdSolicita) {
		System.out.println("LOG PROCEDURE CALL - " + procedureCall);
		Set<Integer> keySet = paramsPrdSolicita.keySet();
        for (Integer key : keySet) {
			Parametro value = paramsPrdSolicita.get(key);
			System.out.println(key + " - " + value.getObject());
		}
	}

	public E alterar(E entidade) {
		return this.entityManager.merge(entidade);
	}

	public List<E> consultaPorNamedQuery(String queryname,
			Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(queryname);
		setQueryParametros(params, query);
		return query.getResultList();
	}

	public List<E> consultaPorNamedQuery(String queryname, Map<String, Object> params, Map<String, TemporalType> paramsTemporalType) {
		Query query = this.entityManager.createNamedQuery(queryname);

		if (paramsTemporalType != null && !paramsTemporalType.isEmpty()) {
			setQueryParametros(params, paramsTemporalType, query);
		} else {
			setQueryParametros(params, query);
		}

		return query.getResultList();
	}

	public List<?> consultaDTOPorNamedQuery(String namedQuery,
			Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setQueryParametros(params, query);
		return query.getResultList();
	}
	
	public List<?> consultaDTOPorNamedQuery(String namedQuery,
			Map<String, Object> params, Paginacao paramPaginacao) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setQueryParametros(params, query);
		configurarPaginacao(query, paramPaginacao);
		return query.getResultList();
	}
	
	public Object consultaDTOPorHql(String hql, Map<String, Object> paramMap){
		Query query = createQuery(hql);
		setQueryParametros(paramMap, query);
		return query.getResultList();
	}
	
	public List<?> consultarPorHqlPaginacao(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao) {

		Query query = createQuery(hql);
		setQueryParametros(paramMap, query);

		configurarPaginacao(query, paramPaginacao);

		return query.getResultList();

	}
	
	public Object consultaDTOUnicoPorHql(String hql, Map<String, Object> paramMap){
		Query query = createQuery(hql);
		setQueryParametros(paramMap, query);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Object consultaDTOUnicoPorNamedQuery(String namedQuery,
			Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		setQueryParametros(params, query);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void executeUpdate(String queryname, Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(queryname);
		setQueryParametros(params, query);
		query.executeUpdate();
	}
	
	public List<E> consultarPorHql(String hql, Map<String, Object> parametros) {
		Query query = createQuery(hql);
		setQueryParametros(parametros, query);

		return query.getResultList();
	}

	public E consultaUnicoRegistroPorHql(String hql, Map<String, Object> parametros) {
		Query query = createQuery(hql);
		setQueryParametros(parametros, query);

		try {
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<E> consultarPorHql(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao) {

		Query query = createQuery(hql);
		setQueryParametros(paramMap, query);

		configurarPaginacao(query, paramPaginacao);

		return query.getResultList();

	}

	public Query createQuery(String hql) {
		return entityManager.createQuery(hql);
	}

	public E consultaUnicoRegistroPorNamedQuery(String queryname,
			Map<String, Object> params) {
		Query query = this.entityManager.createNamedQuery(queryname);
		setQueryParametros(params, query);
		try {
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Long consultaSomatorioPorNamedQuery(String namedQuery,
			Map<String, Object> paramMap) {

		Long sum = 0L;
		try {
			Query query = this.entityManager.createNamedQuery(namedQuery);
			setQueryParametros(paramMap, query);
			Object result = query.getSingleResult();
			sum = result != null ? (Long) result : 0L;
		} catch (NoResultException e) {
		}
		return sum;
	}

	public Object consultaUnicoAtributoPorNamedQuery(String namedQuery,
			Map<String, Object> paramMap) {

		try {
			Query query = this.entityManager.createNamedQuery(namedQuery);
			setQueryParametros(paramMap, query);
			return query.getSingleResult();
		} catch (NoResultException e) {
		}
		return null;
	}
	
	public List<Object> consultaAtributoPorNamedQuery(String namedQuery,
			Map<String, Object> paramMap) {

		try {
			Query query = this.entityManager.createNamedQuery(namedQuery);
			setQueryParametros(paramMap, query);
			return query.getResultList();
		} catch (NoResultException e) {
		}
		return null;
	}

	public E obter(Serializable id) {
		return (E) this.entityManager.find(getPersistentClass(), id);
	}

	public E saveOrUpdate(E entidade) {
		
		if(entidade.getId() == null){
			return incluir(entidade);
		}else{
			return alterar(entidade);
		}
		
	}
	
	public E incluir(E entidade) {
		this.entityManager.persist(entidade);
		return entidade;
	}

	public void excluir(E entidade) {
		E merge = this.entityManager.merge(entidade);
		this.entityManager.remove(merge);
	}

	public List<E> listar() {
		Query query = this.entityManager.createQuery("SELECT o FROM "
				+ getPersistentClass().getSimpleName() + " o");
		return query.getResultList();
	}
	
	public List<E> listarOrdenado(String atributoOrdenacao) {
		Query query = this.entityManager.createQuery("SELECT o FROM "
				+ getPersistentClass().getSimpleName() + " o ORDER BY o." + atributoOrdenacao);
		return query.getResultList();
	}

	protected Query setQueryParametros(Map<String, Object> parametros,
			Query query) {
		if (parametros != null)
			for (String parametro : parametros.keySet())
//				if(parametros.get(parametro) instanceof Date){
//					Date date = (Date) parametros.get(parametro);
//					Calendar calendar = new GregorianCalendar();
//					calendar.setTime(date);
//					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
//					query.setParameter(parametro, calendar.getTime());
//				}else{
					query.setParameter(parametro, parametros.get(parametro));
//				}

		return query;
	}

	protected Query setQueryParametros(Map<String, Object> parametros, Map<String, TemporalType> parametrosTipoData, Query query) {
		if (parametros != null && parametrosTipoData != null) {
			for (String parametro : parametros.keySet()) {
				if (parametrosTipoData.containsKey(parametro) && parametros.get(parametro) instanceof Date) {
					query.setParameter(parametro, (Date) parametros.get(parametro), parametrosTipoData.get(parametro));
				} else {
					query.setParameter(parametro, parametros.get(parametro));
				}
			}
		}

		return query;
	}

	public Class<E> getPersistentClass() {
		return (Class<E>) ReflectionsUtil.getTipoGenerico(getClass());
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Object consultarNativeQuery(String sql, Paginacao paginacao,
			Map<String, Object> params) {
		Query query = this.entityManager.createNativeQuery(sql);
		setQueryParametros(params, query);
		configurarPaginacao(query, paginacao);
		return query.getResultList();
	}
	
	public Integer incluirPorNativeQuery(String sql, Map<String, Object> params) {
		Query query = this.entityManager.createNativeQuery(sql);
		setQueryParametros(params, query);
		return query.executeUpdate();
	}

	private void configurarPaginacao(Query query, Paginacao paginacao) {
		if (paginacao != null) {
			if (paginacao.getPosicao() != null) {
				query.setFirstResult(paginacao.getPosicao().intValue());
			}
			if (paginacao.getLimite() != null)
				query.setMaxResults(paginacao.getLimite().intValue());
		}
	}

	/**
	 * Execute an update or delete statement.
	 * 
	 * @author alexander.magalhaes
	 * @param namedQuery
	 * @param params
	 * @return the number of entities updated or deleted
	 */
	public Integer alterarOuExcluirPorNamedQuery(String namedQuery,
			Map<String, Object> params) {

		Query query = getEntityManager().createNamedQuery(namedQuery);
		setQueryParametros(params, query);

		try {
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Integer alterarOuExcluirPorHql(String hql, Map<String, Object> params) {
		Query query = getEntityManager().createQuery(hql);

		setQueryParametros(params, query);

		try {
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<E> consultarPorNamedQuery(String namedQuery,
			Map<String, Object> paramMap, Paginacao paramPaginacao) {

		Query query = this.entityManager.createNamedQuery(namedQuery);

		setQueryParametros(paramMap, query);

		configurarPaginacao(query, paramPaginacao);

		return query.getResultList();

	}

	public Number consultaSomatorioMaxValuePorNamedQuery(String namedQuery,
			Map<String, Object> paramMap) {

		Number sum = null;

		try {

			Query query = this.entityManager.createNamedQuery(namedQuery);

			setQueryParametros(paramMap, query);

			Object result = query.getSingleResult();

			sum = result != null ? (Number) result : 0L;

		} catch (NoResultException e) {

		}

		return sum;

	}

	public List<E> pesquisar(IEntidade filtro){
		Criteria criteria = createCriteria();
		E entidade = (E) filtro;

		Field[] arrayAtributos = entidade.getClass().getDeclaredFields();

		try {
			for (Field atributo : arrayAtributos) {
				
				if (atributo.isAnnotationPresent(Column.class)) {
					
					atributo.setAccessible(true);
					
					if (atributo.isAnnotationPresent(Id.class) && seFiltrarPorID()) {
						obterFiltro(criteria, entidade, atributo);
					
					} else if ( ! atributo.isAnnotationPresent(Id.class)) {
						obterFiltro(criteria, entidade, atributo);
					}
				}
			}
		
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}	
		
		if(!StringUtil.isStringVazia(obterNomeCampoOrdenacao())){
			criteria.addOrder(Order.asc(obterNomeCampoOrdenacao()));
		}
		
		return criteria.list();
	}
	
	private void obterFiltro(final Criteria criteria, final E entidade, final Field atributo) throws IllegalAccessException {
		
		if (atributo.getType().isAssignableFrom(String.class)) {
			
			if(! StringUtil.isStringVazia((String) atributo.get(entidade))) {
				criteria.add(Restrictions.like(atributo.getName(), JPAQLUtil.montaParametroLikePorAproximacao((String) atributo.get(entidade))).ignoreCase());
			}
			
		} else {
			
			if(atributo.get(entidade) != null){
				criteria.add(Restrictions.eq(atributo.getName(), atributo.get(entidade)));
			}
		}
	}
	
	@Override
	public String obterNomeCampoOrdenacao() {
		return null;
	}
	
	@Override
	public boolean seFiltrarPorID() {
		return false;
	}

	@Override
	public Query getNamedQuery(String namedQuery) {
		return this.entityManager.createNamedQuery(namedQuery);
	}
}
