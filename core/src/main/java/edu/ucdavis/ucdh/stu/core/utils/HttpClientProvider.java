package edu.ucdavis.ucdh.stu.core.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpClientProvider {
	private static final HttpClientProvider provider = new HttpClientProvider();
	private HttpClient client = null;
	private SSLContextBuilder contextBuilder = null;
	private SSLConnectionSocketFactory socketFactory = null;

	/**
	 * <p>Primary constructor</p>
	 * 
	 */
	public HttpClientProvider() {
		super();
		try {
			this.contextBuilder = SSLContextBuilder.create().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
					return true;
				}
			});
			this.socketFactory = new SSLConnectionSocketFactory(contextBuilder.build(), new NoopHostnameVerifier());
		} catch (Exception e) {
			// oops!
		}
	}

	public static HttpClient getClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		return provider.getHttpClient();
	}

	private HttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		if (client == null) {
			client = buildHttpClient();
		}
//		return client;
		return buildHttpClient();
	}

	private HttpClient buildHttpClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        return HttpClients.custom().setSSLSocketFactory(socketFactory).setConnectionManager(new PoolingHttpClientConnectionManager()).build();
	}
}
