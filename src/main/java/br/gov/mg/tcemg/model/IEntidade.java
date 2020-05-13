package br.gov.mg.tcemg.model;

import java.io.Serializable;

public abstract interface IEntidade extends Serializable {
	public abstract Serializable getId();
	public abstract Serializable setId(Long id);
}
