package edu.ucdavis.ucdh.stu.core.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpClientProvider {
	private static final HttpClientProvider provider = new HttpClientProvider();
	private HttpClient client = null;

	/**
	 * <p>Primary constructor</p>
	 */
	public HttpClientProvider() {
		super();
	}

	public static HttpClient getClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return provider.getHttpClient();
	}

	private HttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		if (client == null) {
			client = buildHttpClient();
		}
		return client;
	}

	private HttpClient buildHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build(), new NoopHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
	}
}
