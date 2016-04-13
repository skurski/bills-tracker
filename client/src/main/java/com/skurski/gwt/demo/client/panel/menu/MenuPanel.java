package com.skurski.gwt.demo.client.panel.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.skurski.gwt.demo.client.common.Messages;
import com.skurski.gwt.demo.client.library.CompositeComponent;
import com.skurski.gwt.demo.shared.dto.CategoriesDto;
import com.skurski.gwt.demo.shared.dto.CategoryDto;

import java.util.logging.Logger;


/**
 * Created by PSkurski on 3/25/2016.
 */
public class MenuPanel extends CompositeComponent {
    @UiField
    DecoratedStackPanel stackPanel;

    private static final Logger logger = Logger.getLogger("MenuPanel");

    private final VerticalPanel expensePanel = new VerticalPanel();
    private final HorizontalPanel headerPanel = new HorizontalPanel();

    public MenuPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void onLoad() {
        addBookmarksToStackPanel();
    }

    private void addBookmarksToStackPanel() {
        // add more stack panels
    }

    public void createExpensePanel(CategoriesDto categoriesDto) {
        expensePanel.setSpacing(4);

        for (CategoryDto item : categoriesDto.getCategories()) {
            createLink(item.getName());
        }
        createLink("All");

        stackPanel.add(expensePanel, getAndSetHeaderText(Messages.Default.getInstance().getExpenseHeader()), true);
    }

    private String getAndSetHeaderText(String text) {
        headerPanel.setSpacing(0);
        headerPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        HTML headerText = new HTML(text);
        headerText.setStyleName("stack-panel-header");
        headerPanel.add(headerText);

        return headerPanel.getElement().getString();
    }

    private void createLink(String text) {
        final Anchor allLink = new Anchor(text);
        expensePanel.add(allLink);
        getActionWidgets().put(text, allLink);
    }


    interface MenuPanelUiBinder extends UiBinder<Widget, MenuPanel> {}
    private static MenuPanelUiBinder uiBinder = GWT.create(MenuPanelUiBinder.class);
}