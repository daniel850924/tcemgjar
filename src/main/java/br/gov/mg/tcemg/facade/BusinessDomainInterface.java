package br.gov.mg.tcemg.facade;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import br.gov.mg.tcemg.model.IEntidade;
import br.gov.mg.tcemg.util.Paginacao;
import br.gov.mg.tcemg.util.Parametro;

public interface BusinessDomainInterface <E extends IEntidade> {

	
	public E incluir(E paramE);
	
	public E obter(Serializable paramSerializable);
	
	public E alterar(E paramE);
	
	public void excluir(E paramE);
	
	public Long getNextVal(String sequence) throws Exception;
	
	public void executeUpdate(String queryname, Map<String, Object> params);
	
	public List<E> listar();
	
	public List<E> listarOrdenado(String atributoOrdenacao);
	
	public List<E> consultaPorNamedQuery(String queryname, Map<String, Object> params);

	public List<E> consultaPorNamedQuery(String namedQuery, Map<String, Object> paramMap, Map<String, TemporalType> paramMapTemporalType);

	public List<E> consultarPorNamedQuery(String namedQuery, Map<String, Object> paramMap, Paginacao paramPaginacao);
	
	public E consultaUnicoRegistroPorNamedQuery(String queryname, Map<String, Object> params);
	
	public Map<Integer, Parametro> executarProcedure(String procedureCall, Map<Integer, Parametro> params) throws SQLException;
	
	public Map<Integer, Parametro> executarProcedureSistema(String procedureCall, Map<Integer, Parametro> mapeamento) throws SQLException;
	
	public abstract Object consultarNativeQuery(String sql, Paginacao paramPaginacao, Map<String, Object> paramMap);
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> params, Paginacao paramPaginacao);
	
	public List<?> consultarPorHqlPaginacao(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao);
	
	public Object consultaDTOPorHql(String hql, Map<String, Object> paramMap);

	public abstract Number consultaSomatorioMaxValuePorNamedQuery(String namedQuery, Map<String, Object> paramMap);
	
	public List<E> consultarPorHql(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao);

	E consultaUnicoRegistroPorHql(String hql, Map<String, Object> parametros);

	Integer alterarOuExcluirPorHql(String hql, Map<String, Object> params);
	
	List<E> pesquisar(IEntidade filtro);
	
	public Object consultaDTOUnicoPorHql(String hql, Map<String, Object> paramMap);
	
	public Object pesquisarDTOComFiltrosRestricoes(Integer startPosition, Integer maxResult,String hql, Map<String, Object> paramMap) throws Exception;

	public Long countComRestricoesPorConsulta(String consulta, Map<String, Object> params)throws Exception ;

	public Object consultaDTOUnicoPorNamedQuery(String hql, Map<String, Object> paramMap);
	
	public Long countComRestricoes(String namedQuery, Map<String, Object> params) throws Exception;
	
}
