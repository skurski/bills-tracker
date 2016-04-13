package com.skurski.gwt.demo.client.widget.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.skurski.gwt.demo.client.library.CompositeComponent;
import com.skurski.gwt.demo.client.panel.main.LandingPage;
import com.skurski.gwt.demo.client.widget.popup.ConfirmDialogBoxWidget;
import com.skurski.gwt.demo.client.widget.popup.InfoDialogBoxWidget;
import com.skurski.gwt.demo.shared.dto.CategoriesDto;
import com.skurski.gwt.demo.shared.dto.CategoryDto;
import com.skurski.gwt.demo.shared.dto.MemberDto;
import com.skurski.gwt.demo.shared.service.CategoryService;
import com.skurski.gwt.demo.shared.service.LoginService;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by psk on 02.04.16.
 */
public class LoginWidget extends CompositeComponent {
    @UiField
    TextBox loginBox;
    @UiField
    TextBox passwordBox;
    @UiField
    Label completionLabel1;
    @UiField
    Label completionLabel2;

    private static final Logger logger = Logger.getLogger("LoginWidget");

    private final CategoryService categoryService = GWT.create(CategoryService.class);
    private final LoginService loginService = GWT.create(LoginService.class);

    private Boolean tooShort = false;

    public LoginWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
    }

    @UiHandler("buttonSubmit")
    void doClickSubmit(ClickEvent event) {
        if (tooShort) {
            showValidationInfoInPopupWindow("Login or Password is too short!");
        } else {
            final MemberDto memberDto = new MemberDto("", "", loginBox.getText(), passwordBox.getText());
            callToLoginServiceAuthMember(memberDto);
        }
    }

    private void callToLoginServiceAuthMember(MemberDto memberDto) {
        loginService.authMember(memberDto, new MethodCallback<MemberDto>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                throw new RuntimeException("Failed to call remote service: " + exception.getMessage());
            }

            @Override
            public void onSuccess(Method method, MemberDto response) {
                if (response != null) {
                    RootPanel.get().remove(LoginWidget.this);
                    callToCategoryServiceGetAll();
                } else {
                    showValidationInfoInPopupWindow("Not valid credentials!");
                    clearFields();
                }
            }
        });
    }

    private void clearFields() {
        loginBox.setText("");
        passwordBox.setText("");
    }

    private void showValidationInfoInPopupWindow(String text) {
        InfoDialogBoxWidget infoDialogBoxWidget = new InfoDialogBoxWidget();
        infoDialogBoxWidget.setHeaderTitle("Validation");
        infoDialogBoxWidget.setContentText(text);
        infoDialogBoxWidget.center();
    }

    private void callToCategoryServiceGetAll() {
        categoryService.getAll(new MethodCallback<List<CategoryDto>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                throw new RuntimeException("Failed to call remote service: " + exception.getMessage());
            }

            @Override
            public void onSuccess(Method method, List<CategoryDto> response) {
                final CategoriesDto categoriesDto = new CategoriesDto();
                categoriesDto.setCategories(response);
                RootLayoutPanel.get().add(new LandingPage(categoriesDto));
            }
        });
    }

    @UiHandler("loginBox")
    void handleLoginChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 6) {
            completionLabel1.setText("Login too short (Size must be > 6)");
            tooShort = true;
        } else {
            tooShort = false;
            completionLabel1.setText("");
        }
    }

    @UiHandler("passwordBox")
    void handlePasswordChange(ValueChangeEvent<String> event) {
        if (event.getValue().length() < 4) {
            tooShort = true;
            completionLabel2.setText("Password too short (Size must be > 4)");
        } else {
            tooShort = false;
            completionLabel2.setText("");
        }
    }

    interface LoginWidgetUiBinder extends UiBinder<Widget, LoginWidget> {}
    private static LoginWidgetUiBinder uiBinder = GWT.create(LoginWidgetUiBinder.class);
}
