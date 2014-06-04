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


/**
 * This class holds all the static final variables and enum types.
 *
 * @author svatada
 */
public class Constants {

    static enum SnapsModel {
        Create("Create"), Update("Update"), Read("Read"), Delete("Delete");
        private final String resource;

        private SnapsModel(String resource) {
            this.resource = resource;
        }

        @Override
        public String toString() {
            return resource;
        }
    }
}