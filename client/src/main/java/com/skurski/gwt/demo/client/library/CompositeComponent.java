package com.skurski.gwt.demo.client.library;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by pskurski on 3/31/2016.
 */
public class CompositeComponent extends Composite implements Component {

    private Map<String, Widget> actionWidgets = new HashMap<String, Widget>();
    private List<HandlerRegistration> handlerRegistrations = new LinkedList<HandlerRegistration>();

    @Override
    protected void onAttach() {
        if (!isAttached()) {
            initHandlers();
        }
        if (getWidget() != null) {
            super.onAttach();
        }
    }

    @Override
    protected void onDetach() {
        if (super.isAttached()) {
            super.onDetach();
        }
        removeHandlers();
    }

    public void initHandlers() {

    }

    public void removeHandlers() {
        for (HandlerRegistration registration : handlerRegistrations) {
            registration.removeHandler();
        }
        handlerRegistrations.clear();
    }

    public Map<String, Widget> getActionWidgets() {
        return actionWidgets;
    }

    public List<HandlerRegistration> getHandlerRegistrations() {
        return handlerRegistrations;
    }
}
