package com.skurski.gwt.demo.client.widget.table;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.NoSelectionModel;
import com.skurski.gwt.demo.client.library.CompositeComponent;
import com.skurski.gwt.demo.client.widget.form.ExpenseFormWidget;
import com.skurski.gwt.demo.client.widget.popup.ConfirmDialogBoxWidget;
import com.skurski.gwt.demo.shared.dto.CategoryDto;
import com.skurski.gwt.demo.shared.dto.ExpenseDto;
import com.skurski.gwt.demo.shared.service.CategoryService;
import com.skurski.gwt.demo.shared.service.DialogCallback;
import com.skurski.gwt.demo.shared.service.ExpenseService;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by pskurski on 3/29/2016.
 */
// TODO: add sorting for columns
public class CellTableWidget extends CompositeComponent {
    @UiField
    HTMLPanel contentPanel;
    @UiField
    CellTable<ExpenseDto> table;
    @UiField
    SimplePager pager;
    @UiField
    Button updateButton;
    @UiField
    Button deleteButton;
    @UiField
    Button addButton;

    private static final Logger logger = Logger.getLogger("CellTableWidget");

    private AsyncDataProvider<ExpenseDto> provider;

    private final ExpenseService expenseService = GWT.create(ExpenseService.class);
    private final CategoryService categoryService = GWT.create(CategoryService.class);

    private final CompositeComponent formWidget = new ExpenseFormWidget();

    private final Map<Integer, ExpenseDto> editedExpenses = new HashMap<Integer, ExpenseDto>();
    private final Map<Integer, ExpenseDto> deletedExpenses = new HashMap<Integer, ExpenseDto>();

    private final Map<String, CategoryDto> categoryValues = new HashMap<String, CategoryDto>();

    public CellTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        init();
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
    }

    public void initHandlers() {
        initUpdateButtonHandler();
        initDeleteButtonHandler();
        initCreateButtonHandler();
    }

    public void setModel(CategoryDto categoryDto) {
        initAsyncDataProvider(categoryDto);
    }

    public void setModel(String all) {
        initAsyncDataProvider(all);
    }

    private void init() {
        initTable();
        addColumns();
        addSelectionModel();
        initPager();
        updateButton.setText("Update");
        deleteButton.setText("Delete");
        addButton.setText("Add Expense");
    }

    private void initPager() {
        pager.setDisplay(table);
    }

    private void initCreateButtonHandler() {
        getHandlerRegistrations().add(addButton.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                contentPanel.clear();
                contentPanel.add(formWidget);
            }
        }, ClickEvent.getType()));
    }

    private void initDeleteButtonHandler() {
        getHandlerRegistrations().add(deleteButton.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ConfirmDialogBoxWidget confirmWidget = new ConfirmDialogBoxWidget();
                confirmWidget.setContentText("Do you really want to delete selected expenses?");
                confirmWidget.setDialogCallback(new DialogCallback() {
                    @Override
                    public void onOk() {
                        List<ExpenseDto> expenseToDelete = new ArrayList<ExpenseDto>(deletedExpenses.values());
                        expenseService.delete(expenseToDelete, new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                throw new RuntimeException("Call to server failed: " + exception.getMessage());
                            }

                            @Override
                            public void onSuccess(Method method, Void response) {
                                // trigger the onRangeChange method of AsyncDataProvider
                                table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
                            }
                        });
                    }
                    @Override
                    public void onCancel() {
//                        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
                    }
                });
                confirmWidget.center();
            }
        }, ClickEvent.getType()));
    }

    private void initUpdateButtonHandler() {
        getHandlerRegistrations().add(updateButton.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ConfirmDialogBoxWidget confirmWidget = new ConfirmDialogBoxWidget();
                confirmWidget.setContentText("Do you really want to save the changes?");
                confirmWidget.setDialogCallback(new DialogCallback() {
                    @Override
                    public void onOk() {
                        List<ExpenseDto> expenseToUpdate = new ArrayList<ExpenseDto>(editedExpenses.values());
                        expenseService.setAll(expenseToUpdate, new MethodCallback<Void>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                throw new RuntimeException("Call to server failed: " + exception.getMessage());
                            }

                            @Override
                            public void onSuccess(Method method, Void response) {
                                // trigger the onRangeChange method of AsyncDataProvider
                                table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
                            }
                        });
                    }
                    @Override
                    public void onCancel() {
//                        table.setVisibleRangeAndClearData(table.getVisibleRange(), true);
                    }
                });
                confirmWidget.center();
            }
        }, ClickEvent.getType()));
    }

    private void initAsyncDataProvider(final CategoryDto categoryDto) {
        provider = new AsyncDataProvider<ExpenseDto>() {
            @Override
            protected void onRangeChanged(HasData<ExpenseDto> display) {
                final int start = display.getVisibleRange().getStart();
                final int length = display.getVisibleRange().getLength();

                expenseService.getRowsNumberByCategory(categoryDto.getId(), new MethodCallback<Integer>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        throw new RuntimeException("Call to server failed: " + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(Method method, Integer response) {
                        updateRowCount(response, true);
                    }
                });

                expenseService.getByRangeAndCategory(categoryDto.getId(), start, length, new MethodCallback<List<ExpenseDto>>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        throw new RuntimeException("Call to server failed: " + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(Method method, List<ExpenseDto> response) {
                        updateRowData(start, response);
                    }
                });
            }
        };

        provider.addDataDisplay(table);
    }

    private void initAsyncDataProvider(final String all) {
        provider = new AsyncDataProvider<ExpenseDto>() {
            @Override
            protected void onRangeChanged(HasData<ExpenseDto> display) {
                final int start = display.getVisibleRange().getStart();
                final int length = display.getVisibleRange().getLength();

                if (all.equals("All")) {

                    expenseService.getRowsNumber(new MethodCallback<Integer>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            throw new RuntimeException("Call to server failed: " + exception.getMessage());
                        }

                        @Override
                        public void onSuccess(Method method, Integer response) {
                            updateRowCount(response, true);
                        }
                    });

                    expenseService.getByRange(start, length, new MethodCallback<List<ExpenseDto>>() {
                        @Override
                        public void onFailure(Method method, Throwable exception) {
                            throw new RuntimeException("Call to server failed: " + exception.getMessage());
                        }

                        @Override
                        public void onSuccess(Method method, List<ExpenseDto> response) {
                            updateRowData(start, response);
                        }
                    });
                }
            }
        };

        provider.addDataDisplay(table);
    }

    private void addSelectionModel() {
        final NoSelectionModel<ExpenseDto> selectionModel = new NoSelectionModel<ExpenseDto>();
        table.setSelectionModel(selectionModel);
    }

    private void addColumns() {
        categoryService.getAll(new MethodCallback<List<CategoryDto>>() {
            @Override
            public void onFailure(Method method, Throwable exception) {
                throw new RuntimeException("Call to server failed: " + exception.getMessage());
            }

            @Override
            public void onSuccess(Method method, List<CategoryDto> categories) {
                addCheckboxColumn();
                addIdColumn();
                addTitleColumn();
                addCategoryColumn(categories);
                addAmountColumn();
                addDateColumn();
            }
        });
    }

    private void addCheckboxColumn() {
        Column<ExpenseDto, Boolean> checkboxColumn = new Column<ExpenseDto, Boolean>(new CheckboxCell()) {
            @Override
            public Boolean getValue(ExpenseDto object) {
                return new Boolean(false);
            }
        };
        checkboxColumn.setFieldUpdater(new FieldUpdater<ExpenseDto, Boolean>() {
            @Override
            public void update(int index, ExpenseDto object, Boolean value) {
                if (value.equals(Boolean.TRUE)) {
                    deletedExpenses.put(object.getId(), object);
                }
            }
        });
        table.addColumn(checkboxColumn, "Del");
        table.setColumnWidth(checkboxColumn, 4, Style.Unit.PCT);
    }

    private void addDateColumn() {
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
        Column<ExpenseDto, Date> dateColumn = new Column<ExpenseDto, Date>(new DatePickerCell(dateFormat)) {
            @Override
            public Date getValue(ExpenseDto object) {
                return DateTimeFormat.getFormat("yyyy-MM-dd").parse(object.getShortDate());
            }
        };
        dateColumn.setFieldUpdater(new FieldUpdater<ExpenseDto, Date>() {
            @Override
            public void update(int index, ExpenseDto object, Date value) {
                object.setShortDate(DateTimeFormat.getFormat("yyyy-MM-dd").format(value));
                editedExpenses.put(object.getId(), object);
            }
        });
        table.addColumn(dateColumn, "Date");
        table.setColumnWidth(dateColumn, 20, Style.Unit.PCT);
    }

    private void addAmountColumn() {
        final NumberFormat numberFormat = NumberFormat.getDecimalFormat();
        Column<ExpenseDto, String> amountColumn = new Column<ExpenseDto, String>(new EditTextCell()) {
            @Override
            public String getValue(ExpenseDto object) {
                return numberFormat.format(object.getAmount());
            }
        };
        amountColumn.setFieldUpdater(new FieldUpdater<ExpenseDto, String>() {
            @Override
            public void update(int index, ExpenseDto object, String value) {
                object.setAmount(new BigDecimal(value));
                editedExpenses.put(object.getId(), object);
            }
        });
        table.addColumn(amountColumn, "Amount");
        table.setColumnWidth(amountColumn, 10, Style.Unit.PCT);
    }

    private void addCategoryColumn(List<CategoryDto> categories) {
        for (CategoryDto cat : categories) {
            categoryValues.put(cat.getName(), cat);
        }

        SelectionCell categoryCell = new SelectionCell(new ArrayList<String>(categoryValues.keySet()));
        Column<ExpenseDto, String> categoryColumn = new Column<ExpenseDto, String>(categoryCell) {
            @Override
            public String getValue(ExpenseDto object) {
                return object.getCategory().getName();
            }
        };
        categoryColumn.setFieldUpdater(new FieldUpdater<ExpenseDto, String>() {
            @Override
            public void update(int index, ExpenseDto object, String value) {
                object.setCategory(categoryValues.get(value));
                editedExpenses.put(object.getId(), object);
            }
        });
        table.addColumn(categoryColumn, "Category");
        table.setColumnWidth(categoryColumn, 14, Style.Unit.PCT);
    }

    private void addTitleColumn() {
        Column<ExpenseDto, String> titleColumn = new Column<ExpenseDto, String>(new EditTextCell()) {
            @Override
            public String getValue(ExpenseDto object) {
                return object.getTitle();
            }
        };
        titleColumn.setFieldUpdater(new FieldUpdater<ExpenseDto, String>() {
            @Override
            public void update(int index, ExpenseDto object, String value) {
                object.setTitle(value);
                editedExpenses.put(object.getId(), object);
            }
        });
        table.addColumn(titleColumn, "Title");
        table.setColumnWidth(titleColumn, 20, Style.Unit.PCT);
    }

    private void addIdColumn() {
        TextColumn<ExpenseDto> idColumn = new TextColumn<ExpenseDto>() {
            @Override
            public String getValue(ExpenseDto object) {
                return String.valueOf(object.getId());
            }
        };
        table.addColumn(idColumn, "Id");
        table.setColumnWidth(idColumn, 6, Style.Unit.PCT);
    }

    private void initTable() {
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);
    }

    interface CellTableWidgetUiBinder extends UiBinder<Widget, CellTableWidget> {}
    private static CellTableWidgetUiBinder uiBinder = GWT.create(CellTableWidgetUiBinder.class);

}