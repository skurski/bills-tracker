package com.skurski.gwt.demo.client.panel.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.skurski.gwt.demo.client.common.Messages;
import com.skurski.gwt.demo.client.library.CompositeComponent;
import com.skurski.gwt.demo.client.panel.menu.MenuPanel;
import com.skurski.gwt.demo.client.widget.table.CellTableWidget;
import com.skurski.gwt.demo.shared.dto.CategoriesDto;
import com.skurski.gwt.demo.shared.dto.CategoryDto;

import java.util.logging.Logger;


/**
 * Created by PSkurski on 3/25/2016.
 */
public class LandingPage extends CompositeComponent {
    @UiField
    HTMLPanel northPanel;
    @UiField
    HTMLPanel southPanel;
    @UiField
    HTMLPanel westPanel;
    @UiField
    HTMLPanel centerPanel;

    private static final Logger logger = Logger.getLogger("LandingPage");

    private final CompositeComponent menuPanel = new MenuPanel();
    private final VerticalPanel tableContainerPanel = new VerticalPanel();

    private CategoriesDto categoriesDto = new CategoriesDto();

    public LandingPage(CategoriesDto categoriesDto) {
        initWidget(uiBinder.createAndBindUi(this));

        this.categoriesDto = categoriesDto;
        addLabelsForPanels();
        addMenu();
        addTable();
    }

    public void initHandlers() {
        for (final CategoryDto cat : categoriesDto.getCategories()) {
            getHandlerRegistrations().add(
                    menuPanel.getActionWidgets().get(cat.getName()).addDomHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            centerPanel.clear();
                            addCellTableToCenterPanel(cat);
                        }
                    }, ClickEvent.getType())
            );
        }
        getHandlerRegistrations().add(
                menuPanel.getActionWidgets().get("All").addDomHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        centerPanel.clear();
                        addCellTableToCenterPanel("All");
                    }
                }, ClickEvent.getType())
        );
    }

    private void addCellTableToCenterPanel(String all) {
        CellTableWidget cellTableWidget = new CellTableWidget();
        cellTableWidget.setModel(all);
        centerPanel.add(cellTableWidget);
    }

    private void addCellTableToCenterPanel(CategoryDto categoryDto) {
        CellTableWidget cellTableWidget = new CellTableWidget();
        cellTableWidget.setModel(categoryDto);
        centerPanel.add(cellTableWidget);
    }

    private void addTable() {
        centerPanel.add(tableContainerPanel);
    }

    private void addMenu() {
        ((MenuPanel) menuPanel).createExpensePanel(categoriesDto);
        westPanel.add(menuPanel);
    }

    private void addLabelsForPanels() {
        northPanel.add(new Label(Messages.Default.getInstance().getNorthPanelHeader()));
        southPanel.add(new Label(Messages.Default.getInstance().getSouthPanelFooter()));
    }

    interface LandingPageUiBinder extends UiBinder<Widget, LandingPage> {}
    private static LandingPageUiBinder uiBinder = GWT.create(LandingPageUiBinder.class);
}
