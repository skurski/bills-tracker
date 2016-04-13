package com.skurski.gwt.demo.client.widget.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

import java.util.logging.Logger;

/**
 * Created by pskurski on 4/1/2016.
 */
public class InfoDialogBoxWidget extends DialogBox {
    @UiField
    Label label;
    @UiField
    Button okButton;

    private static final Logger logger = Logger.getLogger("InfoDialogBoxWidget");

    private final String headerText = "Information";

    public InfoDialogBoxWidget() {
        setWidget(uiBuider.createAndBindUi(this));

        setAnimationEnabled(true);
        setGlassEnabled(true);
        initHandlers();
        setText(headerText);
    }

    public void setContentText(String text) {
        label.setText(text);
    }

    public void setHeaderTitle(String title) {
        if (title == null) {
            return;
        }
        setText(title);
    }

    private void initHandlers() {
        okButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                InfoDialogBoxWidget.this.hide();
            }
        });
    }

    interface InfoDialogBoxWidgetUiBinder extends UiBinder<Widget, InfoDialogBoxWidget> {}
    private static InfoDialogBoxWidgetUiBinder uiBuider = GWT.create(InfoDialogBoxWidgetUiBinder.class);
}
