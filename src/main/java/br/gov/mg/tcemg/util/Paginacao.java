package br.gov.mg.tcemg.util;

import java.io.Serializable;

public class Paginacao implements Serializable {
	
	private Integer page;
	private Integer pageSize;
	private Integer order;
	private Integer sortOrder;
	private Integer posicao;
	private Integer limite;

	private static final long serialVersionUID = -6790156681985357746L;

	public Paginacao() {
	}
	
	public Paginacao(Integer posicao, Integer limite) {
		this.posicao = posicao;
		this.limite = limite;
	}

	public void init() {
		this.posicao = (page - 1) * pageSize;
		this.limite = pageSize;
	}
	
	public Paginacao(Integer sortOrder, Integer page, Integer pageSize,	Integer order) {
		
		this.sortOrder = sortOrder;
		this.page = page;
		this.pageSize = pageSize;
		this.order = order;
		init();
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setItemsPerPage(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getLimite() {
		return this.limite;
	}

	public Integer getPosicao() {
		return this.posicao;
	}

	public void setLimite(Integer limite) {
		this.limite = limite;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
}
