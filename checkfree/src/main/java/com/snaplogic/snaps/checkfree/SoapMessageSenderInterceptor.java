/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.checkfree;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;



import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.apache.http.Header;

import com.google.common.collect.Lists;

/**
 * Intercepts the SOAP message before it gets send to the endpoint to allow
 * configuring additional headers defined by the snap.
 * 
 * @author svatada
 */
public class SoapMessageSenderInterceptor extends
		AbstractPhaseInterceptor<Message> {
	private static final String ORG_APACHE_CXF_TRANSPORT_CONDUIT = "org.apache.cxf.transport"
			+ ".Conduit";
	private final Header[] snapHeaders;
	private final boolean trustAllCerts;
	// Create a trust manager that does not validate certificate chains
	private static final TrustManager[] TM_TRUST_ALL = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	} };
	private Message message;
//	private String keyStorePass;
//	private String keyStoreFileUrl;

	public SoapMessageSenderInterceptor(Header[] snapHeaders,
			boolean trustAllCerts, String keyStorePass, String keyStoreFileUrl) {
		super(Phase.PREPARE_SEND);
		this.snapHeaders = snapHeaders;
		this.trustAllCerts = trustAllCerts;
//		this.keyStorePass = keyStorePass;
//		this.keyStoreFileUrl = keyStoreFileUrl;
	}

	@Override
	public void handleMessage(Message message) {
		if (trustAllCerts) {
			 HTTPConduit httpConduit = (HTTPConduit) message.get(ORG_APACHE_CXF_TRANSPORT_CONDUIT);
	         TLSClientParameters clientParameters = httpConduit.getTlsClientParameters();
	         if (clientParameters == null) {
	              clientParameters = new TLSClientParameters();
	              httpConduit.setTlsClientParameters(clientParameters);
	         }
	         clientParameters.setDisableCNCheck(true);
	         clientParameters.setTrustManagers(TM_TRUST_ALL);
//			URLConnection urlConnection;
//			try {
//				urlConnection = new URI(keyStoreFileUrl).toURL()
//						.openConnection();
//				urlConnection.connect();
//				InputStream keystoreIStream = urlConnection.getInputStream();
//				HTTPConduit httpConduit = (HTTPConduit) message
//						.get(ORG_APACHE_CXF_TRANSPORT_CONDUIT);
//				TLSClientParameters tlsCP = new TLSClientParameters();
//				KeyStore keyStore = KeyStore.getInstance("JKS");
//				keyStore.load(keystoreIStream, keyStorePass.toCharArray());
//				KeyManager[] myKeyManagers = getKeyManagers(keyStore,
//						keyStorePass);
//				tlsCP.setKeyManagers(myKeyManagers);
//
//				KeyStore trustStore = KeyStore.getInstance("JKS");
//				trustStore.load(keystoreIStream, keyStorePass.toCharArray());
//				TrustManager[] myTrustStoreKeyManagers = getTrustManagers(trustStore);
//				tlsCP.setTrustManagers(myTrustStoreKeyManagers);
//
//				httpConduit.setTlsClientParameters(tlsCP);
//			} catch (Exception e) {
//				System.err.print(e);
//			}
		}

		if (snapHeaders != null) {
			Map<String, List<String>> headers = CastUtils
					.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));
			if (headers == null) {
				headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			} else if (headers instanceof HashMap) {
				Map<String, List<String>> headers2 = new TreeMap<>(
						String.CASE_INSENSITIVE_ORDER);
				headers2.putAll(headers);
				headers = headers2;
			}

			// add the snap headers to the protocol headers of the message,
			// otherwise the request headers of the underlying connection object
			// will not receive
			// those additional headers during CXF connection configuration.
			for (Header header : snapHeaders) {
				List<String> values = Lists.newArrayList();
				values.add(header.getValue());
				headers.put(header.getName(), values);
			}
		}
		this.message = message;
	}

	/**
	 * Get the message
	 * 
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	private static TrustManager[] getTrustManagers(KeyStore trustStore)
			throws NoSuchAlgorithmException, KeyStoreException {
		String alg = KeyManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory fac = TrustManagerFactory.getInstance(alg);
		fac.init(trustStore);
		return fac.getTrustManagers();
	}

	private static KeyManager[] getKeyManagers(KeyStore keyStore,
			String keyPassword) throws GeneralSecurityException, IOException {
		String alg = KeyManagerFactory.getDefaultAlgorithm();
		char[] keyPass = keyPassword != null ? keyPassword.toCharArray() : null;
		KeyManagerFactory fac = KeyManagerFactory.getInstance(alg);
		fac.init(keyStore, keyPass);
		return fac.getKeyManagers();
	}
}