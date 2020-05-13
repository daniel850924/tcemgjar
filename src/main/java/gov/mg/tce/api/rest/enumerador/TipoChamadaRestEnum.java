package gov.mg.tce.api.rest.enumerador;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TipoChamadaRestEnum")
@XmlEnum(value = Integer.class)
public enum TipoChamadaRestEnum {

	@XmlEnumValue("1")
	POST,

	@XmlEnumValue("2")
	GET

}