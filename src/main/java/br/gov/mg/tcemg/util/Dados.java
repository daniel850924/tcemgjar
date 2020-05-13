package br.gov.mg.tcemg.util;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "dados")
@XmlType(propOrder = { "cnpj", "siglaTipoOrgaoSGI", "siglaTipoOrgaoDBRAP","idTipoOrgaoSiace","idTipoEntidadeSiace","senha" })
public class Dados {

    private String cnpj;
    private String siglaTipoOrgaoSGI;
    private String siglaTipoOrgaoDBRAP;
    private String idTipoOrgaoSiace;
    private String idTipoEntidadeSiace;
    private String senha;
    
    
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getSiglaTipoOrgaoSGI() {
		return siglaTipoOrgaoSGI;
	}
	public void setSiglaTipoOrgaoSGI(String siglaTipoOrgaoSGI) {
		this.siglaTipoOrgaoSGI = siglaTipoOrgaoSGI;
	}
	public String getSiglaTipoOrgaoDBRAP() {
		return siglaTipoOrgaoDBRAP;
	}
	public void setSiglaTipoOrgaoDBRAP(String siglaTipoOrgaoDBRAP) {
		this.siglaTipoOrgaoDBRAP = siglaTipoOrgaoDBRAP;
	}
	public String getIdTipoOrgaoSiace() {
		return idTipoOrgaoSiace;
	}
	public void setIdTipoOrgaoSiace(String idTipoOrgaoSiace) {
		this.idTipoOrgaoSiace = idTipoOrgaoSiace;
	}
	public String getIdTipoEntidadeSiace() {
		return idTipoEntidadeSiace;
	}
	public void setIdTipoEntidadeSiace(String idTipoEntidadeSiace) {
		this.idTipoEntidadeSiace = idTipoEntidadeSiace;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

} 