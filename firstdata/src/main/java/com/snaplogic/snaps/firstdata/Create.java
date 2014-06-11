/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.firstdata;

import static com.snaplogic.snaps.firstdata.Constants.DOCNUM_TAG;
import static com.snaplogic.snaps.firstdata.Constants.ERROR_TAG;
import static com.snaplogic.snaps.firstdata.Constants.FD_PROXY_PKG_PREFIX;
import static com.snaplogic.snaps.firstdata.Constants.FD_REQ_TAG;
import static com.snaplogic.snaps.firstdata.Constants.GET_REGEX;
import static com.snaplogic.snaps.firstdata.Constants.IS_REGEX;
import static com.snaplogic.snaps.firstdata.Constants.MESSAGE_TAG;
import static com.snaplogic.snaps.firstdata.Constants.REASON_TAG;
import static com.snaplogic.snaps.firstdata.Constants.REGEX_SET;
import static com.snaplogic.snaps.firstdata.Constants.RESOLUTION_TAG;
import static com.snaplogic.snaps.firstdata.Constants.RESOUCE_LIST;
import static com.snaplogic.snaps.firstdata.Messages.COUNTER_DESCRIPTION;
import static com.snaplogic.snaps.firstdata.Messages.COUNTER_UNIT;
import static com.snaplogic.snaps.firstdata.Messages.CREATE_LABEL;
import static com.snaplogic.snaps.firstdata.Messages.DOCUMENT_COUNTER;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_DESC;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_LABEL;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_PROP;
import static com.snaplogic.snaps.firstdata.Messages.SNAP_DESC;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.InputSchemaProvider;
import com.snaplogic.api.MetricsProvider;
import com.snaplogic.common.SnapType;
import com.snaplogic.common.metrics.Counter;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.MetricsBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snap.schema.api.ObjectSchema;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snaps.firstdata.bean.AccountBean;

/**
 * Performs create operation in FirstData making use of FirstData SOAP API.
 * 
 * @author svatada
 **/
@General(title = CREATE_LABEL, purpose = SNAP_DESC)
@Inputs(min = 1, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = FirstDataCustomAccount.class, optional = false)
public class Create extends SimpleSnap implements MetricsProvider,
		InputSchemaProvider {
	private static final String TYPE = "Type";
    @Inject
	private FirstDataCustomAccount account;
	private String resourceType = null;
	private Counter counter;
	private static final String INT = "int";
	private static final Logger log = LoggerFactory.getLogger(Create.class);

	@Override
	public void configure(PropertyValues propertyValues)
			throws ConfigurationException {
		resourceType = propertyValues.get(RESOURCE_PROP);
	}

	@Override
	public void defineProperties(PropertyBuilder propertyBuilder) {
		propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC)
				.withAllowedValues(RESOUCE_LIST).required().add();
	}

	@Override
	public void defineMetrics(final MetricsBuilder metricsBuilder) {
		counter = metricsBuilder
				.describe(DOCUMENT_COUNTER, COUNTER_DESCRIPTION)
				.measuredIn(COUNTER_UNIT).counter();
	}

	@Override
	public void defineInputSchema(SchemaProvider provider) {
		Class<?> classType = null;
		log.debug("define schema");
		try {
			classType = Class.forName(getUFSReqClassType());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		log.debug(classType.getName());
		for (String viewName : provider.getRegisteredViewNames()) {
			log.debug(viewName);
			try {
				SchemaBuilder schemaBuilder = provider
						.getSchemaBuilder(viewName);
				schemaBuilder.withChildSchema(getSchema(provider, classType));
				schemaBuilder.build();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	protected void process(Document arg0, String arg1) {
		AccountBean bean = account.connect();
	}

	private ObjectSchema getSchema(SchemaProvider provider, Class<?> classType) {
		ArrayList<Method> setterMethods = findSetters(classType);
		ObjectSchema schema = null;
		if (!setterMethods.isEmpty()) {
			schema = provider.createSchema(SnapType.STRING,
					classType.getSimpleName());
		}
		for (Method method : setterMethods) {
			String paramType = method.getParameterTypes()[0].getName();
			if (paramType.startsWith(FD_PROXY_PKG_PREFIX)
					&& !paramType.endsWith(TYPE)) {
				Class<?> subClass = null;
				try {
					subClass = Class.forName(paramType);
				} catch (ClassNotFoundException e) {
					log.error(e.getMessage(), e);
				}
				ObjectSchema subSchema = getSchema(provider, subClass);
				if (subSchema != null) {
					schema.addChild(subSchema);
				}
			} else {
				schema.addChild(provider.createSchema(getDataTypes(paramType),
						method.getName().substring(3)));
			}
		}
		return schema;
	}

	/*
	 * Returns absolute class type for UFS request object
	 */
	private String getUFSReqClassType() {
		return new StringBuilder().append(FD_PROXY_PKG_PREFIX)
				.append(resourceType).append(FD_REQ_TAG).toString();
	}

	/*
	 * Returns absolute class type for UFS response object
	 */
	private String getCamelCaseForMethod(String resourceType) {
		return new StringBuilder()
				.append(StringUtils.substring(resourceType, 0, 1).toLowerCase())
				.append(StringUtils.substring(resourceType, 1)).toString();
	}

	/* Writes the exception records to error view */
	private void writeToErrorView(final String errMsg, final String errReason,
			final String errResoulution, Exception ex) {
		log.error(ex.getMessage(), ex);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DOCNUM_TAG, counter.getStats());
		map.put(ERROR_TAG, errMsg);
		map.put(MESSAGE_TAG, ex.getLocalizedMessage());
		map.put(REASON_TAG, errReason);
		map.put(RESOLUTION_TAG, errResoulution);
		SnapDataException snapException = new SnapDataException(
				documentUtility.newDocument(map), ex.getMessage()).withReason(
				errReason).withResolution(errResoulution);
		errorViews.write(snapException);
	}

	/* Writes the error records to error view */
	private void writeToErrorView(final String errMsg, final String errReason,
			final String errResoulution, final String errResponse) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(DOCNUM_TAG, counter.getStats());
		map.put(ERROR_TAG, errMsg);
		map.put(MESSAGE_TAG, errResponse);
		map.put(REASON_TAG, errReason);
		map.put(RESOLUTION_TAG, errResoulution);
		SnapDataException snapException = new SnapDataException(
				documentUtility.newDocument(map), errResponse).withReason(
				errReason).withResolution(errResoulution);
		errorViews.write(snapException);
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
	 * @param method
	 * @return SnapType
	 */
	public SnapType getDataTypes(String type) {
		if (StringUtils.contains(type, INT)) {
			return SnapType.INTEGER;
		} else if (StringUtils.containsIgnoreCase(type,
				Float.class.getSimpleName())
				|| StringUtils.containsIgnoreCase(type,
						Long.class.getSimpleName())
				|| StringUtils.containsIgnoreCase(type,
						Double.class.getSimpleName())) {
			return SnapType.NUMBER;
		} else if (StringUtils.containsIgnoreCase(type,
				Calendar.class.getName())) {
			return SnapType.DATETIME;
		} else
			return SnapType.STRING;
	}
}
