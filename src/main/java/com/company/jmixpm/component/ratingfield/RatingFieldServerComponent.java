package com.company.jmixpm.component.ratingfield;

import com.company.jmixpm.widgets.client.component.ratingfield.RatingFieldServerRpc;
import com.company.jmixpm.widgets.client.component.ratingfield.RatingFieldState;
import com.vaadin.ui.AbstractField;

public class RatingFieldServerComponent extends AbstractField<Integer> {

    public RatingFieldServerComponent() {
        registerRpc((RatingFieldServerRpc) value -> setValue(value, true));
    }

    @Override
    protected void doSetValue(Integer value) {
        if (value == null) {
            value = 0;
        }
        getState().value = value;
    }

    @Override
    public Integer getValue() {
        return getState(false).value;
    }

    @Override
    protected RatingFieldState getState() {
        return (RatingFieldState) super.getState();
    }

    @Override
    protected RatingFieldState getState(boolean markAsDirty) {
        return (RatingFieldState) super.getState(markAsDirty);
    }
}
