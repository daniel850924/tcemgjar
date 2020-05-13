package gov.mg.tce.api.rest.util;

import gov.mg.tce.api.rest.constantes.ConstantesRest;
import gov.mg.tce.api.rest.enumerador.TipoChamadaRestEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class RestUtil {

	private String token;
	private String tokenProxy;
	private String proxyUrl;
	private Integer proxyPort;
	private String dateformat;
	
	public RestUtil(String dateformat) {
		this.dateformat = dateformat;
	}
	
	public RestUtil(String tokenProxy, String token) {
		this.token = token;
		this.tokenProxy = tokenProxy;
	}

	public RestUtil(String proxyUrl, Integer proxyPort) {

		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;
	}

	public RestUtil(String tokenProxy, String token, String proxyUrl, Integer proxyPort) {

		this.token = token;
		this.tokenProxy = tokenProxy;
		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;
	}

	public RestUtil() {

		this.token = null;
		this.tokenProxy = null;
		this.proxyUrl = null;
		this.proxyPort = null;
	}

	public String executarChamadaRest(TipoChamadaRestEnum tipoChamada, Map<String, String> parametrosCabecalho, String url, String parametro) throws UnsupportedEncodingException, IOException {

		HttpURLConnection request = null;

		url = URLEncoder.encodeURL(url);
		request = obterConexao(url);

		try {

			request.setDoOutput(true);
			request.setDoInput(true);

			request.setRequestProperty("Content-Type", "application/json");
			if (token != null) {
				request.setRequestProperty(ConstantesRest.AUTH_HEADER_TOKEN_KEY, ConstantesRest.AUTH_HEADER_TOKEN_VALUE_PREFIX + token);
			}
			if(tokenProxy != null){
				request.setRequestProperty(ConstantesRest.AUTH_HEADER_TOKENPROXY_KEY, ConstantesRest.AUTH_HEADER_TOKENPROXY_VALUE_PREFIX + tokenProxy);
			}
			
			if(parametrosCabecalho != null){
				for (String parametroCabecalho : parametrosCabecalho.keySet()) {
					request.setRequestProperty(parametroCabecalho, parametrosCabecalho.get(parametroCabecalho));
				}
			}

			request.setRequestMethod(tipoChamada.name());
			request.connect();

			if (parametro != null) {

				try (OutputStream outputStream = request.getOutputStream()) {
					outputStream.write(parametro.getBytes("UTF-8"));
				}
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(((InputStream) request.getContent())));
			StringBuilder builder = new StringBuilder();
			String aux = "";

			while ((aux = br.readLine()) != null) {

				builder.append(aux);
			}

			return builder.toString();

		} finally {

			request.disconnect();
		}

	}

	private HttpURLConnection obterConexao(String url) throws MalformedURLException, IOException {

		if (proxyUrl != null && proxyPort != null) {

			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyUrl, proxyPort));
			return (HttpURLConnection) new URL(url).openConnection(proxy);
		} else {

			return (HttpURLConnection) new URL(url).openConnection();
		}

	}
	
}