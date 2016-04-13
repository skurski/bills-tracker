package com.skurski.gwt.demo.client.app;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.skurski.gwt.demo.client.widget.login.LoginWidget;


public class Project implements EntryPoint {

    private final LoginWidget loginWidget = new LoginWidget();

    public void onModuleLoad() {
        RootPanel.get().add(loginWidget);
    }

}
