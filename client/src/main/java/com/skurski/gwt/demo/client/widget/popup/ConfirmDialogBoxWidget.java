package com.skurski.gwt.demo.client.widget.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.skurski.gwt.demo.shared.service.DialogCallback;

import java.util.logging.Logger;

/**
 * Created by psk on 02.04.16.
 */
public class ConfirmDialogBoxWidget extends DialogBox {
    @UiField
    Button okButton;
    @UiField
    Button cancelButton;
    @UiField
    Label label;

    private static final Logger logger = Logger.getLogger("ConfirmDialogBoxWidget");

    private final String headerText = "Confirmation";

    private DialogCallback dialogCallback;

    public ConfirmDialogBoxWidget() {
        setWidget(uiBinder.createAndBindUi(this));

        setAnimationEnabled(true);
        setGlassEnabled(true);
        initHandlers();
        setText(headerText);
    }

    public void setContentText(String text) {
        label.setText(text);
    }

    public void setDialogCallback(DialogCallback dialogCallback) {
        this.dialogCallback = dialogCallback;
    }

    public void setHeaderTitle(String title) {
        if (title == null) {
            return;
        }
        setText(title);
    }

    private void initHandlers() {
        cancelButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ConfirmDialogBoxWidget.this.hide();
                dialogCallback.onCancel();
            }
        });
        okButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ConfirmDialogBoxWidget.this.hide();
                dialogCallback.onOk();
            }
        });
    }

    interface ConfirmDialogBoxWidgetUiBinder extends UiBinder<Widget, ConfirmDialogBoxWidget> {}
    private static ConfirmDialogBoxWidgetUiBinder uiBinder = GWT.create(ConfirmDialogBoxWidgetUiBinder.class);
}