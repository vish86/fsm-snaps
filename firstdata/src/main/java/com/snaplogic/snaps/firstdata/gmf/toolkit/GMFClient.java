package com.snaplogic.snaps.firstdata.gmf.toolkit;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.snaplogic.common.SnapType;
import com.snaplogic.snap.schema.api.ObjectSchema;
import com.snaplogic.snap.schema.api.SchemaProvider;

/* This below program demonstrates the procedure of performing various payment transactions
 * through Datawire using different protocols SOAP, HTTP POST or TCP/IP. Please use either SOAP 
 * or HTTP POST  protocol to process transaction through Datawire over the Internet.  
 * TCP/IP is to be used for VPN or leased line.
 */
public class GMFClient {
	static final String DOCNUM_TAG = "DocInfo";
	static final String ERROR_TAG = "Error";
	static final String STATUS_CODE_TAG = "statusCode";
	static final String RESOLUTION_TAG = "Resolution";
	static final String REASON_TAG = "Reason";
	static final String MESSAGE_TAG = "Message";
	static final String IS_REGEX = "^is[A-Z].*";
	static final String GET_REGEX = "^get[A-Z].*";
	static final String REGEX_SET = "^set[A-Z].*";
	static final String FD_PROXY_PKG_PREFIX = "com.snaplogic.snaps.gmf.toolkit.proxy.";
	static final String FD_REQ_TAG = "RequestDetails";

	private void getSchema(Class<?> classType) {
		System.out.println(classType.getSimpleName() + "---->");
		for (Method method : findSetters(classType)) {
			String returnType = method.getGenericReturnType().toString();
			if (returnType.startsWith(FD_PROXY_PKG_PREFIX)) {
				Class<?> subClass = null;
				try {
					subClass = Class.forName(returnType);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				getSchema(subClass);
			} else {
				System.out.println("Field---" + method.getName().substring(3));
			}
		}
	}

	/*
	 * finds the declared setter methods in the given classtype
	 */
	static ArrayList<Method> findSetters(Class<?> classType) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = classType.getDeclaredMethods();
		for (Method method : methods) {
			if (isSetter(method)) {
				list.add(method);
			}
		}
		return list;
	}

	/**
	 * finds the declared getter methods in the given classtype
	 * 
	 * @param c
	 * @return ArrayList<Method>
	 */
	public static ArrayList<Method> findGetters(Class<?> c) {
		ArrayList<Method> list = new ArrayList<Method>();
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				list.add(method);
			}
		}
		return list;
	}

	/*
	 * finds whether the given method setter or not
	 */
	static boolean isSetter(Method method) {
		return Modifier.isPublic(method.getModifiers())
				&& method.getReturnType().equals(void.class)
				&& method.getParameterTypes().length == 1
				&& method.getName().matches(REGEX_SET);
	}

	/*
	 * finds whether the given method getter or not
	 */
	static boolean isGetter(Method method) {
		if (Modifier.isPublic(method.getModifiers())
				&& method.getParameterTypes().length == 0) {
			String methodName = method.getName();
			if (methodName.matches(GET_REGEX)
					&& !method.getReturnType().equals(void.class))
				return true;
			if (methodName.matches(IS_REGEX)
					&& method.getReturnType().equals(boolean.class))
				return true;
		}
		return false;
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public static void main(String[] args){
		GMFClient g= new GMFClient();
		Class<?> classType=null;
		try {
			classType = Class.forName(FD_PROXY_PKG_PREFIX+"Credit"+FD_REQ_TAG);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		g.getSchema(classType);
	}
	public static void main1(String[] args) throws Exception {

		/* Transaction request in XML format */
		String requestString = "";

		/* Transaction response in XML format received from Data wire */
		String responseString = "";

		/* Create Authorization request for a sample Credit Sale transaction. */
		CreditRequest creditSaleReq = new CreditRequest();

		/*
		 * Generate Client Ref Number in the format <STAN>|<TPPID>, right
		 * justified and left padded with "0"
		 */
		String clientRef = creditSaleReq.GetClientRef();

		requestString = creditSaleReq.GetXMLData();
		requestString = requestString.replaceAll("gmfMessageVariants", "GMF");
		System.out.println("GMF Credit Request == " + requestString);

		// Send data using SOAP protocol
		try {
			responseString = new SOAPHandler().SendMessage(requestString,
					clientRef);
		} catch (Exception e) {
			System.out.println("SOAP Exception: " + e.toString());
		}
		System.out.println("Successful SOAP Credit response: " + "\n"
				+ responseString + "\n");

		/* Create Authorization request for a sample Debit Sale transaction. */
		DebitRequest debitSaleReq = new DebitRequest();

		/*
		 * Generate Client Ref Number in the format <STAN>|<TPPID>, right
		 * justified and left padded with "0"
		 */
		clientRef = debitSaleReq.GetClientRef();

		requestString = debitSaleReq.GetXMLData();
		requestString = requestString.replaceAll("gmfMessageVariants", "GMF");
		System.out.println("GMF Debit Request == " + requestString);
		// Send data using SOAP protocol
		try {
			responseString = new SOAPHandler().SendMessage(requestString,
					clientRef);
		} catch (Exception e) {
			System.out.println("SOAP Exception: " + e.toString());
		}
		System.out.println("Successful SOAP Debit response: " + responseString
				+ "\n");
	}
}