package com.snaplogic.snaps.lunex.http.handler;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.snaplogic.api.ExecutionException;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snaps.lunex.constants.Constants.LunexSnaps;
import com.snaplogic.snaps.lunex.constants.ServiceURIInfo;
import static com.snaplogic.snaps.lunex.constants.Constants.*;
import static com.snaplogic.snaps.lunex.constants.Messages.INVALID_URI;
import static com.snaplogic.snaps.lunex.constants.Messages.INVALID_URI_RESOLUTION;
;

public class RequestBuilder {
	private String lunexEndpontHostIP = null;
	private List<Pair<String, String>> Headers = null;
	private LunexSnaps snapType = null;
	private String requestBody = null;
	private Document doc = null;
	private Object resource = null;
	private List<Pair<String, ExpressionProperty>> queryParams;

	public String getURL() {
		return resolveUrl(doc);
	}

	public List<Pair<String, String>> getHeaders() {
		return Headers;
	}

	public LunexSnaps getSnapType() {
		return snapType;
	}

	public String getRequestBody() {
		return requestBody;
	}
	public String getRequestBodyLenght() {
		return requestBody.length() + "";
	}

	public RequestBuilder addEndPointIP(String host) {
		this.lunexEndpontHostIP = host;
		return this;
	}

	public RequestBuilder addHeaders(List<Pair<String, String>> headers) {
		Headers = headers;
		return this;
	}

	public RequestBuilder addSnapTye(LunexSnaps snaps) {
		this.snapType = snaps;
		return this;
	}

	public RequestBuilder addRequestBody(String requestBody) {
		this.requestBody = requestBody;
		return this;
	}

	public RequestBuilder addDoc(Document doc) {
		this.doc = doc;
		return this;
	}

	public RequestBuilder addResource(Object resource) {
		this.resource = resource;
		return this;
	}

	public RequestBuilder addQueryParams(
	        List<Pair<String, ExpressionProperty>> queryParams) {
		this.queryParams = queryParams;
		return this;
	}

	private String resolveUrl(final Document document) {
		try {
			String resourceSpecificUri = null;
			switch (snapType) {
				case Create :
					resourceSpecificUri = ServiceURIInfo.CR_URI_LIST
					        .get(resource);
					break;
				case Read :
					resourceSpecificUri = ServiceURIInfo.RR_URI_LIST
					        .get(resource);
					break;
				case Delete :
					resourceSpecificUri = ServiceURIInfo.DR_URI_LIST
					        .get(resource);
					break;
				case Update :
					resourceSpecificUri = ServiceURIInfo.UR_URI_LIST
					        .get(resource);
					break;
			}
			if (queryParams != null) {
				CharSequence source, target;
				for (Pair<String, ExpressionProperty> paramPair : queryParams) {
					source = OPENTAG + paramPair.getLeft() + CLOSETAG;
					target = paramPair.getRight().eval(document).toString();
					resourceSpecificUri = resourceSpecificUri.replace(source,
					        target);
				}
			}
			return new String(HTTP + lunexEndpontHostIP + resourceSpecificUri);
		} catch (Exception e) {
			String msg = String.format(INVALID_URI, new String(HTTP
			        + lunexEndpontHostIP));
			throw new ExecutionException(e, msg).withReason(msg)
			        .withResolution(INVALID_URI_RESOLUTION);
		}
	}

}
