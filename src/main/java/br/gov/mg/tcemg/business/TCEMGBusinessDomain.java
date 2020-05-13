package br.gov.mg.tcemg.business;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.persistence.TemporalType;

import br.gov.mg.tcemg.dao.TCEMGDao;
import br.gov.mg.tcemg.facade.BusinessDomainInterface;
import br.gov.mg.tcemg.model.IEntidade;
import br.gov.mg.tcemg.util.Paginacao;
import br.gov.mg.tcemg.util.Parametro;

public abstract class TCEMGBusinessDomain<E extends IEntidade> implements BusinessDomainInterface<E>{

	protected abstract TCEMGDao<E> getDao();
	
	public Long getNextVal(String sequence) throws Exception {
		return getDao().getNextVal(sequence);
	}
	
	public E alterar(E paramE) {
		return getDao().alterar(paramE);		
	}

	public List<E> consultaPorNamedQuery(String namedQuery, Map<String, Object> paramMap) {
		return getDao().consultaPorNamedQuery(namedQuery, paramMap);
	}

	public List<E> consultaPorNamedQuery(String namedQuery, Map<String, Object> paramMap, Map<String, TemporalType> paramMapTemporalType) {
		return getDao().consultaPorNamedQuery(namedQuery, paramMap, paramMapTemporalType);
	}

	public void excluir(E paramE) {
		getDao().excluir(paramE);
	}

	public E incluir(E paramE) {
		return getDao().incluir(paramE);
	}
	
	public E saveOrUpdate(E entidade) {
		return getDao().saveOrUpdate(entidade);
	}

	public List<E> listar() {
		return getDao().listar();
	}
	
	public List<E> listarOrdenado(String atributoOrdenacao) {
		return getDao().listarOrdenado(atributoOrdenacao);
	}

	public E obter(Serializable paramSerializable) {
		return getDao().obter(paramSerializable);
	}
	
	public Map<Integer, Parametro> executarProcedure(String procedureCall, Map<Integer, Parametro> mapeamento) throws SQLException{
		 return getDao().executarProcedure(procedureCall, mapeamento);
	}
	
	public Map<Integer, Parametro> executarProcedureSistema(String procedureCall, Map<Integer, Parametro> mapeamento) throws SQLException{
		return getDao().executarProcedureSistema(procedureCall, mapeamento);
	}
	
	public E consultaUnicoRegistroPorNamedQuery(String queryname, Map<String, Object> params){
		return getDao().consultaUnicoRegistroPorNamedQuery(queryname, params);
	}
	
	public Object consultaUnicoAtributoPorNamedQuery(String namedQuery, Map<String, Object> paramMap){
		return getDao().consultaUnicoAtributoPorNamedQuery(namedQuery, paramMap);
	}
	
	public List<Object> consultaAtributoPorNamedQuery(String namedQuery, Map<String, Object> paramMap){
		return getDao().consultaAtributoPorNamedQuery(namedQuery, paramMap);
	}
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> paramMap){
		return getDao().consultaDTOPorNamedQuery(namedQuery, paramMap);
	}
	
	public Object consultaDTOPorNamedQuery(String namedQuery, Map<String, Object> params, Paginacao paramPaginacao) {
		return getDao().consultaDTOPorNamedQuery(namedQuery, params, paramPaginacao);
	}
	
	public List<?> consultarPorHqlPaginacao(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao){
		return getDao().consultarPorHqlPaginacao(hql, paramMap, paramPaginacao);
	}
	
	public Object consultarNativeQuery(String sql, Paginacao paginacao, Map<String, Object> params){
		return getDao().consultarNativeQuery(sql, paginacao, params);
	}
	
	public List<E> consultarPorHql(String hql, Map<String, Object> parametros) {
		return getDao().consultarPorHql(hql, parametros);
	}
	
	public List<E> consultarPorHql(String hql, Map<String, Object> paramMap, Paginacao paramPaginacao){
		return getDao().consultarPorHql(hql, paramMap, paramPaginacao);
	}

	public E consultaUnicoRegistroPorHql(String hql, Map<String, Object> parametros) {
		return getDao().consultaUnicoRegistroPorHql(hql, parametros);
	}

	public List<E> consultarPorNamedQuery(String namedQuery, Map<String, Object> paramMap, Paginacao paramPaginacao){
		return getDao().consultarPorNamedQuery(namedQuery, paramMap, paramPaginacao);
	}
	
	public Number consultaSomatorioMaxValuePorNamedQuery(String namedQuery, Map<String, Object> paramMap){
		return getDao().consultaSomatorioMaxValuePorNamedQuery(namedQuery, paramMap);
	}
	
	public void executeUpdate(String queryname, Map<String, Object> params) {
		getDao().executeUpdate(queryname, params);
	}

	public Integer alterarOuExcluirPorHql(String hql, Map<String, Object> params) {
		return getDao().alterarOuExcluirPorHql(hql, params);
	}
	
	public Object consultaDTOPorHql(String hql, Map<String, Object> paramMap) {
		return getDao().consultaDTOPorHql(hql, paramMap);
	}

	@Override
	public List<E> pesquisar(IEntidade filtro) {
		return getDao().pesquisar(filtro);
	}
	
	public Object consultaDTOUnicoPorHql(String hql, Map<String, Object> paramMap) {
		return getDao().consultaDTOUnicoPorHql(hql, paramMap);
	}
	
	public Object pesquisarDTOComFiltrosRestricoes(Integer startPosition, Integer maxResult,String hql, Map<String, Object> paramMap) throws Exception {
		return getDao().pesquisarDTOComFiltrosRestricoes(startPosition, maxResult, hql, paramMap);
	}
	
	public Long countComRestricoesPorConsulta(String consulta, Map<String, Object> params) throws Exception  {
		return getDao().countComRestricoesPorConsulta(consulta, params);
	}
	
	public Object consultaDTOUnicoPorNamedQuery(String hql, Map<String, Object> paramMap) {
		return getDao().consultaDTOUnicoPorNamedQuery(hql, paramMap);
	}
	
	public Long countComRestricoes(String namedQuery, Map<String, Object> params) throws Exception {
		return getDao().countComRestricoes(namedQuery, params);
	}
	
}
