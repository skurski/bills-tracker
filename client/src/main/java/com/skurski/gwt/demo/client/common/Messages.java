package com.skurski.gwt.demo.client.common;


import com.google.gwt.core.client.GWT;

public interface Messages extends com.google.gwt.i18n.client.Messages {

    @DefaultMessage("Finance Helper App")
    @Key("getNorthPanelHeader")
    String getNorthPanelHeader();

    @DefaultMessage("Made by Peter Skurski")
    @Key("getSouthPanelFooter")
    String getSouthPanelFooter();

    @DefaultMessage("Expenses")
    @Key("getExpenseHeader")
    String getExpenseHeader();

    class Default {
        private static final Messages INSTANCE = GWT.create(Messages.class);
        public static Messages getInstance() {
            return INSTANCE;
        }
    }
}
