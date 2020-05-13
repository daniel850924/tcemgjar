package br.gov.mg.tcemg.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.text.MaskFormatter;

public class StringUtil {
	
	public static final String MASCARA_EMAIL = "\\w{0}|[A-Za-z0-9][A-Za-z0-9._-]*[A-Za-z0-9]@[A-Za-z0-9][A-Za-z0-9._-]*\\.[A-Za-z]{2,3}";

	public static String formataTextoAtributoMetodo(String nomeAtributo) {
		if ((nomeAtributo == null|| nomeAtributo.equals(""))){
			return "";
		}
		char[] textoLetras;
		textoLetras = nomeAtributo.toCharArray();
		String texto=String.valueOf(textoLetras[0]).toUpperCase();
		for (int i=1;i<textoLetras.length;i++){
			texto = texto.concat(String.valueOf(textoLetras[i]));
		}
		return texto;
	}
	
	/**
	 * M�todo exportado para a classe ValidaStringUtil, pois todos os metodos que retornam boolean
	 * @param atributoVerificar
	 * @deprecated Recomenda-se utilizar ValidaStringUtil.hasCaracterEspecial()
	 * @return
	 */
	@Deprecated
	public static boolean hasCaracterEspecial(String atributoVerificar){
		
		Pattern padrao = Pattern.compile("[a-zA-Z0-9\\s]+$");
		Matcher pesquisa = padrao.matcher(atributoVerificar);

		if(pesquisa.matches()){
			return false;
			
		}else {
			return true;
			
		}
	}

	public static String formataTextoAtributoFk(String nomeEntidade){
		char[] textoLetras = nomeEntidade.toCharArray();
		nomeEntidade=String.valueOf(textoLetras[0]).toLowerCase();

		for (int i=1;i<textoLetras.length;i++){
			nomeEntidade = nomeEntidade.concat(String.valueOf(textoLetras[i]));
		}
		return nomeEntidade;
	}
	public static String formataTextoEntidade(String texto){
		texto = texto.toLowerCase();
		texto = tiraCaracteresEspeciais(texto);
		char[] textoLetras;
		textoLetras = texto.toCharArray();
		texto=String.valueOf(textoLetras[0]).toUpperCase();

		for (int i=1;i<textoLetras.length;i++){
			if (textoLetras[(i-1)]=='_'){ // verifica se o caractere anterior � um espa�o, se for ele fica maiusculo
				texto = texto.concat(String.valueOf((textoLetras[i])).toUpperCase());
			}else{
				texto = texto.concat(String.valueOf(textoLetras[i]));
			}
		}
		texto = texto.replaceAll("_","");

		return texto;

	}

	public static String substituiEspacoPorUnderlineETiraCaracateresEspeciais(String str){
		return substituiEspacoPorUnderline(tiraCaracteresEspeciais(str));
	}

	public static String formatoDecimalArquivosFolha(String numStr, String quantidade){
		Double num = new Double(numStr);
		return formatoDecimalArquivosFolha(num,quantidade);
	}

	public static String formatoDecimalArquivosFolha(String numStr){
		Double num = new Double(numStr);
		return formatoDecimalArquivosFolha(num);
	}

	public static String formatoDecimalArquivosFolha(Double num){
		return formatoDecimalArquivosFolha(num,null);
	}
	
	public static String formatoDecimalArquivosFolha(Double num, String quantidade){
		DecimalFormat d = new DecimalFormat("#0.00");
		String numStr =  d.format(num);
		String result = tiraCaracteresEspeciais(numStr);
		if(quantidade==null){
			return result;
		}
		return completaComZeros(result,quantidade);
	}

	public static String tiraCaracteresEspeciaisECompletaComZeros(String texto, String quantidade){
		return completaComZeros(tiraCaracteresEspeciais(texto),quantidade);
	}

	public static String tiraCaracteresEspeciaisECompletaComEspacos(String texto, String quantidade){
		return completaComEspacos(tiraCaracteresEspeciais(texto),quantidade);
	}

	public static String substituiEspacoPorUnderline(String str){
		return str.replace(' ', '_');
	}
	
	public static String removerQuebraDeLinha (String s) {
		s = s.replaceAll("\n", " ");
		s = s.replaceAll("\r", " ");
		return s;
	}
	
	/**
	 * @Deprecated nome estava errado, substituida por <code>tiraCaracteresEspeciais(String texto)</code>
	 * @param texto
	 * @return
	 */
	@Deprecated 
	public static String tiraCaracateresEspeciais(String texto) {
		return tiraCaracteresEspeciais(texto);
	}

	public static String tiraCaracteresEspeciais(String texto) {
		// TODO Auto-generated method stub
		if(texto==null) {
			return null;
		}
		texto = texto.trim();
		texto = texto.replaceAll("[����]","e");
		texto = texto.replaceAll("[����]","u");
		texto = texto.replaceAll("[����]","i");
		texto = texto.replaceAll("[�����]","a");
		texto = texto.replaceAll("[�����]","o");
		texto = texto.replaceAll("�","c");

		texto = texto.replaceAll("[����]","E");
		texto = texto.replaceAll("[����]","U");
		texto = texto.replaceAll("[����]","I");
		texto = texto.replaceAll("[�����]","A");
		texto = texto.replaceAll("[�����]","O");
		texto = texto.replaceAll("�","C");
		texto = texto.replaceAll(" / "," ");
		texto = texto.replaceAll("/"," ");
		texto = texto.replaceAll("<", " ");
		texto = texto.replace(">", " ");
		texto = texto.replaceAll("[+-.:)({}&*,?!;']","");
		return texto;

	}

	public static String tiraEspaco(String texto){
		texto = texto.replaceAll(" ", "");
		return texto;
	}




	public static String substituiAcentosParaFormatoHTMLParaLink(String texto){
		if (texto==null || texto.equals("")){
			return "";
		}
		texto = texto.trim();
		//acento agudo
		texto = texto.replaceAll("�", "%E1");
		texto = texto.replaceAll("�", "%C1");
		texto = texto.replaceAll("�", "%E9");
		texto = texto.replaceAll("�", "%c9");
		texto = texto.replaceAll("�", "%ed");
		texto = texto.replaceAll("�", "%cd");
		texto = texto.replaceAll("�", "%f3");
		texto = texto.replaceAll("�", "%d3");
		texto = texto.replaceAll("�", "%da");
		texto = texto.replaceAll("�", "%fa");

		//acento circunflexo
		texto = texto.replaceAll("�", "%e2");
		texto = texto.replaceAll("�", "%c2");
		texto = texto.replaceAll("�", "%ea");
		texto = texto.replaceAll("�", "%ca");
		texto = texto.replaceAll("�", "%ee");
		texto = texto.replaceAll("�", "%ce");
		texto = texto.replaceAll("�", "%f4");
		texto = texto.replaceAll("�", "%d4");

		//acento grave
		texto = texto.replaceAll("�", "%e0");
		texto = texto.replaceAll("�", "%c0");
		texto = texto.replaceAll("�", "%e8");
		texto = texto.replaceAll("�", "%c8");
		texto = texto.replaceAll("�", "%ec");
		texto = texto.replaceAll("�", "%cc");
		texto = texto.replaceAll("�", "%f2");
		texto = texto.replaceAll("�", "%d2");
		texto = texto.replaceAll("�", "%f9");
		texto = texto.replaceAll("�", "%d9");

		// til
		texto = texto.replaceAll("�", "%e3");
		texto = texto.replaceAll("�", "%c3");
		texto = texto.replaceAll("�", "%f5");
		texto = texto.replaceAll("�", "%d5");

		// trema
		texto = texto.replaceAll("�", "%e4");
		texto = texto.replaceAll("�", "%c4");
		texto = texto.replaceAll("�", "%eb");
		texto = texto.replaceAll("�", "%cb");
		texto = texto.replaceAll("�", "%ef");
		texto = texto.replaceAll("�", "%cf");
		texto = texto.replaceAll("�", "%f6");
		texto = texto.replaceAll("�", "%d6");
		texto = texto.replaceAll("�", "%fc");
		texto = texto.replaceAll("�", "%dc");

		//cedilha
		texto = texto.replaceAll("�","%e7");
		texto = texto.replaceAll("�","%c7");
		texto = texto.replaceAll("�","%ba");
		texto = texto.replaceAll("�","%aa");
		texto = texto.replaceAll("�","%b0");
		texto = texto.replaceAll("\r\n", "%0D%0A");
		texto = texto.replaceAll("\r", "%0D%0A");
		texto = texto.replaceAll("\n", "%0D%0A");

		return texto;
	}

	public static String substituiAcentosParaFormatoHTML(String texto){
		if (texto==null || texto.equals("")){
			return "";
		}
		texto = texto.trim();
		//acento agudo
		texto = texto.replaceAll("�", "&aacute;");
		texto = texto.replaceAll("�", "&Aacute;");
		texto = texto.replaceAll("�", "&eacute;");
		texto = texto.replaceAll("�", "&Eacute;");
		texto = texto.replaceAll("�", "&iacute;");
		texto = texto.replaceAll("�", "&Iacute;");
		texto = texto.replaceAll("�", "&oacute;");
		texto = texto.replaceAll("�", "&Oacute;");
		texto = texto.replaceAll("�", "&Uacute;");
		texto = texto.replaceAll("�", "&uacute;");

		//acento circunflexo
		texto = texto.replaceAll("�", "&acirc;");
		texto = texto.replaceAll("�", "&Acirc;");
		texto = texto.replaceAll("�", "&ecirc;");
		texto = texto.replaceAll("�", "&Ecirc;");
		texto = texto.replaceAll("�", "&icirc;");
		texto = texto.replaceAll("�", "&Icirc;");
		texto = texto.replaceAll("�", "&ocirc;");
		texto = texto.replaceAll("�", "&Ocirc;");

		//acento grave
		texto = texto.replaceAll("�", "&agrave;");
		texto = texto.replaceAll("�", "&Agrave;");
		texto = texto.replaceAll("�", "&egrave;");
		texto = texto.replaceAll("�", "&Egrave;");
		texto = texto.replaceAll("�", "&igrave;");
		texto = texto.replaceAll("�", "&Igrave;");
		texto = texto.replaceAll("�", "&ograve;");
		texto = texto.replaceAll("�", "&Ograve;");
		texto = texto.replaceAll("�", "&ugrave;");
		texto = texto.replaceAll("�", "&Ugrave;");

		// til
		texto = texto.replaceAll("�", "&atilde;");
		texto = texto.replaceAll("�", "&Atilde;");
		texto = texto.replaceAll("�", "&otilde;");
		texto = texto.replaceAll("�", "&Otilde;");

		// trema
		texto = texto.replaceAll("�", "&auml;");
		texto = texto.replaceAll("�", "&Auml;");
		texto = texto.replaceAll("�", "&euml;");
		texto = texto.replaceAll("�", "&Euml;");
		texto = texto.replaceAll("�", "&iuml;");
		texto = texto.replaceAll("�", "&Iuml;");
		texto = texto.replaceAll("�", "&ouml;");
		texto = texto.replaceAll("�", "&Ouml;");
		texto = texto.replaceAll("�", "&uuml;");
		texto = texto.replaceAll("�", "&Uuml;");

		//cedilha
		texto = texto.replaceAll("�","&ccedil;");
		texto = texto.replaceAll("�","&Ccedil;");
		texto = texto.replaceAll("�","&ordm;");
		texto = texto.replaceAll("�","&ordf;");
		texto = texto.replaceAll("�","&deg;");


		return texto;
	}

	public static String substituiAcentos(String texto){
		if (texto==null || texto.equals("")){
			return "";
		}
		texto = texto.trim();
		//acento agudo
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "e");
		texto = texto.replaceAll("�", "E");
		texto = texto.replaceAll("�", "i");
		texto = texto.replaceAll("�", "I");
		texto = texto.replaceAll("�", "o");
		texto = texto.replaceAll("�", "O");
		texto = texto.replaceAll("�", "U");
		texto = texto.replaceAll("�", "u");

		//acento circunflexo
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "A");
		texto = texto.replaceAll("�", "e");
		texto = texto.replaceAll("�", "E");
		texto = texto.replaceAll("�", "i");
		texto = texto.replaceAll("�", "I");
		texto = texto.replaceAll("�", "o");
		texto = texto.replaceAll("�", "O");

		//acento grave
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "A");
		texto = texto.replaceAll("�", "e");
		texto = texto.replaceAll("�", "E");
		texto = texto.replaceAll("�", "i");
		texto = texto.replaceAll("�", "I");
		texto = texto.replaceAll("�", "o");
		texto = texto.replaceAll("�", "O");
		texto = texto.replaceAll("�", "u");
		texto = texto.replaceAll("�", "U");

		// til
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "A");
		texto = texto.replaceAll("�", "o");
		texto = texto.replaceAll("�", "O");

		// trema
		texto = texto.replaceAll("�", "a");
		texto = texto.replaceAll("�", "A");
		texto = texto.replaceAll("�", "e");
		texto = texto.replaceAll("�", "E");
		texto = texto.replaceAll("�", "i");
		texto = texto.replaceAll("�", "I");
		texto = texto.replaceAll("�", "o");
		texto = texto.replaceAll("�", "O");
		texto = texto.replaceAll("�", "u");
		texto = texto.replaceAll("�", "U");

		//cedilha
		texto = texto.replaceAll("�","c");
		texto = texto.replaceAll("�","C");


		return texto;
	}

	public static String substituiTextoParaFormatoHTML(String texto){
		if (texto==null || texto.equals("")){
			return "";
		}
		texto = texto.trim();

		// trema

		texto = texto.replaceAll("<","&lt;");
		texto = texto.replaceAll(">","&gt;");
		texto = texto.replaceAll("�","&laquo;");
		texto = texto.replaceAll("&","&amp;");
		texto = texto.replaceAll(">","&gt;");

		//acentos
		texto = substituiAcentosParaFormatoHTML(texto);

		return texto;
	}
	
	/**
	 * Subistitui os caracteres com acento para que n�o o perca na convers�o para a URL
	 * @param String texto
	 * @return String texto
	 */
	public static String substituiTextoParaURL(String texto){
		try {
			return URLEncoder.encode(texto, Charset.defaultCharset().name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String substituiFormatoHTMLParaTexto(String texto){
		if (texto==null || texto.equals("")){
			return "";
		}
		texto = texto.trim();
		//acento agudo
		texto = texto.replaceAll("&aacute;","�");
		texto = texto.replaceAll("&Aacute;","�");
		texto = texto.replaceAll("&eacute;","�");
		texto = texto.replaceAll("&Eacute;","�");
		texto = texto.replaceAll("&iacute;","�");
		texto = texto.replaceAll("&Iacute;","�");
		texto = texto.replaceAll("&oacute;","�");
		texto = texto.replaceAll("&Oacute;","�");
		texto = texto.replaceAll("&Uacute;","�");
		texto = texto.replaceAll("&uacute;","�");

		//acento circunflexo
		texto = texto.replaceAll("&acirc;","�");
		texto = texto.replaceAll("&Acirc;","�");
		texto = texto.replaceAll("&ecirc;","�");
		texto = texto.replaceAll("&Ecirc;","�");
		texto = texto.replaceAll("&icirc;","�");
		texto = texto.replaceAll("&Icirc;","�");
		texto = texto.replaceAll("&ocirc;","�");
		texto = texto.replaceAll("&Ocirc;","�");

		//acento grave
		texto = texto.replaceAll("&agrave;","�");
		texto = texto.replaceAll("&Agrave;","�");
		texto = texto.replaceAll("&egrave;","�");
		texto = texto.replaceAll("&Egrave;","�");
		texto = texto.replaceAll("&Igrave;","�");
		texto = texto.replaceAll("&Igrave;","�");
		texto = texto.replaceAll("&igrave;","�");
		texto = texto.replaceAll("&Ograve;","�");
		texto = texto.replaceAll("&ugrave;","�");
		texto = texto.replaceAll("&Ugrave;","�");

		// til
		texto = texto.replaceAll("&atilde;","�");
		texto = texto.replaceAll("&Atilde;","�");
		texto = texto.replaceAll("&otilde;","�");
		texto = texto.replaceAll("&Otilde;","�");

		// trema
		texto = texto.replaceAll("&auml;","�");
		texto = texto.replaceAll("&Auml;","�");
		texto = texto.replaceAll("&euml;","�");
		texto = texto.replaceAll("&Euml;","�");
		texto = texto.replaceAll("&iuml;","�");
		texto = texto.replaceAll("&Iuml;","�");
		texto = texto.replaceAll("&ouml;","�");
		texto = texto.replaceAll("&Ouml;","�");
		texto = texto.replaceAll("&uuml;","�");
		texto = texto.replaceAll("&Uuml;","�");

		//cedilha
		texto = texto.replaceAll("&ccedil;","�");
		texto = texto.replaceAll("&Ccedil;","�");
		texto = texto.replaceAll("&ordm;","�");
		texto = texto.replaceAll("&ordf;","�");
		texto = texto.replaceAll("&deg;","�");

		texto = texto.replaceAll("&lt;","<");
		texto = texto.replaceAll("&gt;",">");
		texto = texto.replaceAll("&laquo;","�");
		texto = texto.replaceAll("&amp;","&");
		texto = texto.replaceAll("&gt;",">");


		return texto;
	}

	/**
	 * Coloca em maiuscula, a primeira letra
	 * @param texto
	 * @param pos
	 * @return texto com a letra arrumado
	 */
	public static String captalize(String texto){
		return captalize(texto,0);
	}

	/**
	 * Coloca em maiuscula, a posicao da letra passada como parametro
	 * @param texto
	 * @param pos
	 * @return texto com a letra arrumado
	 */
	public static String captalize(String texto, int pos){
		if (!texto.equals("")){
			char[] textoLetras = texto.toCharArray();
			String letraToChange;
			letraToChange = String.valueOf(textoLetras[pos]).toUpperCase();
			textoLetras[pos] = letraToChange.charAt(0);
			String textoMudado = "";
			for (int i=0 ; i<textoLetras.length;i++){
				textoMudado = textoMudado.concat(""+textoLetras[i]);
			}
			return textoMudado;
		}
		return "";
	}

	/**
	 * Coloca em maiuscula, a posicao da letra passada como parametro
	 * @param texto
	 * @param pos
	 * @return texto com a letra arrumado
	 */
	public static String letraToMinuscula(String texto, int pos){
		if (!texto.equals("")){
			char[] textoLetras = texto.toCharArray();
			String letraToChange;
			letraToChange = String.valueOf(textoLetras[pos]).toLowerCase();
			textoLetras[pos] = letraToChange.charAt(0);
			String textoMudado = "";
			for (int i=0 ; i<textoLetras.length;i++){
				textoMudado = textoMudado.concat(""+textoLetras[i]);
			}
			return textoMudado;
		}
		return "";
	}

	public static String primeiraLetraToMinuscula(String texto){
		return letraToMinuscula(texto, 0);
	}

	/**
	 * Retorna a primeira Palavra de um texto passado como parametro
	 * @param texto
	 * @return String primeira palavra
	 */
	public static String getPrimeiraPalavraDoTexto(String texto) {
		String[] palavras = texto.split(" ");
		return palavras[0];
	}

	public static int getIndex(String str,String strf,int n){
		int index = 0;
		for(int i = 0 ; i < n ; i++){
			index = str.indexOf(strf,index+1);
		}
		return index;
	}

	public static int indexOfFechaParenteses(int indexOfAbreParenteses, String texto, char escape){
		int abreParenteses = 0;

		char[] textoLetras = texto.toCharArray();

		if (textoLetras[indexOfAbreParenteses]=='('){
			indexOfAbreParenteses++;
		}
		for (int i=indexOfAbreParenteses; i < textoLetras.length; i++){
			if (textoLetras[i] == escape){
				i++;
			}
			if (textoLetras[i]=='('){
				abreParenteses ++;
			}
			if (textoLetras[i]==')'){
				if (abreParenteses==0){
					return i;
				}
				abreParenteses --;
			}
		}
		return -1;
	}
	/**
	 * Retorna a posicao que fecha o parenteses: ")", apartir da posicao do parenteses 
	 * que abre: "(".
	 * @param Posicao do caracter que abre os parenteses: "("
	 * @param texto
	 * @return posicao do caracter que fecha o parenteses
	 */
	public static int indexOfFechaParenteses(int indexOfAbreParenteses, String texto){
		return indexOfFechaLimitador(indexOfAbreParenteses, texto, '(', ')');
	}

	/**
	 * Retorna a posicao que um limitador � fechado, apartir da posi��o de abertura e dos caracteres de abertura e fechamento
	 * @param indexOfAbreLimitador Posicao do caracter de abertura
	 * @param texto
	 * @param aberturaLimitador Caractere de abertura do limitador
	 * @param fechamentoLimitador Caractere de fechamento do limitador
	 * @return Posi��o do fechamento do limitador 
	 */
	public static int indexOfFechaLimitador(int indexOfAbreLimitador, String texto, char aberturaLimitador, char fechamentoLimitador){
		int abreParenteses = 0;

		char[] textoLetras = texto.toCharArray();

		if (textoLetras[indexOfAbreLimitador]==aberturaLimitador){
			indexOfAbreLimitador++;
		}
		for (int i=indexOfAbreLimitador; i < textoLetras.length; i++){
			if (textoLetras[i]==aberturaLimitador){
				abreParenteses ++;
			}
			if (textoLetras[i]==fechamentoLimitador){
				if (abreParenteses==0){
					return i;
				}
				abreParenteses --;
			}
		}
		return -1;
	}

	public static boolean terminaCom(String string1,
			String string2) {
		int sizeString2 = string2.length();
		int sizeString1 = string1.length();
		if(sizeString2>sizeString1){
			return false;
		}
		if(string1.substring(sizeString1-sizeString2).equals(string2)){
			return true;
		}else{
			return false;
		}
	}

	public static String[] splitCaracterForaDeParentes(String string,Character chara) {
		List<String> chars = new ArrayList<String>();
		int ultimoIndex =0;
		int parentesAbertos = 0;
		for(int i=0;i<string.length();i++){
			if(string.charAt(i)==chara&&parentesAbertos==0){
				String parteString = string.substring(ultimoIndex,i);
				chars.add(parteString);
				ultimoIndex = i+1;
			}
			if(string.charAt(i)=='('){
				parentesAbertos++;
			}else if(string.charAt(i)==')'){
				parentesAbertos--;
			}
		}
		if(chars.isEmpty()){
			chars.add(string);
		}else if(ultimoIndex<string.length()){
			chars.add(string.substring(ultimoIndex));
		}
		return chars.toArray(new String[chars.size()]);
	}

	/**
	 * retorna o conteudo entre limitadores, apartir da posi��o que abre o limitador,
	 * se a posi��o passada como parametro nao for de um parenteses, o metodo pega
	 * o primeiro parenteses, apartir da posicao passada como parametro.
	 * @param indexOfAbreLimitador Indice de abertura do limitador
	 * @param texto
	 * @param aberturaLimitador Caractere de abertura do limitador
	 * @param fechamentoLimitador Caractere de fechamento do limitador
	 * @return String do conteudo entre limitadores
	 */
	public static String getConteudoEntreLimitadores(int indexOfAbreLimitador, String texto, char aberturaLimitador, char fechamentoLimitador) {
		if (texto.charAt(indexOfAbreLimitador)!=aberturaLimitador){
			for (int i=indexOfAbreLimitador;i<texto.length();i++){
				if (texto.charAt(i)==aberturaLimitador){
					indexOfAbreLimitador = i;
					break;
				}
			}
		}

		int indexOfFechaParenteses = indexOfFechaLimitador(indexOfAbreLimitador, texto, aberturaLimitador, fechamentoLimitador);

		return texto.substring(indexOfAbreLimitador+1,indexOfFechaParenteses);
	}


	/**
	 * retorna o conteudo entre parenteses, apartir da posi��o que abre o parenteses,
	 * se a posi��o passada como parametro nao for de um parenteses, o metodo pega
	 * o primeiro parenteses, apartir da posicao passada como parametro.
	 * @param indexOfAbreParenteses
	 * @param texto
	 * @return String do conteudo entre parenteses
	 */
	public static String getConteudoEntreParenteses(int indexOfAbreParenteses, String texto){
		return getConteudoEntreLimitadores(indexOfAbreParenteses, texto, '(', ')');
	}	

	/**
	 *  pega o conteudo entre parenteses passando o primeiro abre parenteses como paramentro
	 * @param texto
	 * @return String do conteudo entre parenteses
	 */
	public static String getConteudoEntreParenteses(String texto){
		return getConteudoEntreLimitadores(texto.indexOf("("), texto, '(', ')');
	}

	public static String getConteudoEntreAspas(int indexOfAbreAspas,	String conteudo) {

		int indexOfFechaAspas = 0;

		char[] conteudoArray = conteudo.toCharArray();

		if (conteudoArray[indexOfAbreAspas]=='"'){
			indexOfAbreAspas++;
		}
		for (int i=indexOfAbreAspas; i<conteudo.length();i++){
			if (conteudoArray[i]=='"'){
				indexOfFechaAspas = i;
				break;
			}
		}

		return conteudo.substring(indexOfAbreAspas,indexOfFechaAspas);
	}

	public static String formataNumero(Long valor, String formato){
		DecimalFormat d = new DecimalFormat(formato);
		return d.format(valor);
	}
	
	/**
	 * Retorna o valor do Double formatado para o padr�o utilizado no sistema, com 2 casas decimais.
	 * @param valor
	 * @return String formatada.
	 */
	public static String formataValorDouble(Double valor){
		String formato = "#,##0.00";
		DecimalFormat d = new DecimalFormat(formato);
		return d.format(valor);
	}
	
	public static String formataValor1CasaDecimal(String valor){
		if(valor!=null) {
			String formato = "#,#0.0";
			DecimalFormat d = new DecimalFormat(formato);
			return d.format(new Double(valor));
		}
		return "0";
	}
	
	public static String formataValorDoubleParaInteiro(Double valor){
		if(valor!=null) {
			return valor.intValue()+"";
		}
		return "0";
	}

	public static String substituiPontosPorVirgula(String texto){

		return texto.replace(".",",");
	}

	public static String substituiPontosPorUnderline(String texto){

		return texto.replace(".","_");
	}

	public static String substituiAspasSimplesPorDupla(String texto){

		return texto.replace("'","''");
	}

	public static String limpaDadosOracle(String texto){
		texto = substituiAspasSimplesPorDupla(texto);
		texto = texto.replace("\\","\\\\");
		texto = texto.replace("\n","[quebraLinha]");
		texto = texto.replace("\r","[quebraLinha]");
		return texto.replace("&","\\&");
	}
	public static String limpaDadosOracleArquivo(String texto){
		texto = limpaDadosOracle(texto);
		return texto.replace("C:\\\\desenvolvimento\\\\rhfolha\\\\documentacao\\\\analise_projeto\\\\","");
	}

	public static String substituiBarraInvertidaPorBarraSimples(String texto){
		return texto.replace("\\","/");
	}

	public static String limpaDadosPostgre(String texto){
		texto = substituiAspasSimplesPorDupla(texto);
		return substituiBarraInvertidaPorBarraSimples(texto);
	}

	public static String removeMilissegundos(String texto){
		if (texto.length() > 19) {
			texto = texto.substring(0, 19);
		}
		return texto;
	}

	public static String removePrefixoFk(String texto){
		texto = texto.substring(3, texto.length());
		return texto;
	}

	public static String adicionaZeroATresAlgarismos(String texto){
		if(texto.length()==2){
			return "0"+texto;
		}else if(texto.length()==1){
			return "00"+texto;
		}else{
			return texto;
		}
	}
	public static String escapaVirgula(String texto){
		return texto.replace(",", "|,");
	}

	public static int indexOfCharAtOcurrency(String texto, char characterProcurado, int ocorrencia){
		char[] arrayTexto = texto.toCharArray();
		int kOcorrency =0;
		for (int i=0;i<arrayTexto.length;i++){
			if (arrayTexto[i]==characterProcurado){
				kOcorrency++;
				if (ocorrencia == kOcorrency){
					return i;
				}
			}
		}
		return 0;
	}
	
	public static String removeNonDigits(String str) {
		   if (str == null || str.length() == 0) {
		       return "";
		   }
		   return str.replaceAll("[^0-9]+", "");
		}

	public static String completaComEspacosADireita(String texto, String tamanhoTotal){
		StringBuilder stb = new StringBuilder(texto);
		for (int i=texto.length();i<Integer.parseInt(tamanhoTotal);i++){
			stb.append(" ");
		}
		return stb.toString();
	}
	
	public static String completaComEspacos(String texto, String tamanhoTotal){
		StringBuilder stb = new StringBuilder(texto);
		for (int i=texto.length();i<Integer.parseInt(tamanhoTotal);i++){
			stb.append(" ");
		}
		return stb.substring(0, Integer.parseInt(tamanhoTotal)).toString();
	}

	public static String completaComZeros(String texto, String tamanhoTotal){
		StringBuilder stb = new StringBuilder(texto);
		for (int i=texto.length();i<Integer.parseInt(tamanhoTotal);i++){
			stb.insert(0,"0");
		}
		return stb.toString();
	}

	public static String completaComZerosADireita(String texto, String tamanhoTotal){
		StringBuilder stb = new StringBuilder(texto);
		for (int i=texto.length();i<Integer.parseInt(tamanhoTotal);i++){
			stb.append("0");
		}
		return stb.toString();
	}

	public static String formataCnab240Nome(String texto){
		texto = tiraCaracteresEspeciais(texto);
		texto = texto.toUpperCase();
		texto = texto.trim();
		return texto;
	}

	public static String formataCnab240Cnpj(String texto){
		texto = tiraCaracteresEspeciais(texto);
		texto = texto.replace(" ", "");
		texto = texto.toUpperCase();
		return texto;
	}
	public static String formataMaiusculo(String texto){
		texto = tiraCaracteresEspeciais(texto);
		texto = formataMaiusculoComCaracteresEspeciais(texto);
		return texto;
	}
	public static String formataMaiusculoComCaracteresEspeciais(String texto){
		texto = texto.toUpperCase();
		return texto;
	}

	public static String limitaTexto(String texto, int tamanhoTotal){
		if (texto!=null&&texto.length()>tamanhoTotal){
			return texto.substring(0,tamanhoTotal);
		}
		return texto;
	}

	public static String limitaTexto(String texto, String tamanhoTotal){
		int total = Integer.parseInt(tamanhoTotal);
		return limitaTexto(texto, total);
	}

	/**
	 * Replace usados nos modelos
	 * @return
	 */
	public static String replace(String texto, String oldText, String newText){
		return texto.replace(oldText, newText);
	}

	/**
	 * Uppercase usado nos modelos
	 * @param texto
	 * @return
	 */
	public static String toUpperCase(String texto){
		return texto.toUpperCase();
	}

	public static String adicionaZeroRAIS(String sequencia){
		int numero = Integer.parseInt(sequencia);
		numero +=2;
		sequencia = String.valueOf(numero);
		while(sequencia.length()<6){
			sequencia = "0"+sequencia;
		}
		return sequencia;
	}

	public static String adicionaCaracterNaPosicao(String texto,String caracter, String i){
		StringBuilder sb = new StringBuilder("");
		sb.append(texto.substring(0, Integer.parseInt(i)));
		sb.append(caracter);
		sb.append(texto.substring(Integer.parseInt(i)));
		return sb.toString();
	}
	
	public static boolean isStringVazia(String texto) {
		if(texto==null||texto.trim().equals("")
				||texto.trim().toLowerCase().equals("null")) {
			return true;
		}
		return false;
	}
	
	public static String convertEncoding(String charsetStr, String string) {
		Charset charset = Charset.forName(charsetStr);
		return convertEncoding(charset, string);
	}

	private static String convertEncoding(Charset charset, String string) {
		CharsetDecoder decoder = charset.newDecoder();
		CharsetEncoder encoder = charset.newEncoder();
		String newString=new String();
		try {
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(string));
		    CharBuffer cbuf = decoder.decode(bbuf);
		    newString = cbuf.toString();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return newString;
	}
	
	public static String convertFromUTF8ToISO(String str) {
		Charset charsetUTF8 = Charset.forName("UTF-8");
		Charset charsetISO = Charset.forName("ISO-8859-1");
		CharsetDecoder decoder = charsetUTF8.newDecoder();
		CharsetEncoder encoder = charsetISO.newEncoder();
		String newString=new String();
		try {
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(str));
		    CharBuffer cbuf = decoder.decode(bbuf);
		    newString = cbuf.toString();
		} catch (CharacterCodingException e) {
			e.printStackTrace();
		}
		return newString;
	}
	
	public static String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static String removerAcentosForcaBruta (String string){  
		
	      string = string.replaceAll("[�����]","A");  
	      string = string.replaceAll("[�����]","a");  
	      string = string.replaceAll("[����]","E");  
	      string = string.replaceAll("[����]","e");  
	      string = string.replaceAll("����","I");  
	      string = string.replaceAll("����","i");  
	      string = string.replaceAll("[�����]","O");  
	      string = string.replaceAll("[�����]","o");  
	      string = string.replaceAll("[����]","U");  
	      string = string.replaceAll("[����]","u");  
	      string = string.replaceAll("�","C");  
	      string = string.replaceAll("�","c");   
	      string = string.replaceAll("[��]","y");  
	      string = string.replaceAll("�","Y");  
	      string = string.replaceAll("�","n");  
	      string = string.replaceAll("�","N");  
	      
	      return string;  
	}
	
	public static String SemAcento(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
	
	public static String formatar(String valor, String mascara) {

		String dado = "";
		// remove caracteres nao numericos
		for (int i = 0; i < valor.length(); i++) {
			char c = valor.charAt(i);
			if (Character.isDigit(c)) {
				dado += c;
			}
		}

		int indMascara = mascara.length();
		int indCampo = dado.length();

		for (; indCampo > 0 && indMascara > 0;) {
			if (mascara.charAt(--indMascara) == '#') {
				indCampo--;
			}
		}

		String saida = "";
		for (; indMascara < mascara.length(); indMascara++) {
			saida += ((mascara.charAt(indMascara) == '#') ? dado.charAt(indCampo++) : mascara.charAt(indMascara));
		}
		return saida;
	}
	
	public static boolean verificaExisteTexto(String criterio, String texto) {
		String[] split = criterio.split(" ");

		if (split.length == 0) {
			return texto.contains(split[0]);
		}

		for (int i = 0; i < split.length; i++) {
			if (!texto.contains(split[i]))
				return false;
		}

		return true;
	}
	
	public static String formatarCpfStringFormatoPadraoComZeroAEsquerda(String numDocumento){
		
		if(numDocumento != null && !"".equals(numDocumento)){
			try {
				
				Formatter cpfFormatadoComZeroAEsquerda = new Formatter().format("%011d", Long.valueOf(numDocumento));
				MaskFormatter mf = new MaskFormatter("###.###.###-##");  
				mf.setValueContainsLiteralCharacters(false);
				return mf.valueToString(cpfFormatadoComZeroAEsquerda.toString());
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public static String getNomeMes(int mes) {
		switch(mes){
		case 1:
			 return "Janeiro".toUpperCase();
		case 2:
			 return "Fevereiro".toUpperCase();
		case 3:
			 return "Mar\u00E7o".toUpperCase();
		case 4:
			 return "Abril".toUpperCase();
		case 5:
			 return "Maio".toUpperCase();
		case 6:
			 return "Junho".toUpperCase();
		case 7:
			 return "Julho".toUpperCase();
		case 8:
			 return "Agosto".toUpperCase();
		case 9:
			 return "Setembro".toUpperCase();
		case 10:
			 return "Outubro".toUpperCase();
		case 11:
			 return "Novembro".toUpperCase();
		case 12:
			 return "Dezembro".toUpperCase();
		}
		return "";
	}
	
	public static int getIntStringMes(String mes) {
		switch(mes.toUpperCase()){
		case "JANEIRO":
			 return 1;
		case "FEVEREIRO":
			 return 2;
		case "MARÇO":
			 return 3;
		case "ABRIL":
			 return 4;
		case "MAIO":
			 return 5;
		case "JUNHO":
			 return 6;
		case "JULHO":
			 return 7;
		case "AGOSTO":
			 return 8;
		case "SETEMBRO":
			 return 9;
		case "OUTUBRO":
			 return 10;
		case "NOVEMBRO":
			 return 11;
		case "DEZEMBRO":
			 return 12;
		}
		return 0;
	}

	public static String toUpperCaseRemoveAccentuation(String str) {

		if (str != null && !str.trim().isEmpty()) {

			return removerAcentos(str).toUpperCase();

		}

		return null;

	}
	
	/**
	 * Valida o valor informado verificando a mascara em regex.
	 * 
	 * @param valor
	 *            a ser validado
	 * @param mascaraRegex
	 *            código regex para validação
	 * @return true caso o valor seja validado pela mascara
	 */
	public static final boolean validaMascaraRegex(String valor, String mascaraRegex) {
		Pattern p = Pattern.compile(mascaraRegex);
		Matcher m = p.matcher(valor);
		return m.matches();
	}
	
	public static boolean validarEmail(String email) {
		return validaMascaraRegex(email, MASCARA_EMAIL);
	}
	
	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}
	
	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
	}

}