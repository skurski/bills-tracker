package com.skurski.gwt.demo.client.widget.form;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.skurski.gwt.demo.client.library.CompositeComponent;
import com.skurski.gwt.demo.client.widget.popup.InfoDialogBoxWidget;
import com.skurski.gwt.demo.shared.dto.CategoryDto;
import com.skurski.gwt.demo.shared.dto.ExpenseDto;
import com.skurski.gwt.demo.shared.service.CategoryService;
import com.skurski.gwt.demo.shared.service.ExpenseService;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by pskurski on 4/1/2016.
 */
// TODO: add validation for fields in Expense Add form
public class ExpenseFormWidget extends CompositeComponent {
    @UiField
    HTMLPanel formPanel;

    private static final Logger logger = Logger.getLogger("ExpenseFormWidget");

    private final Button addButton = new Button("Add Expense");
    private final FlexTable layout = new FlexTable();
    private final TextBox titleField = new TextBox();
    private final ListBox categories = new ListBox();
    private final TextBox amountField = new TextBox();
    private final DatePicker datePicker = new DatePicker();
    private final Label textDate = new Label();
    private final InfoDialogBoxWidget infoWindow = new InfoDialogBoxWidget();

    private final Map<String, CategoryDto> categoryValues = new HashMap<String, CategoryDto>();

    private final ExpenseService expenseService = GWT.create(ExpenseService.class);
    private final CategoryService categoryService = GWT.create(CategoryService.class);

    public ExpenseFormWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        initialize();
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
    }

    @Override
    public void initHandlers() {
        getHandlerRegistrations().add(addButton.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (titleField.getValue().equals("") ||
                        amountField.getValue().equals("") ||
                        textDate.getText().equals("")) {

                    infoWindow.setContentText("Please fill in all fields!");
                    infoWindow.center();
                    return;
                }
                callToExpenseServiceSetOne();

            }
        }, ClickEvent.getType()));
    }

    private void callToExpenseServiceSetOne() {
        ExpenseDto newExpense = new ExpenseDto(
                titleField.getValue(),
                new BigDecimal(amountField.getValue()),
                textDate.getText(),
                categoryValues.get(categories.getSelectedValue())
        );

        expenseService.setOne(newExpense, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                throw new RuntimeException("Call to server failed: " + exception.getMessage());
            }

            @Override
            public void onSuccess(Method method, Void response) {
                infoWindow.setContentText("Expense saved succesfully!");
                infoWindow.center();
                clearFields();
            }
        });
    }

    private void clearFields() {
        titleField.setText("");
        categories.setItemSelected(0, true);
        amountField.setText("");
        textDate.setText("");
        datePicker.setValue(new Date());
    }

    private void initialize() {
        createForm();
        addFieldsToForm();
        wrapForm();
    }

    private void wrapForm() {
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setWidget(layout);

        formPanel.add(decPanel);
    }

    private void addFieldsToForm() {
        categoryService.getAll(new MethodCallback<List<CategoryDto>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                throw new RuntimeException("Call to server failed: " + exception.getMessage());
            }

            @Override
            public void onSuccess(Method method, List<CategoryDto> response) {
                layout.setHTML(1, 0, "Title");
                layout.setWidget(1, 1, titleField);

                for (CategoryDto categoryDto: response) {
                    categories.addItem(categoryDto.getName());
                    categoryValues.put(categoryDto.getName(), categoryDto);
                }
                categories.setVisibleItemCount(1);
                layout.setHTML(2, 0, "Category");
                layout.setWidget(2, 1, categories);

                layout.setHTML(3, 0, "Amount");
                layout.setWidget(3, 1, amountField);

                datePicker.setValue(new Date(), true);
                layout.setHTML(4, 0, "Date");
                layout.setWidget(4, 1, textDate);
                layout.setWidget(5, 1, datePicker);
                datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
                    @Override
                    public void onValueChange(ValueChangeEvent<Date> event) {
                        textDate.setText(DateTimeFormat.getFormat("yyyy-MM-dd").format(event.getValue()));
                    }
                });

                layout.setWidget(6, 1, addButton);
            }
        });
    }

    private void createForm() {
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        // Add a title to the form
        layout.setHTML(0, 0, "Add Expense");
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(
                0, 0, HasHorizontalAlignment.ALIGN_CENTER);
    }

    interface ExpenseFormWidgetUiBinder extends UiBinder<Widget, ExpenseFormWidget> {}
    private static ExpenseFormWidgetUiBinder uiBinder = GWT.create(ExpenseFormWidgetUiBinder.class);
}