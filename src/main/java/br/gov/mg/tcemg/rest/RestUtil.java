package br.gov.mg.tcemg.rest;

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

import br.gov.mg.tcemg.enumerador.TipoChamadaRestEnum;

public class RestUtil {

	private String token;
	private String proxyUrl;
	private Integer proxyPort;

	public RestUtil(String token) {

		this.token = token;

	}

	public RestUtil(String proxyUrl, Integer proxyPort) {

		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;

	}

	public RestUtil(String token, String proxyUrl, Integer proxyPort) {

		this.token = token;
		this.proxyUrl = proxyUrl;
		this.proxyPort = proxyPort;

	}

	public RestUtil() {

		this.token = null;
		this.proxyUrl = null;
		this.proxyPort = null;

	}

	public String executarChamadaRest(String url, String parametro, TipoChamadaRestEnum tipoChamada) throws UnsupportedEncodingException, IOException {

		HttpURLConnection request = null;

		request = obterConexao(url);

		try {

			request.setDoOutput(true);
			request.setDoInput(true);

			request.setRequestProperty("Content-Type", "application/json");

			if (token != null) {

				request.setRequestProperty("Authorization", "Bearer " + token);

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