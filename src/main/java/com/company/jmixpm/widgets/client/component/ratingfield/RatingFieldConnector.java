package com.company.jmixpm.widgets.client.component.ratingfield;


import com.company.jmixpm.component.ratingfield.RatingFieldServerComponent;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractFieldConnector;
import com.vaadin.shared.ui.Connect;

@Connect(RatingFieldServerComponent.class)
public class RatingFieldConnector extends AbstractFieldConnector {

    @Override
    public RatingFieldWidget getWidget() {
        RatingFieldWidget widget = (RatingFieldWidget) super.getWidget();

        if (widget.listener == null) {
            widget.listener = new RatingFieldWidget.StarClickListener() {
                @Override
                public void starClicked(int value) {
                    getRpcProxy(RatingFieldServerRpc.class).starClicked(value);
                }
            };
        }

        return widget;
    }

    @Override
    public RatingFieldState getState() {
        return (RatingFieldState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);

        if (stateChangeEvent.hasPropertyChanged("value")) {
            getWidget().setValue(getState().value);
        }
    }
}
