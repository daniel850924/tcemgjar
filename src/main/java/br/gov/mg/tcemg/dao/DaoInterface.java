package br.gov.mg.tcemg.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import br.gov.mg.tcemg.model.IEntidade;
import br.gov.mg.tcemg.util.Paginacao;
import br.gov.mg.tcemg.util.Parametro;

public abstract interface DaoInterface<E extends IEntidade> {

	public E alterar(E entidade);
	
	public List<E> consultaPorNamedQuery(String queryname, Map<String, Object> params);

	public List<E> consultaPorNamedQuery(String queryname, Map<String, Object> params, Map<String, TemporalType> paramsTemporalType);

	public E consultaUnicoRegistroPorNamedQuery(String queryname, Map<String, Object> params);
	
	public E obter(Serializable id);
	
	public E incluir(E entidade);
	
	public void excluir(E entidade);
	
	public List<E> listar();
	
	public List<E> listarOrdenado(String atributoOrdenacao);
	
	public Map<Integer, Parametro> executarProcedure(String procedureCall, Map<Integer, Parametro> params) throws SQLException;
	
	public Map<Integer, Parametro> executarProcedureSistema(String procedureCall, Map<Integer, Parametro> mapeamento) throws SQLException;
	
	public Long consultaSomatorioPorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public abstract Object consultaUnicoAtributoPorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public abstract List<Object> consultaAtributoPorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public Integer incluirPorNativeQuery(String sql, Map<String, Object> params);
	
	public abstract Object consultarNativeQuery(String sql, Paginacao paramPaginacao, Map<String, Object> paramMap);
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> params, Paginacao paramPaginacao);
	
	public List<?> consultarPorHqlPaginacao(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao);
	
	public Object consultaDTOPorHql(String hql, Map<String, Object> paramMap);
	
	public Object consultaDTOUnicoPorHql(String hql, Map<String, Object> paramMap);

	void executeUpdate(String queryname, Map<String, Object> params);
	
	public List<E> consultarPorNamedQuery(String namedQuery, Map<String, Object> paramMap, Paginacao paramPaginacao);
	
	public Number consultaSomatorioMaxValuePorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public List<E> consultarPorHql(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao);

	E consultaUnicoRegistroPorHql(String hql, Map<String, Object> parametros);

	Integer alterarOuExcluirPorHql(String hql, Map<String, Object> params);
	
	public List<E> pesquisar(IEntidade filtro);
	
	public String obterNomeCampoOrdenacao();
	
	public boolean seFiltrarPorID();
	
	Query getNamedQuery(String namedQuery);

}
