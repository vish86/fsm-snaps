package com.snaplogic.snaps.lunex.http.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snaplogic.snaps.lunex.constants.Constants.LunexSnaps;

public class RequestProcessor {
	private static final Logger log = LoggerFactory
	        .getLogger(RequestProcessor.class);
	private static final String CONTENT_LENGTH = "Content-length";
	private static RequestProcessor rHandler = null;
	static {
		rHandler = new RequestProcessor();
	}
	public static RequestProcessor getInstance() {
		return rHandler;
	}
	private RequestProcessor() {

	}

	public String execute(RequestBuilder rBuilder)
	        throws MalformedURLException, IOException {
		try {
			URL api_url = new URL(rBuilder.getURL());
			log.debug("Lunex host info:" + api_url.getHost() + " Path:"
			        + api_url.getPath());
			URLConnection urlConn = api_url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			for (Pair<String, String> header : rBuilder.getHeaders()) {
				urlConn.setRequestProperty(header.getKey(), header.getValue());
			}
			urlConn.setRequestProperty(CONTENT_LENGTH,
			        rBuilder.getRequestBodyLenght());
			StringBuffer response = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			        urlConn.getInputStream()));
			String line;
			log.debug("processing resource:" + rBuilder.getSnapType());
			if (rBuilder.getSnapType() != LunexSnaps.Read) {
				String paramsJson = null;
				if (!StringUtils
				        .isEmpty(paramsJson = rBuilder.getRequestBody())) {
					DataOutputStream cgiInput = new DataOutputStream(
					        urlConn.getOutputStream());
					cgiInput.writeBytes(paramsJson);
					cgiInput.flush();
					cgiInput.close();
				}
			}

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			return response.toString();

		} catch (MalformedURLException me) {
			log.error(me.getMessage(), me);
			throw me;
		} catch (IOException ioe) {
			log.error(ioe.getMessage(), ioe);
			throw ioe;
		}
	}
}