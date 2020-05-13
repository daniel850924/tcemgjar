package br.gov.mg.tcemg.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtil extends Date {

	private static final long serialVersionUID = 4316365515469017283L;
	private String dataFormatada = null;
	private Date data = null;
	private String dia = "";
	private String mes = "";
	private String ano = "";
	private String hora = "";
	private String Minutos = "";
	private String mesTexto = "";

	public DateUtil(Date data) {
		super();
		this.data = data;
		String dataString = getDataInvertidaComMinutos();
		this.dia = dataString.substring(8, 10);
		this.mes = dataString.substring(5, 7);
		this.ano = dataString.substring(0, 4);
		this.hora = dataString.substring(11, 13);
		this.Minutos = dataString.substring(14, 16);

	}

	public DateUtil() {

	}

	public DateUtil(long date) {
		this.data = new Date(date);
	}

	/**
	 * 
	 * @param date
	 *            (not null) - java.util.Date
	 * @param parte
	 *            - Calendar."parte"
	 * @return
	 */
	public static int getParteData(Date date, int parte) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(parte);
	}

	/**
	 * @return (dd/MM/yyyy)
	 */
	public static Date getDataAtual() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * 
	 * @param dia
	 * @param mes
	 *            - 1 a 12.
	 * @param ano
	 * @return
	 */
	public static Date geraData(int dia, int mes, int ano) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, dia);
		c.set(Calendar.MONTH, mes - 1);
		c.set(Calendar.YEAR, ano);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static int getQuadrimestre(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return (c.get(Calendar.MONTH) / 3) + 1;
	}

	@SuppressWarnings("unused")
	public static String retornaDataValida(String data) {

		if (data.equalsIgnoreCase(""))
			return "";

		String ano = "";
		String mes = "";
		String dia = "";
		String hora = "";
		String minutos = "";
		String segundos = "00";

		boolean anoBoolean = false;
		boolean mesBoolean = false;
		boolean diaBoolean = false;
		boolean horaBoolean = false;
		boolean minutosBoolean = false;

		String resposta = "";

		for (char charRetorno : data.toCharArray()) {
			if (Character.isDigit(charRetorno)) {
				if (!anoBoolean) {
					ano += charRetorno;
				} else if (!mesBoolean) {
					mes += charRetorno;
				} else if (!diaBoolean) {
					dia += charRetorno;
				} else if (!horaBoolean) {
					hora += charRetorno;
				} else {
					minutos += charRetorno;
				}
			} else if (charRetorno == '/') {
				if (!anoBoolean) {
					anoBoolean = true;
				} else if (!mesBoolean) {
					mes = completaZeroEsquerda(2, mes);
					mesBoolean = true;
				}
			} else if (charRetorno == ' ') {
				dia = completaZeroEsquerda(2, dia);
				diaBoolean = true;
			} else if (charRetorno == ':') {
				hora = completaZeroEsquerda(2, hora);
				horaBoolean = true;
			}
		}

		minutos = completaZeroEsquerda(2, minutos);
		resposta = ano + "/" + mes + "/" + dia + " " + hora + ":" + minutos;
		return resposta;
	}

	@SuppressWarnings("unused")
	public static String retornaDataParaPersistir(String data, boolean anoB, boolean mesB, boolean diaB, boolean horaB, boolean minutosB) {

		String ano = "";
		String mes = "";
		String dia = "";
		String hora = "";
		String minutos = "";
		String segundos = "00";

		boolean anoBoolean = anoB;
		boolean mesBoolean = mesB;
		boolean diaBoolean = diaB;
		boolean horaBoolean = horaB;
		boolean minutosBoolean = minutosB;

		String resposta = "";

		for (char charRetorno : data.toCharArray()) {
			if (Character.isDigit(charRetorno)) {
				if (!anoBoolean) {
					ano += charRetorno;
				} else if (!mesBoolean) {
					mes += charRetorno;
				} else if (!diaBoolean) {
					dia += charRetorno;
				} else if (!horaBoolean) {
					hora += charRetorno;
				} else {
					minutos += charRetorno;
				}
			} else if (charRetorno == '/') {
				if (!anoBoolean) {
					anoBoolean = true;
				} else if (!mesBoolean) {
					mes = completaZeroEsquerda(2, mes);
					mesBoolean = true;
				}
			} else if (charRetorno == ' ') {
				dia = completaZeroEsquerda(2, dia);
				diaBoolean = true;
			} else if (charRetorno == ':') {
				hora = completaZeroEsquerda(2, hora);
				horaBoolean = true;
			}
		}

		minutos = completaZeroEsquerda(2, minutos);
		resposta = ano + "-" + mes + "-" + dia + " " + hora + ":" + minutos + ":" + segundos;
		return resposta;
	}

	public static String completaZeroEsquerda(int tamanho, String numero) {
		while (tamanho > numero.length()) {
			numero = "0" + numero;
		}
		return numero;
	}

	/**
	 * 
	 * @param date
	 * @param campo
	 *            - Calendar.[campo]
	 * @param amount
	 *            - quantos dias/meses/anos/horas/etc. Obs: pode ser negativo.
	 * @return
	 */
	public static Date somar(Date date, int campo, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(campo, amount);

		return c.getTime();
	}

	/*
	 * public DateUtil(String dataFormatada){ if(dataFormatada == null ||
	 * dataFormatada.equalsIgnoreCase("")){ this.data = new Date(); } else{
	 * 
	 * this.dia = dataFormatada.substring(8,10); this.mes =
	 * dataFormatada.substring(5,7); this.ano = dataFormatada.substring(0,4);
	 * this.hora = dataFormatada.substring(11,13); this.Minutos =
	 * dataFormatada.substring(14,16); GregorianCalendar gregorianCalendar = new
	 * GregorianCalendar(); gregorianCalendar.set(new Integer(ano), new
	 * Integer(mes)-1, new Integer(dia), new Integer(hora), new
	 * Integer(Minutos)); this.data = gregorianCalendar.getTime(); this.data =
	 * new Date(new Integer(ano), new Integer(mes), new Integer(dia), new
	 * Integer(hora), new Integer(Minutos)); } }
	 */

	/**
	 * Método que retorna a data formatada no padrão ( dd/MM/yyyy )
	 * 
	 * @return {@link String}
	 */
	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataFormatada = sdf.format(data);
		return dataFormatada;
	}

	/**
	 * Formata a data no formato que foi passado como parametro
	 * 
	 * @param date
	 * @return
	 */
	public static String getDataFormatada(Date date, String formato) {
		if (date == null || formato == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(date);
	}

	/**
	 * Método que retorna a data formatada no padrão ( dd/MM/yyyy )
	 * 
	 * @return {@link String}
	 */
	public String getDataFormatadaComMinutos() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		dataFormatada = sdf.format(data);

		return dataFormatada;
	}

	/**
	 * Método que retorna o ano ( dd/MM/yyyy )
	 * 
	 * @return {@link String}
	 */
	public String getAno() {
		if (ano == null || ano.equalsIgnoreCase("")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ano = sdf.format(data);
		}
		return ano;
	}

	/**
	 * Método para setar a data formatada no objeto
	 * 
	 * @param dataFormatada
	 *            {@link void}
	 */
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	/**
	 * Método que retorna a data
	 * 
	 * @return {@link Date}
	 */
	public Date getData() {
		return data;
	}

	/**
	 * Metodo para setar a data
	 * 
	 * @param Date
	 *            - data {@link void}
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * Método que retorna a data invertida no padrão ( yyyy/MM/dd )
	 * 
	 * @return {@link String}
	 */
	public String getDataInvertida() {
		if (dataFormatada == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			dataFormatada = sdf.format(data);
		}
		return dataFormatada;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public Date compararDataExpediente() {
		String dataString = this.getDataFormatadaComMinutos();
		Integer horas = new Integer(this.data.getHours());
		Date date = new Date();
		long MilisegundosFimExpediente = 18 * 3600 * 1000;
		long MilisegundosAtuais = date.getHours() * 3600 * 1000;
		MilisegundosAtuais += date.getMinutes() * 60 * 1000;
		long MilisegundosAtuaisSLA = this.data.getHours() * 3600 * 1000;
		MilisegundosAtuaisSLA += this.data.getMinutes() * 60 * 1000;
		long MilisegundosComplementares = 14 * 3600 * 1000;
		MilisegundosComplementares -= MilisegundosAtuais;
		long MilisegundosResposta = MilisegundosAtuaisSLA - MilisegundosFimExpediente;
		if (horas >= 18) {
			MilisegundosResposta += MilisegundosComplementares;
			MilisegundosResposta += MilisegundosFimExpediente;
			date = new Date(date.getTime() + MilisegundosResposta);
		} else {
			date = new Date(this.data.getTime());
		}

		return date;
	}

	public String getDataInvertidaComMinutos() {
		if (dataFormatada == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			dataFormatada = sdf.format(data);
		}
		return dataFormatada;
	}

	public Date CoverteStringData(String data) throws Exception {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = (java.util.Date) formatter.parse(data);

		} catch (ParseException e) {
			throw new Exception(e);
		}

		return date;
	}

	public Date CoverteStringDataFormatada(String data) throws Exception {

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date date = new Date();
		try {
			date = (java.util.Date) formatter.parse(data);

		} catch (ParseException e) {
			throw new Exception(e);
		}

		return date;
	}

	public static Date CoverteStringDataFormatadaMesAno(String data) throws Exception {

		DateFormat formatter = new SimpleDateFormat("MM/yyyy");

		Date date = new Date();
		try {
			date = (java.util.Date) formatter.parse(data);

		} catch (ParseException e) {
			throw new Exception(e);
		}

		return date;
	}

	public Date CoverteStringDataInvertida(String data) throws Exception {

		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		@SuppressWarnings("unused")
		Date date = new Date();
		try {
			date = (java.util.Date) formatter.parse(data);

		} catch (ParseException e) {
			throw new Exception(e);
		}

		return null;
	}

	public static Date CoverteStringDataBanco(String data) throws Exception {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = (java.util.Date) formatter.parse(data);

		} catch (ParseException e) {
			throw new Exception(e);
		}

		return date;
	}

	/*
	 * public static boolean validaData(String string){ DateUtil data = new
	 * DateUtil(string); return validaData(data); }
	 */

	public static boolean validaData(DateUtil data) {

		Integer dia;
		Integer mes;
		Integer hora;
		Integer minutos;
		if (!EhNumero(data.getDia())) {
			return false;
		} else if (!EhNumero(data.getMes())) {
			return false;
		} else if (!EhNumero(data.getAno())) {
			return false;
		} else if (!EhNumero(data.getHora())) {
			return false;
		} else if (!EhNumero(data.getMinutos())) {
			return false;
		}

		dia = new Integer(data.getDia());
		mes = new Integer(data.getMes());
		hora = new Integer(data.getHora());
		minutos = new Integer(data.getMinutos());

		if (mes > 12 || mes <= 0) {
			return false;
		} else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
			if (!(dia <= 31 && dia > 0)) {
				return false;
			}
		} else if (mes == 2) {
			if (ehBisexto(data.getAnoString())) {
				if (!(dia <= 29 && dia > 0)) {
					return false;
				}
			} else {
				if (!(dia <= 28 && dia > 0)) {
					return false;
				}
			}
		} else if (!(dia <= 30 && dia > 0)) {
			return false;
		}
		if (hora > 23 || hora < 0) {
			return false;
		}
		if (minutos > 60 || minutos < 0) {
			return false;
		}
		/*
		 * Data dataAtual = new Data(new Date()); dataAtual = new
		 * Data(dataAtual.getDataInvertidaComMinutos()); if(!ehDepois(data,
		 * dataAtual)) return false;
		 */
		return true;
	}

	public static boolean ehBisexto(String ano) {
		Integer anoInt = new Integer(ano);
		if (anoInt % 400 == 0) {
			return true;
		} else if (anoInt % 4 == 0 && anoInt % 100 != 0) {
			return true;
		}
		return false;
	}

	public static boolean EhNumero(String valor) {
		for (int i = 0; i < valor.length(); i++) {
			Character caractere = valor.charAt(i);
			if (!Character.isDigit(caractere)) {
				return false;
			}
		}
		return true;
	}

	public static boolean ehDepois(DateUtil maior, DateUtil menor) {

		if (new Integer(menor.getAnoString()) > new Integer(maior.getAnoString())) {
			return false;
		} else if (new Integer(maior.getAnoString()) > new Integer(menor.getAnoString())) {
			return true;
		} else {

			if (new Integer(menor.getMes()) > new Integer(maior.getMes())) {
				return false;
			} else if (new Integer(maior.getMes()) > new Integer(menor.getMes())) {
				return true;
			} else {

				if (new Integer(menor.getDia()) > new Integer(maior.getDia())) {
					return false;
				} else if (new Integer(maior.getDia()) > new Integer(menor.getDia())) {
					return true;
				} else {
					if (new Integer(menor.getHora()) > new Integer(maior.getHora())) {
						return false;
					} else if (new Integer(maior.getHora()) > new Integer(menor.getHora())) {
						return true;
					} else {
						if (new Integer(menor.getMinutos()) > new Integer(maior.getMinutos())) {
							return false;
						} else if (new Integer(maior.getMinutos()) > new Integer(menor.getMinutos())) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}

	}

	public DateUtil formataData(DateUtil data) {

		if (data.getDia().length() == 1) {
			data.setDia("0" + data.getDia());
		}
		if (data.getMes().length() == 1) {
			data.setMes("0" + data.getMes());
		}
		if (data.getHora().length() == 1) {
			data.setHora("0" + data.getHora());
		}
		if (data.getMinutos().length() == 1) {
			data.setMinutos("0" + data.getMinutos());
		}
		return data;
	}

	public static String diferencaEmHoras(DateUtil dataInicial, DateUtil dataFinal) {
		long diferenca = dataFinal.getData().getTime() - dataInicial.getData().getTime();
		long diferencaEmHoras = (diferenca / 1000) / 60 / 60;
		long minutosRestantes = (diferenca / 1000) / 60 % 60;
		String horas = diferencaEmHoras + "";
		if (horas.length() == 1)
			horas = "0" + horas;
		String minutos = minutosRestantes + "";
		if (minutos.length() == 1)
			minutos = "0" + minutos;
		return horas + ":" + minutos;

	}

	public static String diferencaEmDias(DateUtil dataInicial, DateUtil dataFinal) {

		long diferenca = dataFinal.getData().getTime() - dataInicial.getData().getTime();
		long diferencaEmDias = ((((diferenca / 1000) / 60) / 60) / 24);
		/* long minutosRestantes = (diferenca/1000)/60/60%24; */
		/*
		 * String horas = diferencaEmHoras+""; if(horas.length() == 1) horas =
		 * "0"+horas; String minutos = minutosRestantes+""; if(minutos.length()
		 * == 1) minutos = "0"+minutos;
		 */
		return "" + diferencaEmDias;

	}

	public String getAnoString() {
		return ano;
	}

	public String getDia() {
		if (dia != null && dia.length() == 1)
			return "0" + dia;
		return dia;
	}

	public String getMes() {
		if (mes != null && mes.length() == 1)
			return "0" + mes;
		return mes;
	}

	public String getHora() {
		if (hora != null && hora.length() == 1)
			return "0" + hora;
		return hora;
	}

	public String getMinutos() {
		if (Minutos != null && Minutos.length() == 1)
			return "0" + Minutos;
		return Minutos;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public void setMinutos(String minutos) {
		Minutos = minutos;
	}

	public String getMesTexto() {
		if (mes != null) {
			if (mes.equalsIgnoreCase("1") || mes.equalsIgnoreCase("01")) {
				return "Janeiro";
			} else if (mes.equalsIgnoreCase("2") || mes.equalsIgnoreCase("02")) {
				return "Fevereiro";
			} else if (mes.equalsIgnoreCase("3") || mes.equalsIgnoreCase("03")) {
				return "Mar\u00E7o";
			} else if (mes.equalsIgnoreCase("4") || mes.equalsIgnoreCase("04")) {
				return "Abril";
			} else if (mes.equalsIgnoreCase("5") || mes.equalsIgnoreCase("05")) {
				return "Maio";
			} else if (mes.equalsIgnoreCase("6") || mes.equalsIgnoreCase("06")) {
				return "Junho";
			} else if (mes.equalsIgnoreCase("7") || mes.equalsIgnoreCase("07")) {
				return "Julho";
			} else if (mes.equalsIgnoreCase("8") || mes.equalsIgnoreCase("08")) {
				return "Agosto";
			} else if (mes.equalsIgnoreCase("9") || mes.equalsIgnoreCase("09")) {
				return "Setembro";
			} else if (mes.equalsIgnoreCase("10")) {
				return "Outubro";
			} else if (mes.equalsIgnoreCase("11")) {
				return "Novembro";
			} else if (mes.equalsIgnoreCase("12")) {
				return "Dezembro";
			}
		}
		return mesTexto;
	}

	public void setMesTexto(String mesTexto) {
		this.mesTexto = mesTexto;
	}

	/**
	 * 
	 * @param data
	 * @param formato
	 * @return
	 * @throws ParseException
	 */
	public static String formataData(String data, String formato) throws ParseException {

		if (data != null && !data.equals("null")) {

			SimpleDateFormat formato1 = new SimpleDateFormat(formato);
			SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");

			return formato2.format(formato1.parse(data));
		}
		return "";

	}

	public static String formataData(Date data, String formato) {

		SimpleDateFormat formato1 = new SimpleDateFormat(formato);

		return formato1.format(data).toString();
	}

	/**
	 * 
	 * @param data
	 * @param formato
	 * @return
	 * @throws ParseException
	 */
	public static boolean dataMaiorDataAtual(String data, String formato) {

		if (data != null) {

			try {
				SimpleDateFormat formato1 = new SimpleDateFormat(formato);

				if (formato1.parse(data).after(new Date())) {
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public static Integer mesesEntre(Date ini, Date fim) {
		Calendar dataInicial = Calendar.getInstance();
		dataInicial.setTime(ini);

		Calendar dataFinal = Calendar.getInstance();
		dataFinal.setTime(fim);

		int count = 0;

		dataInicial.add(Calendar.MONTH, 1);

		while (dataInicial.getTime().before(dataFinal.getTime())) {
			dataInicial.add(Calendar.MONTH, 1);
			count++;
		}

		return count;

	}

	public static Date somarDias(Date date, Integer numDias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, numDias);

		return calendar.getTime();
	}

	// ======================================================================================
	// SGAP
	// =====================================================================================================

	public static Date somenteData(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date XMLGregoriaToDate(XMLGregorianCalendar xmlDate) {
		if (xmlDate != null) {
			return xmlDate.toGregorianCalendar().getTime();
		}
		return null;
	}

	/*
	 * public DateUtil(String dataFormatada){ if(dataFormatada == null ||
	 * dataFormatada.equalsIgnoreCase("")){ this.data = new Date(); } else{
	 * 
	 * this.dia = dataFormatada.substring(8,10); this.mes =
	 * dataFormatada.substring(5,7); this.ano = dataFormatada.substring(0,4);
	 * this.hora = dataFormatada.substring(11,13); this.Minutos =
	 * dataFormatada.substring(14,16); GregorianCalendar gregorianCalendar = new
	 * GregorianCalendar(); gregorianCalendar.set(new Integer(ano), new
	 * Integer(mes)-1, new Integer(dia), new Integer(hora), new
	 * Integer(Minutos)); this.data = gregorianCalendar.getTime(); this.data =
	 * new Date(new Integer(ano), new Integer(mes), new Integer(dia), new
	 * Integer(hora), new Integer(Minutos)); } }
	 */

	/*
	 * public static boolean validaData(String string){ DateUtil data = new
	 * DateUtil(string); return validaData(data); }
	 */

	// retorna o dia da semana dada uma data
	public static String retornarDiaSemana(Date date) {
		return pesquisarDiaSemana(date.getDay() + 1);
	}

	// faz a pesquisa, dado um inteiro de 1 a 7
	public static String pesquisarDiaSemana(int dia) {
		String diaSemana = null;

		switch (dia) {

		case 1: {
			diaSemana = "domingo";
			break;
		}
		case 2: {
			diaSemana = "segunda-feira";
			break;
		}
		case 3: {
			diaSemana = "ter�a-feira";
			break;
		}
		case 4: {
			diaSemana = "quarta-feira";
			break;
		}
		case 5: {
			diaSemana = "quinta-feira";
			break;
		}
		case 6: {
			diaSemana = "sexta-feira";
			break;
		}
		case 7: {
			diaSemana = "s�bado";
			break;
		}

		}
		return diaSemana;
	}

	public static String obterMesString(Date date) {
		String mes = null;

		switch (date.getMonth() + 1) {
		case 1: {
			mes = "Janeiro";
			break;
		}
		case 2: {
			mes = "Fevereiro";
			break;
		}
		case 3: {
			mes = "Mar\u00E7o";
			break;
		}
		case 4: {
			mes = "Abril";
			break;
		}
		case 5: {
			mes = "Maio";
			break;
		}
		case 6: {
			mes = "Junho";
			break;
		}
		case 7: {
			mes = "Julho";
			break;
		}
		case 8: {
			mes = "Agosto";
			break;
		}
		case 9: {
			mes = "Setembro";
			break;
		}
		case 10: {
			mes = "Outubro";
			break;
		}
		case 11: {
			mes = "Novembro";
			break;
		}
		case 12: {
			mes = "Dezembro";
			break;
		}
		}
		return mes;
	}

	public String retornaStringAnoMesDia(String dataFormatoBrasil) {
		if (!dataFormatoBrasil.equals("")) {

			if (dataFormatoBrasil.length() == 19) {
				hora = dataFormatoBrasil.substring(11, dataFormatoBrasil.length());
				dataFormatoBrasil = dataFormatoBrasil.substring(0, 10);
				String[] datas = dataFormatoBrasil.split("/");
				String[] horas = hora.split(":");
				return datas[2] + datas[1] + datas[0] + horas[0] + horas[1] + horas[2];
			}
			String[] unidades = dataFormatoBrasil.split("/");
			return unidades[2] + unidades[1] + unidades[0];
		}
		return "";
	}

	public static Date zeraHora(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date setHoraFinal(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	public static int getAno(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(Calendar.YEAR);
	}

	public static long getDiferencaEmSegundos(long milisegundosIniciais, long milisegundosFinais) {
		return ((milisegundosFinais - milisegundosIniciais) / 1000);
	}

	public static Date preencherHora(Date data, String hora) {
		if (data == null) {
			return null;
		}

		if (hora != null && !hora.trim().isEmpty()) {
			int horas = Integer.parseInt(hora.substring(0, 2));
			int minutos = Integer.parseInt(hora.substring(3, 5));

			return DateUtil.setHorarioNaData(DateUtil.descartaHoras(data), horas, minutos);
		} else {
			return DateUtil.descartaHoras(data);
		}
	}

	public static String formatoHoraPorExtenso(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH'h'mm'm'");
		return dateFormat.format(date);
	}

	public static List<Date> getDatasIntervaloMes(Date dataInicio, Date datafim) {
		List<Date> list = new ArrayList<Date>();
		do {
			list.add(dataInicio);
			dataInicio = adicionaMeses(dataInicio, 1);
		} while (mesmoMes(dataInicio, datafim) || getDiferencaEntreDatasEmMeses(dataInicio, datafim) > 0);

		return list;
	}

	/**
	 * Compara duas datas descartando horas, minutos e segundos.
	 * 
	 * @param primeiraData
	 * @param segundaData
	 * @return -1 se a primeira data for anterior � segunda. 0 se as datas forem
	 *         iguais. 1 se a primeira data for posterior � segunda.
	 * 
	 */
	public static int comparaDataDescartandoHoras(Date primeiraData, Date segundaData) {

		GregorianCalendar dataUm = new GregorianCalendar();
		dataUm.setTime(primeiraData);
		GregorianCalendar dataDois = new GregorianCalendar();
		dataDois.setTime(segundaData);

		GregorianCalendar dataUmFormatada = new GregorianCalendar();
		GregorianCalendar dataDoisFormatada = new GregorianCalendar();

		dataUmFormatada.set(dataUm.get(GregorianCalendar.YEAR), dataUm.get(GregorianCalendar.MONTH), dataUm.get(GregorianCalendar.DATE));
		dataDoisFormatada.set(dataDois.get(GregorianCalendar.YEAR), dataDois.get(GregorianCalendar.MONTH), dataDois.get(GregorianCalendar.DATE));

		if (dataUmFormatada.before(dataDoisFormatada)) {
			return -1;
		} else if (dataUmFormatada.after(dataDoisFormatada)) {
			return 1;
		} else {
			return 0;
		}

	}

	public static Long getDiferencaEmSegundos(Date primeiraData, Date segundaData) {
		long diferencaSegundos = (primeiraData.getTime() - segundaData.getTime()) / 1000;
		return new Long(diferencaSegundos);
	}

	public static Long getDiferencaEmMinutos(Date primeiraData, Date segundaData) {
		long diferencaSegundos = (primeiraData.getTime() - segundaData.getTime()) / 60000;
		return new Long(diferencaSegundos);
	}

	public static boolean isUltimoDiaDoMes(Date data) {
		Date diaSeguinte = DateUtil.adicionaDias(data, 1);
		if (mesmoMes(data, diaSeguinte)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isDataAnterior(Date primeiraData, Date segundaData) {
		if (comparaDataDescartandoHoras(primeiraData, segundaData) < 0) {
			return true;
		}
		return false;
	}

	public static boolean isDataAnteriorOuIgual(Date primeiraData, Date segundaData) {

		if (comparaDataDescartandoHoras(primeiraData, segundaData) <= 0) {

			return true;

		}

		return false;

	}

	public static boolean isDataPosterior(Date primeiraData, Date segundaData) {
		if (comparaDataDescartandoHoras(primeiraData, segundaData) > 0) {
			return true;
		}
		return false;
	}

	public static boolean isDataPosteriorOuIgual(Date primeiraData, Date segundaData) {

		if (comparaDataDescartandoHoras(primeiraData, segundaData) >= 0) {

			return true;

		}

		return false;

	}

	public static Date newDate(int dia, int mes, int ano) {
		GregorianCalendar gc = new GregorianCalendar(ano, mes - 1, dia);
		return gc.getTime();
	}

	/**
	 * Pega o n�mero de dias que cont�m em um ano passado como par�metro.
	 * 
	 * @param ano
	 * @return
	 */
	public static Long getDiasTotalNoAno(int ano) {
		Date dataInicio = newDate(1, 1, ano);
		Date dataFim = newDate(31, 12, ano);
		return getDiferencaEntreDatasEmDias(dataInicio, dataFim);
	}

	/**
	 * Pega a data atual descartando as horas
	 * 
	 * @return data atual com horas = 0
	 */
	public static Date getDataAtualDescartandoHoras() {
		Date date = new Date();
		return descartaHoras(date);
	}

	/**
	 * Descarta as horas de determinada data
	 * 
	 * @param date
	 * @return date com as horas descartadas
	 */
	public static Date descartaHoras(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * Seta determinada ano em um date
	 * 
	 * @param date
	 * @param horas
	 *            (baseado em 0-23)
	 * @param minutos
	 * @return data com a hora alterada
	 */
	public static Date setAnoNaData(Date date, int ano) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(date);
		calendario.set(Calendar.YEAR, ano);
		return calendario.getTime();
	}

	/**
	 * Seta determinada hora em um date
	 * 
	 * @param date
	 * @param horas
	 *            (baseado em 0-23)
	 * @param minutos
	 * @return data com a hora alterada
	 */
	public static Date setHorarioNaData(Date date, int horas, int minutos) {
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(date);
		calendario.set(Calendar.HOUR_OF_DAY, horas);
		calendario.set(Calendar.MINUTE, minutos);
		return calendario.getTime();
	}

	/**
	 * Seta ultima hora do dia
	 * 
	 * @param date
	 * @return date com 23 horas e 59 minutos e 59 segundos
	 */
	public static Date setUltimaHoraDoDia(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return calendar.getTime();
	}

	/**
	 * Seta primeira hora do dia
	 * 
	 * @param date
	 * @return date com 0 horas e 0 minutos e 0 segundos
	 */
	public static Date setPrimeiraHoraDoDia(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * Verifica se a uma data est� entre as outras duas de acordo com o m�s e
	 * ano.
	 * 
	 * @param mes
	 * @param ano
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static boolean estaNoIntervalo(int mes, int ano, Date dataInicio, Date dataFim) {
		Date dataComparar = newDate(1, mes, ano);
		if (dataComparar.after(dataInicio) && dataComparar.before(dataFim)) {
			return true;
		}
		return false;
	}

	/**
	 * Tranforma uma hora no formato 00h00m em minutos.
	 * 
	 * @param horaString
	 * @return Minutos de uma hora String.
	 */
	public static int transformaHoraStringEmMinutos(String horaString) {
		String[] splitHora = horaString.split("h");
		int horas = Integer.parseInt(splitHora[0]);
		int minutos = Integer.parseInt(splitHora[1].replace("m", ""));

		int totalEmMinutos = (horas * 60) + minutos;

		return totalEmMinutos;
	}

	/**
	 * Formata um valor em minutos para o formato maskHoraSimples(00h00m)
	 * 
	 * @param minutos
	 * @return String no formado maskHoraSimples(00h00m)
	 */
	public static String formataMaskHoraSimples(int minutos) {
		int horas = 0;
		if (minutos > 59) {
			horas = minutos / 60;
			minutos = minutos - (horas * 60);
		}
		return StringUtil.completaComZeros(horas + "", "2") + "h" + StringUtil.completaComZeros(minutos + "", "2") + "m";
	}

	/**
	 * Subtrai uma quantidade de anos a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de anos somado.
	 */
	public static Date subtraiAnos(Date data, int qtdeAnos) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.YEAR, -qtdeAnos);

		return calendario.getTime();
	}

	/**
	 * Subtrai uma quantidade de anos a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de anos somado.
	 */
	public static Date subtraiAnos(Date data, Long qtdeAnos) {
		return subtraiAnos(data, qtdeAnos.intValue());
	}

	/**
	 * Adiciona uma quantidade de anos a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de anos somado.
	 */
	public static Date adicionaAnos(Date data, int qtdeAnos) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.YEAR, qtdeAnos);

		return calendario.getTime();
	}

	public static Date adicionaAnos(Date data, Long qtdeAnos) {
		return adicionaAnos(data, qtdeAnos.intValue());
	}

	/**
	 * Adiciona uma quantidade de meses a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de meses somado.
	 */
	public static Date adicionaMeses(Date data, int qtdeMeses) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.MONTH, qtdeMeses);

		return calendario.getTime();
	}

	/**
	 * subtrai uma quantidade de meses a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de meses somado.
	 */
	public static Date subtraiMeses(Date data, int qtdeMeses) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.MONTH, -qtdeMeses);

		return calendario.getTime();
	}

	/**
	 * Verifica qual dia da semana a data se refere.
	 * 
	 * @param data
	 * @return Qual o dia da semana (1-7) a data se refere.
	 */
	public static int getDiaSemana(Date data) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);

		return calendario.get(Calendar.DAY_OF_WEEK);
	}

	public static String getNomeDiaSemana(Date data) {
		return getNomeDiaSemana(getDiaSemana(data));
	}

	public static String getNomeDiaSemana(int dia) {
		switch (dia) {
		case Calendar.SUNDAY:
			return "domingo";
		case Calendar.MONDAY:
			return "segunda-feira";
		case Calendar.TUESDAY:
			return "ter�a-feira";
		case Calendar.WEDNESDAY:
			return "quarta-feira";
		case Calendar.THURSDAY:
			return "quinta-feira";
		case Calendar.FRIDAY:
			return "sexta-feira";
		case Calendar.SATURDAY:
			return "s�bado";
		default:
			return "";
		}
	}

	public static String getNomeDiaSemanaAbreviado(Date data) {
		return getNomeDiaSemanaAbreviado(getDiaSemana(data));
	}

	public static String getNomeDiaSemanaAbreviado(int dia) {
		switch (dia) {
		case Calendar.SUNDAY:
			return "dom";
		case Calendar.MONDAY:
			return "seg";
		case Calendar.TUESDAY:
			return "ter";
		case Calendar.WEDNESDAY:
			return "qua";
		case Calendar.THURSDAY:
			return "qui";
		case Calendar.FRIDAY:
			return "sex";
		case Calendar.SATURDAY:
			return "sab";
		default:
			return "";
		}
	}

	/**
	 * Adiciona uma quantidade de dias a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de dias somado.
	 */
	public static Date adicionaDias(Date data, int qtdeDias) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.DAY_OF_MONTH, qtdeDias);

		return calendario.getTime();
	}

	/**
	 * Adiciona segundos a hora.
	 * 
	 * @param data
	 * @param qtdeSegundos
	 * @return
	 */
	public static Date adicionaSegundos(Date data, int qtdeSegundos) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.SECOND, qtdeSegundos);

		return calendario.getTime();

	}

	/**
	 * Adiciona uma quantidade de minutos a uma data.
	 * 
	 * @param data
	 * @param minutos
	 * @return Data com um n�mero definido de minutos somado.
	 */
	public static Date adicionaMinutos(Date data, int minutos) {

		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		calendario.add(GregorianCalendar.MINUTE, minutos);

		return calendario.getTime();
	}

	/**
	 * Subtrai uma quantidade de dias a uma data.
	 * 
	 * @param data
	 * @param qtdeDias
	 * @return Data com um n�mero definido de dias subtraido.
	 */
	public static Date subtraiDias(Date data, int qtdeDias) {
		return adicionaDias(data, -qtdeDias);
	}

	/**
	 * Subtrai uma quantidade de segundos da data.
	 * 
	 * @param data
	 * @param qtdeSegundos
	 * @return
	 */
	public static Date subtraiSegundos(Date data, int qtdeSegundos) {

		return adicionaSegundos(data, -qtdeSegundos);

	}

	/**
	 * Pega a menor data a partir de N datas passadas como par�metro.
	 * 
	 * @param datas
	 * @return
	 */
	public static Date getMenorData(Date... datas) {
		int i = datas.length;
		Date dataMenor = null;
		for (int j = 0; j < i; j++) {
			if (datas[j] != null && dataMenor == null) {
				dataMenor = (Date) datas[j].clone();
			} else {
				if (datas[j] != null && datas[j].before(dataMenor)) {
					dataMenor = (Date) datas[j].clone();
				}
			}
		}
		return dataMenor;
	}

	/**
	 * Pega a maior data a partir de N datas passadas como par�metro.
	 * 
	 * @param datas
	 * @return
	 */
	public static Date getMaiorData(Date... datas) {
		int i = datas.length;
		Date dataMaior = null;
		for (int j = 0; j < i; j++) {
			if (datas[j] != null && dataMaior == null) {
				dataMaior = (Date) datas[j].clone();
			} else {
				if (datas[j] != null && datas[j].after(dataMaior)) {
					dataMaior = (Date) datas[j].clone();
				}
			}
		}
		return dataMaior;
	}

	/**
	 * retorna um GregorianCalendar com o dia anterior da data passada
	 * 
	 * @param dataCalendario
	 * @return
	 */
	public static GregorianCalendar dataAnterior(GregorianCalendar dataCalendario) {
		dataCalendario.add(GregorianCalendar.DATE, -1);
		return dataCalendario;
	}

	/**
	 * retorna um Date com o dia anterior da data passada
	 * 
	 * @param date
	 * @return
	 */
	public static Date dataAnterior(Date date) {
		GregorianCalendar dataCalendario = new GregorianCalendar();
		dataCalendario.setTime(date);
		return dataAnterior(dataCalendario).getTime();
	}

	public static Date timeStampToDate(String data) {
		if (data.length() <= 10) {
			data += " 00:00:00.0";
		}
		Timestamp timestamp = Timestamp.valueOf(data);
		return new Date(timestamp.getTime());
	}

	public static String formatoPadrao(String date, String formato) {
		return getDataFormatada(timeStampToDate(date), formato);
	}

	public static String formatoPadrao(String date) {
		return formatoPadrao(timeStampToDate(date));
	}

	/**
	 * Formata a data no formato padrao dd/MM/yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String formatoPadrao(Date date) {
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			return dateFormat.format(date);
		} else {
			return "";
		}

	}

	public static String formatoDiaSemana(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		String dia = dateFormat.format(date);
		dia += " / " + getNomeDiaSemanaAbreviado(date);
		return dia;
	}

	/**
	 * Formata a data no formato padrao dd/MM/yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String formatoDataPorExtenso(Date date) {
		String resultado = "" + getDiaMes(date);
		resultado += " de " + getNomeMes(date);
		resultado += " de " + getAnoData(date);
		return resultado;
	}

	/**
	 * Formata a data no formato padrao dd/MM/yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String formatoArquivos(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		return dateFormat.format(date);
	}

	public static String dataAtualFormatoArquivos(String s) {
		return formatoArquivos(getDataAtualDescartandoHoras());
	}

	public static String horaAtualformatoArquivos(String s) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
		return dateFormat.format(date);
	}

	public static String horaSegundoAtualformatoArquivos(String s) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		return dateFormat.format(date);
	}

	public static String formatoHora(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(date);
	}

	public static String getNomeMes(int mes) {
		switch (mes) {
		case 1:
			return "janeiro";
		case 2:
			return "fevereiro";
		case 3:
			return "mar\u00E7o";
		case 4:
			return "abril";
		case 5:
			return "maio";
		case 6:
			return "junho";
		case 7:
			return "julho";
		case 8:
			return "agosto";
		case 9:
			return "setembro";
		case 10:
			return "outubro";
		case 11:
			return "novembro";
		case 12:
			return "dezembro";
		}
		return "";
	}

	public static String getNomeMes(Date date) {
		return getNomeMes(getMesData(date));
	}

	public static int getDiaMes() {
		return getDiaMes(new Date());
	}

	public static int getDiaMes(Date data) {
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Formata a data no formato que foi passado como parametro
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String data, String formato) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		try {
			return dateFormat.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static Date parseDate(String data) {
		return parseDate(data, "dd/MM/yyyy");
	}

	public static List<Date> getDatasIntervalo(Date dataInicio, Date datafim) {
		List<Date> list = new ArrayList<Date>();
		do {
			list.add(dataInicio);
			dataInicio = adicionaDias(dataInicio, 1);
		} while (mesmoDia(dataInicio, datafim) || datafim.after(dataInicio));
		return list;
	}

	/**
	 * verifica se duas data sao para o mesmo dia.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean mesmoDia(Date data1, Date data2) {
		if (data1 == null && data2 == null) {
			return true;
		}
		if (data1 == null || data2 == null) {
			return false;
		}
		if (formatoPadrao(data1).equals(formatoPadrao(data2))) {
			return true;
		}
		return false;
	}

	/**
	 * verifica se duas data sao do mesmo mes.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean mesmoMes(Date data1, Date data2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
		if (dateFormat.format(data1).equals(dateFormat.format(data2))) {
			return true;
		}
		return false;
	}

	/**
	 * verifica se duas data sao do mesmo ano.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean mesmoAno(Date data1, Date data2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		if (dateFormat.format(data1).equals(dateFormat.format(data2))) {
			return true;
		}
		return false;
	}

	public static boolean mesmoAno(Date data1, String data2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		if (dateFormat.format(data1).equals(data2)) {
			return true;
		}
		return false;
	}

	public static boolean mesmoAno(Date data1, Long data2) {
		return mesmoAno(data1, "" + data2);
	}

	/**
	 * verifica se Date � do mes passado por parametro.
	 * 
	 * @param Date
	 * @param mes
	 *            1-12
	 * @param ano
	 * @return
	 */
	public static boolean mesmoMes(Date data, int mes, int ano) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		return mesmoMes(calendar, mes, ano);
	}

	/**
	 * verifica se Date � do mes passado por parametro.
	 * 
	 * @param Date
	 * @param mes
	 *            1-12
	 * @param ano
	 * @return
	 */
	public static boolean mesmoMes(Date data, Long mes, Long ano) {
		return mesmoMes(data, mes.intValue(), ano.intValue());
	}

	/**
	 * verifica se Calendar � do mes passado por parametro.
	 * 
	 * @param calendar
	 * @param mes
	 *            1-12
	 * @param ano
	 * @return
	 */
	private static boolean mesmoMes(Calendar calendar, int mes, int ano) {
		if (calendar.get(Calendar.MONTH) != mes - 1)
			return false;
		if (calendar.get(Calendar.YEAR) != ano)
			return false;
		return true;
	}

	/**
	 * Recebe uma String no formato yyyy-MM-dd hh:mm:ss e passa para um objeto
	 * do tipo Date.
	 * 
	 * @param string
	 * @return
	 */
	@SuppressWarnings("all")
	public static Date converteStringParaDate(String string) {
		if (string.length() >= 10) {
			String ano = string.substring(0, 4);
			String mes = string.substring(5, 7);
			String dia = string.substring(8, 10);

			Calendar calendario = new GregorianCalendar();
			try {
				calendario.set(Calendar.YEAR, Integer.parseInt(ano));
				calendario.set(Calendar.MONTH, Integer.parseInt(mes) - 1);
				calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
				Date data = calendario.getTime();
				try {
					String hora = string.substring(11, 13);
					String minuto = string.substring(14, 16);
					String segundo = string.substring(17, 19);
					data.setHours(Integer.parseInt(hora));
					data.setMinutes(Integer.parseInt(minuto));
					data.setSeconds(Integer.parseInt(segundo));
				} catch (Exception e) {
					data.setHours(0);
					data.setMinutes(0);
					data.setSeconds(0);
				}

				return data;
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * A partir de duas datas pegar a diferen�a em dias. (Metodo deprecated use
	 * getDiferencaEntreDatasEmDias(dataInicio,dataFim))
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	@Deprecated
	public static int pegaDiferencaEntreDatasEmDias(Date dataInicio, Date dataFim) {
		return (int) getDiferencaEntreDatasEmDias(dataInicio, dataFim);
	}

	/**
	 * Retorna o total de dias a partir dos Anos, Meses e Dias passados como
	 * par�metro.
	 * 
	 * @param anos
	 * @param meses
	 * @param dias
	 * @return
	 */
	public static long getDiasAPartirDeAnosMesesDias(Long anos, Long meses, Long dias) {
		long diasTotal = 0;
		if (anos == null) {
			anos = new Long(0);
		}
		if (meses == null) {
			meses = new Long(0);
		}
		if (dias == null) {
			dias = new Long(0);
		}
		diasTotal += anos * 365;
		diasTotal += meses * 30;
		diasTotal += dias;
		return diasTotal;
	}

	/**
	 * Retorna o total de dias a partir dos Anos, Meses e Dias passados como
	 * par�metro por extenso.
	 * 
	 * @param anos
	 * @param meses
	 * @param dias
	 * @return String. Ex: 1 ano, 10 meses , 1 dia.
	 */
	public static String getDiferencaEntreDatasEmAnosMesesDiasPorExtenso(Date dataInicio, Date dataFim) {
		long[] anosMesesDias = getDiferencaEntreDatasEmAnosMesesDias(dataInicio, dataFim);
		String anosMesesDiasPorExtenso = "";
		if (anosMesesDias[0] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " ano, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " anos, ";
		}
		if (anosMesesDias[1] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " mes, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " meses, ";
		}
		if (anosMesesDias[2] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[2] + " dia.";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[2] + " dias.";
		}
		return anosMesesDiasPorExtenso;
	}

	/**
	 * Retorna o total de dias a partir dos Anos e Dias passados como par�metro
	 * por extenso.
	 * 
	 * @param anos
	 * @param dias
	 * @return String. Ex: 1 ano, 1 dia.
	 */
	public static String getDiferencaEntreDatasEmAnosDiasPorExtenso(Date dataInicio, Date dataFim) {
		long[] anosMesesDias = getDiferencaEntreDatasEmAnosDias(dataInicio, dataFim);
		String anosMesesDiasPorExtenso = "";
		if (anosMesesDias[0] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " ano, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " anos, ";
		}
		if (anosMesesDias[1] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " dia.";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " dias.";
		}
		return anosMesesDiasPorExtenso;
	}

	/**
	 * Retorna o total de dias a partir dos Anos, Meses e Dias passados como
	 * par�metro por extenso.
	 * 
	 * @param anos
	 * @param meses
	 * @param dias
	 * @return String. Ex: 1 ano, 10 meses , 1 dia.
	 */
	public static String getAnosMesesDiasAPartirDeDiasPorExtenso(long totalDias) {
		long[] anosMesesDias = getAnosMesesDiasAPartirDeDias(totalDias);
		String anosMesesDiasPorExtenso = "";
		if (anosMesesDias[0] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " ano, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " anos, ";
		}
		if (anosMesesDias[1] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " mes, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " meses, ";
		}
		if (anosMesesDias[2] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[2] + " dia.";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[2] + " dias.";
		}
		return anosMesesDiasPorExtenso;
	}

	/**
	 * Retorna o total de dias a partir dos Anos e Dias passados como par�metro
	 * por extenso.
	 * 
	 * @param anos
	 * @param dias
	 * @return String. Ex: 1 ano, 1 dia.
	 */
	public static String getAnosDiasAPartirDeDiasPorExtenso(long totalDias) {
		long[] anosMesesDias = getAnosDiasAPartirDeDias(totalDias);
		String anosMesesDiasPorExtenso = "";
		if (anosMesesDias[0] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " ano, ";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[0] + " anos, ";
		}
		if (anosMesesDias[1] == 1) {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " dia.";
		} else {
			anosMesesDiasPorExtenso += anosMesesDias[1] + " dias.";
		}
		return anosMesesDiasPorExtenso;
	}

	/**
	 * A partir de duas datas pegar a diferen�a em anos, meses e dias. ex:
	 * 01/01/10 e 01/01/10 retorna um long[] = {1,0,0}.
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static long[] getDiferencaEntreDatasEmAnosMesesDias(Date dataInicio, Date dataFim) {

		long diasTotal = getDiferencaEntreDatasEmDias(dataInicio, dataFim);

		return getAnosMesesDiasAPartirDeDias(diasTotal);
	}

	@SuppressWarnings("deprecation")
	public static int getDiferencaEntreDatasEmMeses(Date date1, Date date2) {
		int m1 = date1.getYear() * 12 + date1.getMonth();
		int m2 = date2.getYear() * 12 + date2.getMonth();
		return m2 - m1 + 1;
	}

	/**
	 * A partir de duas datas pegar a diferen�a em anos e dias. ex: 01/01/10 e
	 * 01/01/11 retorna um long[] = {1,0}.
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static long[] getDiferencaEntreDatasEmAnosDias(Date dataInicio, Date dataFim) {

		long diasTotal = getDiferencaEntreDatasEmDias(dataInicio, dataFim);

		return getAnosDiasAPartirDeDias(diasTotal);
	}

	public static long[] getAnosMesesDiasAPartirDeDias(long diasTotal) {
		long anos = diasTotal / 365;
		long meses = (diasTotal % 365) / 30;
		long dias = ((diasTotal % 365) % 30);

		long[] array = { anos, meses, dias };
		return array;

	}

//	 public static void main(String[] args) {
//	 Date inicio = DateUtil.newDate(1, 5, 1998);
//	 Date fim = DateUtil.newDate(31, 1, 2000);
	 
//	 Date inicio = DateUtil.newDate(6, 7, 2000);
//	 Date fim = DateUtil.newDate(31, 7, 2001);
	 
//	 Date inicio = DateUtil.newDate(1, 7, 2000);
//	 Date fim = DateUtil.newDate(5, 7, 2000);
		 
//		 Date inicio = DateUtil.newDate(9, 3, 2006);
//		 Date fim = DateUtil.newDate(6, 1, 2008);

	public static Long[] getAnosMesesDiasParaContagemAno365Dias(Date inicio, Date fim) {

		Long anos = DateUtil.getDiferencaEntreDatasEmAnos(inicio, fim);

		inicio = DateUtil.adicionaAnos(inicio, anos);
		// System.out.println(anos);

		Integer meses = DateUtil.getDiferencaEntreDatasEmMeses(inicio, fim) - 1;
//		--meses;
		if (meses > 11) {
			meses = 11;
		}
		inicio = DateUtil.adicionaMeses(inicio, meses);
		Long dias = DateUtil.getDiferencaEntreDatasEmDias(inicio, fim);
		while (dias < 0) {
			--dias;
			--meses;
			Date datePrimeiroDiaDoMes = DateUtil.getDatePrimeiroDiaDoMes(inicio);
			Date dateUltimoDiaDoMes = DateUtil.getDateUltimoDiaDoMes(inicio);
			Long totalDiaMes = DateUtil.getDiferencaEntreDatasEmDias(datePrimeiroDiaDoMes, dateUltimoDiaDoMes);

			dias += totalDiaMes;
		}

//		System.out.println(anos + " " + meses.longValue() + " " + dias);
		Long[] array = { anos, meses.longValue(), dias };
		
		return array;
	}

	public static long[] getAnosDiasAPartirDeDias(long diasTotal) {
		long anos = diasTotal / 365;
		long dias = (diasTotal % 365);

		long[] array = { anos, dias };
		return array;
	}

	/**
	 * A partir de duas datas pegar a diferen�a em dias. ex: 01/01/10 e 02/01/10
	 * retorna 2 dias.
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static long getDiferencaEntreDatasEmDias(Date dataInicio, Date dataFim) {
		dataFim = (Date) getProximoDia(dataFim);
		dataInicio = descartaHoras(dataInicio);
		dataFim = descartaHoras(dataFim);
		long milliSecondFim = dataFim.getTime() + 3600000;
		long milliSecondInicio = dataInicio.getTime();
		long differenceMilliSeconds = milliSecondFim - milliSecondInicio;
		return differenceMilliSeconds / 1000 / 60 / 60 / 24;
	}

	public static long getDiferencaEntreDatasEmDiasFormat(Date dataInicio, Date dataFim) throws Exception {

		DateFormat dateInstance = java.text.DateFormat.getDateInstance(DateFormat.DEFAULT, java.util.Locale.getDefault());
		dataInicio = dateInstance.parse(dateInstance.format(dataInicio));
		dataFim = dateInstance.parse(dateInstance.format(dataFim));

		dataFim = (Date) getProximoDia(dataFim);
		dataInicio = descartaHoras(dataInicio);
		dataFim = descartaHoras(dataFim);
		long milliSecondFim = dataFim.getTime() + 3600000;
		long milliSecondInicio = dataInicio.getTime();
		long differenceMilliSeconds = milliSecondFim - milliSecondInicio;
		return differenceMilliSeconds / 1000 / 60 / 60 / 24;
	}

	/**
	 * A partir de duas datas pegar a diferen�a em anos. ex: 01/01/10 e 01/01/11
	 * retorna 1 ano.
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static long getDiferencaEntreDatasEmAnos(Date dataInicio, Date dataFim) {
		dataInicio = descartaHoras(dataInicio);
		dataFim = descartaHoras(dataFim);
		long qtdDias = getDiferencaEntreDatasEmDias(dataInicio, dataFim);
		Double resultDouble = qtdDias / new Double(365.25);
		return resultDouble.longValue();
	}

	/**
	 * A partir de duas datas pegar a diferen�a em anos. ex: 01/01/10 e 01/01/11
	 * retorna 1 ano.
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	public static long getDiferencaEntreDatasEmMinutos(Date dataInicio, Date dataFim) {
		long qtdDias = getDiferencaEntreDatasEmDias(dataInicio, dataFim) - 1; // TODO
																				// esse
																				// metodo
																				// esta
																				// trazendo
																				// 1
																				// dia
																				// a
																				// mais,
																				// verificar
																				// todas
																				// as
																				// referencias
																				// no
																				// projeto...
		long minutosDia = qtdDias * 24 * 60;
		int minutosInicio = getHoraMinuto(dataInicio);
		int minutosFim = getHoraMinuto(dataFim);
		minutosDia += minutosFim - minutosInicio;
		return minutosDia;
	}

	public static long getDiferencaEntreDatasEmHoras(Date dataInicio, Date dataFim) {
		long minutos = getDiferencaEntreDatasEmMinutos(dataInicio, dataFim);
		long horas = minutos / 60;
		return horas;
	}

	/**
	 * Pega a quantidade de dias de um mes de uma data. Ex.: 01/01/2009 ->
	 * Retorna 31.
	 * 
	 * @param data
	 * @return
	 */
	public static int pegaDiasDeUmMes(Date data) {
		GregorianCalendar calendario = new GregorianCalendar();
		calendario.setTime(data);
		return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getMesData() {
		return getMesData(new Date());
	}

	/**
	 * retorna o numero do mes, apartir de 1 (janeiro = 1, fevereiro = 2, ...)
	 * 
	 * @param date
	 * @return
	 */
	public static int getMesData(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return getMesData(calendar);
	}

	/**
	 * retorna o numero do mes, apartir de 1 (janeiro = 1, fevereiro = 2, ...)
	 * 
	 * @param date
	 * @return
	 */
	public static int getMesData(GregorianCalendar date) {
		return date.get(GregorianCalendar.MONTH) + 1;
	}

	public static int getAnoData() {
		return getAnoData(new Date());
	}

	/**
	 * retorna o numero do ano da data...
	 * 
	 * @param date
	 * @return
	 */
	public static int getAnoData(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return getAnoData(calendar);
	}

	/**
	 * retorna o numero do ano da data...
	 * 
	 * @param date
	 * @return
	 */
	public static int getAnoData(GregorianCalendar date) {
		return date.get(GregorianCalendar.YEAR);
	}

	/**
	 * retorna um Date com o a data do primeiro dia do mes passado como
	 * parametro
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getDatePrimeiroDiaDoMes(int mes, int ano) {
		Date date = getCalendarPrimeiroDiaDoMes(mes, ano).getTime();
		return date;
	}

	/**
	 * retorna um Calendar com o a data do primeiro dia do mes passado como
	 * parametro
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Calendar getCalendarPrimeiroDiaDoMes(int mes, int ano) {
		GregorianCalendar gCalendar = new GregorianCalendar(ano, mes - 1, 1);
		return gCalendar;
	}

	/**
	 * retorna um Date do primeiro dia do proximo mes do Date passado por
	 * parametro
	 * 
	 * @param data
	 * @return
	 */
	public static Date getDatePrimeiroDiaDoProximoMes(Date data) {
		Date dataUltimoDiaMes = getDateUltimoDiaDoMes(data);
		return getProximoDia(dataUltimoDiaMes);
	}

	/**
	 * retorna um Date com o primeiro dia do m�s do Date passado por parametro
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDatePrimeiroDiaDoMes(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar = getCalendarPrimeiroDiaDoMes(calendar);
		return calendar.getTime();
	}

	/**
	 * Pega 31/12 do ano da data passada
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateUltimoDiaDoAno(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * Pega 01/01 do ano da data passada
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDatePrimeiroDiaDoAno(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * retorna um Calendar com o primeiro dia do m�s do Calendar passado por
	 * parametro
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar getCalendarPrimeiroDiaDoMes(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar;

	}

	/**
	 * retorna um Date com o �ltimo dia do m�s do Date passado por parametro
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateUltimoDiaDoMes(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar = getCalendarUltimoDiaDoMes(calendar);
		return calendar.getTime();
	}

	/**
	 * retorna um Calendar com o �ltimo dia do m�s do Calendar passado por
	 * parametro
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar getCalendarUltimoDiaDoMes(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar;

	}

	/**
	 * retorna um Date com o a data do ultimo dia do mes passado como parametro
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getDateUltimoDiaDoMes(Long mes, Long ano) {
		return getDateUltimoDiaDoMes(mes.intValue(), ano.intValue());
	}

	/**
	 * retorna um Date com o a data do ultimo dia do mes passado como parametro
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Date getDateUltimoDiaDoMes(int mes, int ano) {
		Date date = getCalendarUltimoDiaDoMes(mes, ano).getTime();
		return date;

	}

	/**
	 * retorna um Calendar com o a data do ultimo dia do mes passado como
	 * parametro
	 * 
	 * @param mes
	 * @param ano
	 * @return
	 */
	public static Calendar getCalendarUltimoDiaDoMes(int mes, int ano) {
		GregorianCalendar gCalendar = new GregorianCalendar(ano, mes - 1, 1);
		gCalendar.set(Calendar.DAY_OF_MONTH, gCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return gCalendar;
	}

	/**
	 * retorna um Date com o proximo dia do Date passado por parametro
	 * 
	 * @param data
	 * @return
	 */
	public static Date getProximoDia(Date data) {
		if (data == null) {
			return null;
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		return getProximoDia(calendar).getTime();
	}

	/**
	 * retorna um Calendar com o proximo dia do Calendar passado por parametro
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar getProximoDia(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		calendar.add(Calendar.DATE, 1);
		return calendar;
	}

	public static long getDiasIntersecao(Date dataInicioPeriodo1, Date dataFimPeriodo1, Date dataInicioPeriodo2, Date dataFimPeriodo2) {
		if (existeIntersecao(dataInicioPeriodo1, dataFimPeriodo1, dataInicioPeriodo2, dataFimPeriodo2)) {
			Date maiorDataInicio = DateUtil.getMaiorData(dataInicioPeriodo1, dataInicioPeriodo2);
			Date menorDataFim = DateUtil.getMenorData(dataFimPeriodo1, dataFimPeriodo2);
			return DateUtil.getDiferencaEntreDatasEmDias(maiorDataInicio, menorDataFim);
		}
		return 0;
	}

	public static boolean existeIntersecao(Date dataInicioPeriodo1, Date dataFimPeriodo1, Date dataInicioPeriodo2, Date dataFimPeriodo2) {
		// verifica se a dataInicio do per�odo1 � igual � dataInicio do
		// per�odo2, se � igual � dataFim do per�odo2, ou se est� entre as duas.
		if (estaNoIntervalo(dataInicioPeriodo1, dataInicioPeriodo2, dataFimPeriodo2, true)) {
			return true;
		}
		// verifica se a dataFim do per�odo1 � igual � dataInicio do per�odo2,
		// se � igual � dataFim do per�odo2, ou se est� entre as duas.
		if (estaNoIntervalo(dataFimPeriodo1, dataInicioPeriodo2, dataFimPeriodo2, true)) {
			return true;
		}
		if (dataFimPeriodo1 != null && dataFimPeriodo2 != null) {
			if (dataInicioPeriodo1.before(dataFimPeriodo2) && dataInicioPeriodo2.before(dataFimPeriodo1)) {
				return true;
			}
		}
		return false;
	}

	public static boolean estaNoIntervalo(Date data, Date dataInicioPeriodo, Date dataFimPeriodo, boolean consideraMesmoDia) {
		if (data == null) {
			return false;
		}
		// verifica se a data esta no intervalo de um periodo, sem considerar os
		// extremos
		if (data.after(dataInicioPeriodo) && (dataFimPeriodo == null || data.before(dataFimPeriodo))) {
			return true;
		}
		// verifica se a data esta no intervalo de um per�odo considerando os
		// extremos.
		if (consideraMesmoDia == true) {
			if (mesmoDia(data, dataInicioPeriodo) || dataFimPeriodo != null && DateUtil.mesmoDia(data, dataFimPeriodo)) {
				return true;
			}
		}
		return false;
	}

	public static int getHora(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinuto(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		return calendar.get(Calendar.MINUTE);
	}

	public static int getSegundo(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		return calendar.get(Calendar.SECOND);
	}

	public static int getHoraMinuto(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		int minutos = calendar.get(Calendar.MINUTE);
		int horaMinuto = (hora * 60) + minutos;
		return horaMinuto;
	}

	public static boolean isHoraAnterior(int hora1, int hora2) {
		if (hora1 < hora2) {
			return true;
		}
		return false;
	}

	public static boolean isHoraPosterior(int hora1, int hora2) {
		if (hora1 > hora2) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se o dia � sabado ou domingo
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isFimDeSemana(Date data) {
		int dia = getDiaSemana(data);
		if (dia == Calendar.SATURDAY || dia == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se uma data � valida
	 * 
	 * @param date
	 * @param format
	 * @return true se a data � valida, false se � invalida
	 */
	public static boolean isValidDateStr(String date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			sdf.parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * retorna a maior data entre as duas
	 * 
	 * @param data
	 * @param data2
	 * @return
	 */
	public static Date getMaiorData(Date data, Date data2) {
		if (data == null) {
			return data2;
		}
		if (data2 == null) {
			return data;
		}
		if (data.after(data2)) {
			return data;
		}
		return data2;
	}

	/**
	 * retorna a menor data entre as duas
	 * 
	 * @param data
	 * @param data2
	 * @return
	 */
	public static Date getMenorData(Date data, Date data2) {
		if (data == null) {
			return data2;
		}
		if (data2 == null) {
			return data;
		}
		if (data.before(data2)) {
			return data;
		}
		return data2;
	}

	public static List<Integer> getListaAnosEntreDatas(Date dataInicio, Date dataFim) {
		ArrayList<Integer> lista = new ArrayList<Integer>();
		Date dataIniTemp = newDate(1, 1, getAnoData((Date) dataInicio.clone()));
		Date dataFimTemp = (Date) dataFim.clone();

		while (dataIniTemp.before(dataFimTemp)) {
			lista.add(getAnoData(dataIniTemp));
			dataIniTemp = adicionaAnos(dataIniTemp, 1);
		}
		return lista;
	}

	/**
	 * Extrai apratir da data de Exercicio a qunatidade de anos decorridos.
	 * 
	 * @param dataExercicio
	 *            1931-01-01 00:00:00.0
	 * @return
	 */
	public static String getTempoDecorridoDias(String dataExercicio) {
		Date date = converteStringParaDate(dataExercicio);
		return "" + getTempoDecorridoDias(date);
	}

	public static long getTempoDecorridoDias(Date dataExercicio) {
		long diferncaDias = getDiferencaEntreDatasEmDias(dataExercicio, new Date());
		long dias = diferncaDias;
		return dias;
	}

	/**
	 * Extrai apratir da data de Exercicio a qunatidade de meses decorridos.
	 * 
	 * @param dataExercicio
	 * @return
	 */
	public static String getTempoDecorridoMeses(String dataExercicio) {
		Date date = converteStringParaDate(dataExercicio);
		return "" + getTempoDecorridoMeses(date);
	}

	public static long getTempoDecorridoMeses(Date dataExercicio) {
		long diferncaDias = getDiferencaEntreDatasEmDias(dataExercicio, new Date());
		long meses = diferncaDias / 31;
		return meses;
	}

	/**
	 * Extrai apratir da data de Exercicio a qunatidade de anos decorridos.
	 * 
	 * @param dataExercicio
	 * @return
	 */
	public static String getTempoDecorridoAnos(String dataExercicio) {
		Date date = converteStringParaDate(dataExercicio);
		return "" + getTempoDecorridoAnos(date);
	}

	public static long getTempoDecorridoAnos(Date dataExercicio) {
		long diferncaDias = getDiferencaEntreDatasEmDias(dataExercicio, new Date());
		long anos = diferncaDias / 365;
		return anos;
	}

	/**
	 * Extrai apratir da data de Exercicio o Tempo Total decorridos.
	 * 
	 * @param dataExercicio
	 * @return
	 */
	public static String getTempoDecorridoTotal(String dataExercicio) {
		Date date = converteStringParaDate(dataExercicio);
		return "" + getTempoDecorridoTotal(date);
	}

	public static String getTempoDecorridoTotal(Date dataExercicio) {
		long diferncaDias = getDiferencaEntreDatasEmDias(dataExercicio, new Date());
		long anos = diferncaDias / 365;
		long meses = (diferncaDias % 365) / 30;
		long dias = (diferncaDias % 365) % 30;
		return anos + " anos, " + meses + " meses e " + dias + " dias.";
	}

	/**
	 * Pega a data atual descartando as horas
	 * 
	 * @return data atual por extenso
	 */

	public static String getDataAtualFormatadaPorExtenso(String str) {
		String dataFormatada = formatoDataPorExtenso(new Date());
		return dataFormatada;
	}

	public static String getDataPorExtenso(String dataComunicacaoAdm) {
		Date date = converteStringParaDate(dataComunicacaoAdm);
		String dataFormatada = formatoDataPorExtenso(date);
		return dataFormatada;
	}

	public static String getHorasMinutos(int minutos) {
		int horas = 0;
		StringBuilder tempo = new StringBuilder();
		if (minutos > 60) {
			horas = minutos / 60;
			minutos = minutos - horas * 60;
		}
		if (horas < 10) {
			tempo.append("0");
		}
		tempo.append(horas);
		tempo.append("h");
		if (minutos < 10) {
			tempo.append("0");
		}
		tempo.append(minutos);
		tempo.append("m");
		return tempo.toString();
	}

	public static Date[] getDatasDoMeio(Date data1, Date data2, Date data3, Date data4) {
		Long data1Milis = data1.getTime();
		Long data2Milis = data2.getTime();
		Long data3Milis = data3.getTime();
		Long data4Milis = data4.getTime();

		ArrayList<Date> listaDatas = new ArrayList<Date>();
		listaDatas.add(data1);
		listaDatas.add(data2);
		listaDatas.add(data3);
		listaDatas.add(data4);

		Long maiorValor = new Long(0);
		Long menorValor = new Long(999999999999999999L);

		if (data1Milis > maiorValor) {
			maiorValor = data1Milis;
		}
		if (data1Milis < menorValor) {
			menorValor = data1Milis;
		}

		if (data2Milis > maiorValor) {
			maiorValor = data2Milis;
		}
		if (data2Milis < menorValor) {
			menorValor = data2Milis;
		}

		if (data3Milis > maiorValor) {
			maiorValor = data3Milis;
		}
		if (data3Milis < menorValor) {
			menorValor = data3Milis;
		}

		if (data4Milis > maiorValor) {
			maiorValor = data4Milis;
		}
		if (data4Milis < menorValor) {
			menorValor = data4Milis;
		}

		Date dataMenor = new Date(menorValor);
		Date dataMaior = new Date(maiorValor);

		LinkedList<Date> listaDatasResult = new LinkedList<Date>();
		for (Date data : listaDatas) {
			if (comparaDataDescartandoHoras(data, dataMaior) != 0 && comparaDataDescartandoHoras(data, dataMenor) != 0) {
				if (listaDatasResult.size() > 0) {
					if (data.before(listaDatasResult.get(0))) {
						listaDatasResult.addFirst(data);
					}
				}
				listaDatasResult.add(data);

			}
		}

		Date[] array = { listaDatasResult.get(0), listaDatasResult.get(1) };
		return array;
	}

	public static Long getAnosEntreDatas(Date dataInicio, Date dataFim) {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.setTime(dataInicio);

		Calendar calendarFim = new GregorianCalendar();
		calendarFim.setTime(dataFim);
		calendarFim.add(Calendar.DAY_OF_YEAR, 1);

		Long anos = new Long(calendarFim.get(Calendar.YEAR) - calendarInicio.get(Calendar.YEAR));

		if (DateUtil.adicionaAnos(dataInicio, anos).after(calendarFim.getTime())) {
			anos--;
		}
		return anos;
	}

	public static Long getMesesEntreDatas(Date dataInicio, Date dataFim) {
		Calendar calendarInicio = new GregorianCalendar();
		calendarInicio.setTime(dataInicio);

		Calendar calendarFim = new GregorianCalendar();
		calendarFim.setTime(dataFim);
		calendarFim.add(Calendar.DAY_OF_YEAR, 1);

		Long meses = new Long((calendarFim.get(Calendar.YEAR) * 12 + calendarFim.get(Calendar.MONTH)) - (calendarInicio.get(Calendar.YEAR) * 12 + calendarInicio.get(Calendar.MONTH)));

		if (DateUtil.adicionaMeses(dataInicio, meses.intValue()).after(calendarFim.getTime())) {
			meses--;
		}
		return meses;
	}

	public static Long[] subtraiDiasMesesAnosDeDiasMesesAnos(Long[] diasMesesAnos1, Long[] diasMesesAnos2) {
		Long[] diasMesesAnos = new Long[3];
		diasMesesAnos[0] = diasMesesAnos1[0];
		if (diasMesesAnos[0] == null) {
			diasMesesAnos[0] = new Long(0);
		}
		diasMesesAnos[1] = diasMesesAnos1[1];
		if (diasMesesAnos[1] == null) {
			diasMesesAnos[1] = new Long(0);
		}
		diasMesesAnos[2] = diasMesesAnos1[2];
		if (diasMesesAnos[2] == null) {
			diasMesesAnos[2] = new Long(0);
		}
		if (diasMesesAnos2 == null) {
			return diasMesesAnos1;
		}
		if (diasMesesAnos2[0] != null) {
			diasMesesAnos[0] -= diasMesesAnos2[0];
			while (diasMesesAnos[0] < 0) {
				diasMesesAnos[0] += 30;
				diasMesesAnos[1]--;
			}
		}
		if (diasMesesAnos2[1] != null) {
			diasMesesAnos[1] -= diasMesesAnos2[1];
			while (diasMesesAnos[1] < 0) {
				diasMesesAnos[1] += 12;
				diasMesesAnos[2]--;
			}
		}
		if (diasMesesAnos2[2] != null) {
			diasMesesAnos[2] -= diasMesesAnos2[2];
		}
		return diasMesesAnos;
	}

	public static Long[] subtraiDiasDeDiasMesesAnos(Long[] diasMesesAnos, Long dias) {
		if (dias == null) {
			return diasMesesAnos;
		}
		Long[] diasMesesAnos2 = new Long[3];
		diasMesesAnos2[0] = dias;
		diasMesesAnos2[1] = new Long(0);
		diasMesesAnos2[2] = new Long(0);
		return subtraiDiasMesesAnosDeDiasMesesAnos(diasMesesAnos, diasMesesAnos2);
	}

	public static boolean isAnoBissexto(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.isLeapYear(calendar.get(Calendar.YEAR));
	}

	public static Boolean dataFimValidator(Date dataMenor, Date dataMaior) {

		if ((dataMenor != null) && (dataMaior != null) && (dataMenor.after(dataMaior))) {
			return true;
		}

		return false;
	}

	public static String converterDateParaString(Date data) {

		String dataConvertida = null;

		try {

			if (data != null && (!"".equals(data))) {
				dataConvertida = new java.text.SimpleDateFormat("dd/MM/yyyy").format(data.getTime());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataConvertida;
	}

	public static Date converterStringParaDate(String data) {

		try {

			if (data != null && (!"".equals(data))) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);

				Date date = sdf.parse(data);

				if (date != null) {
					return date;
				}

			}

		} catch (Exception e) {
		}

		return null;
	}
	
	public static Date converterDataStringParaDate(String data) {

		try {

			if (data != null && (!"".equals(data))) {

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				sdf.setLenient(false);

				Date date = sdf.parse(data);

				if (date != null) {
					return date;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static XMLGregorianCalendar gerarXMLGregCalendar(Date data) throws Exception {

		if (data != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = sdf.format(data);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(date);

		}

		return null;
	}

	public static String formatarData(Date data, String formato) {

		if (data != null) {
			SimpleDateFormat formato1 = new SimpleDateFormat(formato);
			return formato1.format(data);
		}
		return null;
	}
	
	public static long transformarAnosEmDias(long anos) {
		return anos * 365l;
	}	
	// Atencao!!! Considerando que todos os meses possuem 30 dias (calculo de aposentadoria)
	public static long transformarMesesEm30Dias(long meses) {
		return meses * 30;
	}
	public static long transformarDiasEmAnos(long dias) {
		return dias / 365;
	}
	// Atencao!!! Considerando que todos os meses possuem 30 dias (calculo de aposentadoria)
	public static long transformar30DiasEmMeses(long dias) {
		return dias / 30;
	}
	public static long transformarMesesEmAnos(long meses) {
		return meses / 12;
	}
	// Atencao!!! Considerando que todos os meses possuem 30 dias (calculo de aposentadoria)
	public static long obterDiasRestanteDaTransformacaoEmMeses(long dias) {
		return dias % 30;
	}
	public static long obterDiasRestanteDaTransformacaoEmAnos(long dias) {
		return dias % 365;
	}
	public static long obterMesesRestanteDaTransformacaoEmAnos(long meses) {
		return meses % 12;
	}
	public static Long obterQtdDiasNoPeriodo(Date inicio, Date fim) {
		
		if(inicio == null || fim == null) {
			return 0L;
		}
		
		long difDias = DateUtil.getDiferencaEntreDatasEmDias(inicio, fim);
		return difDias;
	}
	
	public static Long obterTotalDias(Long dias, Long meses, Long anos) {		
		Long totalDias = 0L;		
		
		if(dias != null) {
			totalDias += dias;
		}
		if(meses != null) {
			totalDias += transformarMesesEm30Dias(meses);
		}
		if(anos != null) {
			totalDias += transformarAnosEmDias(anos);
		}
		return totalDias;
	}
	
	public static Long obterTotalDiasTransformandoMesesEmAnos(Long dias, Long meses, Long anos) {
		
		long mesesAux = meses == null ? 0 : meses;
		long anosAux = anos == null ? 0 : anos;
		long diasAux = dias == null ? 0 : dias;
		
		if(diasAux > 30) {			
			if(mesesAux > 0){
				mesesAux = mesesAux + transformar30DiasEmMeses(diasAux);
				diasAux = obterDiasRestanteDaTransformacaoEmMeses(diasAux);				
			} else {
				anosAux = anosAux + transformarDiasEmAnos(diasAux);
				diasAux = obterDiasRestanteDaTransformacaoEmAnos(diasAux);
			}
		}
		if(mesesAux >= 12) {			
			anosAux = anosAux + transformarMesesEmAnos(mesesAux);
			mesesAux = obterMesesRestanteDaTransformacaoEmAnos(mesesAux);			
			diasAux += transformarMesesEm30Dias(mesesAux);
			mesesAux = 0;
		}
		return obterTotalDias(diasAux, mesesAux, anosAux);
	}
	
	public static long[] obtemQtdDiasNoPeriodoMesesReal(Date dataInicio, Date dataFim) {
		
		Calendar dInicial = Calendar.getInstance(); 
        dInicial.setTime(dataInicio);
        Calendar dFinal = Calendar.getInstance();
        dFinal.setTime(dataFim);
        
        long qtdTotalDias = 0;  
        long qtdMeses = 0;
        long qtdDias = 0;

        Calendar calAux = Calendar.getInstance();
        calAux.setTimeInMillis(dInicial.getTimeInMillis());
        
        if(calAux.get(Calendar.MONTH) == dFinal.get(Calendar.MONTH) && dInicial.get(Calendar.YEAR) == dFinal.get(Calendar.YEAR)) {
        	qtdTotalDias = calAux.getActualMaximum(Calendar.DAY_OF_MONTH);
        	qtdDias = qtdTotalDias;
        	return new long[]{qtdTotalDias, qtdMeses, qtdTotalDias};
        }
        
        boolean somandoDias = true;
        while(somandoDias) {
        	qtdTotalDias += calAux.getActualMaximum(Calendar.DAY_OF_MONTH);
        	
        	if(calAux.get(Calendar.MONTH) == dInicial.get(Calendar.MONTH) && !(calAux.get(Calendar.DAY_OF_MONTH) == dInicial.getActualMinimum(Calendar.DAY_OF_MONTH))) {
        		qtdTotalDias -= dInicial.get(Calendar.DAY_OF_MONTH) - 1;
        		qtdDias = qtdTotalDias;
        	} else if((calAux.get(Calendar.DAY_OF_MONTH) == dInicial.getActualMinimum(Calendar.DAY_OF_MONTH)) && (calAux.get(Calendar.MONTH) == dFinal.getActualMinimum(Calendar.MONTH))) {
        		qtdDias = dFinal.get(Calendar.DAY_OF_MONTH);
        	} else {
        		qtdMeses++;
        	}
        	
        	somandoDias = calAux.get(Calendar.MONTH) != dFinal.get(Calendar.MONTH);
        	
        	if(!somandoDias) {
        		qtdTotalDias -= calAux.getActualMaximum(Calendar.DAY_OF_MONTH) - dFinal.get(Calendar.DAY_OF_MONTH);
        	}
        	
        	if(calAux.get(Calendar.MONTH) == Calendar.DECEMBER) {
        		calAux.set(Calendar.MONTH, Calendar.JANUARY);
        		calAux.set(Calendar.YEAR, calAux.get(Calendar.YEAR)+1);
        	} else {
        		calAux.set(Calendar.MONTH, calAux.get(Calendar.MONTH)+1);        		
        	}        	
        }
        
        return new long[]{qtdTotalDias, qtdMeses, qtdDias};
	}
	
	public static long calcularDiasEntreDatasAbonoMagisterio(Date dataInicio, Date dataFim) {
		
		if(dataInicio != null && dataFim != null) {
			return TimeUnit.MILLISECONDS.toDays(Math.abs(dataFim.getTime() - dataInicio.getTime())) - 2;			
		}
		return 0;		
	}
	
	public static long[] calcularDiferencaDiasMesesAnos(Date d1, Date d2Base, int qtdDiasAdicional) {
		
		Date d2 = new Date(d2Base.getTime());
		
		if(qtdDiasAdicional > 0) {
			d2 = adicionaDias(d2, qtdDiasAdicional);
		}
		
		int monthDay[] =  { 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		int year = 0;
		int month = 0;
		int day = 0;
		int increment = 0;
		if (d1.compareTo(d2)>0) {
			fromDate.setTime(d2);
			toDate.setTime(d1);
		}
		else {
			fromDate.setTime(d1);
			toDate.setTime(d2);
		}
		if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate.get(Calendar.DAY_OF_MONTH)) {
			increment = monthDay[fromDate.get(Calendar.MONTH) - 1];
		}
		if (increment == -1) {
			GregorianCalendar gc = new GregorianCalendar();
			if (gc.isLeapYear(fromDate.get(Calendar.YEAR))) {
				increment = 29;
			} else {
				increment = 28;
			}
		}
		if (increment != 0) {
			day = (toDate.get(Calendar.DAY_OF_MONTH) + increment) - fromDate.get(Calendar.DAY_OF_MONTH);
			increment = 1;
		} else {
			day = toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH);
		}
		if ((fromDate.get(Calendar.MONTH) + increment) > toDate.get(Calendar.MONTH)) {
			month = (toDate.get(Calendar.MONTH) + 12) - (fromDate.get(Calendar.MONTH) + increment);
			increment = 1;
		} else {
			month = (toDate.get(Calendar.MONTH)) - (fromDate.get(Calendar.MONTH) + increment);
			increment = 0;
		}
		year = toDate.get(Calendar.YEAR) - (fromDate.get(Calendar.YEAR) + increment);
		return new long[]{day,month,year};
	}
}
