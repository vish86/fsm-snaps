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

package com.snaplogic.snaps.firstdata.soap.suggestions;

import com.snaplogic.common.jsonpath.InvalidPathException;
import com.snaplogic.snap.api.editor.XMLTemplateEvaluatorImpl;

import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * A template evaluator implementation for SOAP Execute snap
 *
 * @author svatada
 */
public class SOAPExecuteTemplateEvaluatorImpl extends XMLTemplateEvaluatorImpl {

    /**
     * @param defaultValue
     * @return SOAPExecuteTemplateEvaluatorImpl
     */
    public SOAPExecuteTemplateEvaluatorImpl withDefaultValue(final String defaultValue) {
        emptyReference = defaultValue;
        return this;
    }

    @Override
    protected Object findValue(String templateKey, Map<String, Object> allValues)
            throws InvalidPathException {
        Object value = super.findValue(templateKey, allValues);
        if (value == null) {
            allValues.put(templateKey, StringUtils.EMPTY);
            return StringUtils.EMPTY;
        }
        return value;
    }
}
