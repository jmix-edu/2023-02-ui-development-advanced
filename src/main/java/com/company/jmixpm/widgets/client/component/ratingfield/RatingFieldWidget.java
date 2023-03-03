package com.company.jmixpm.widgets.client.component.ratingfield;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;

import java.util.ArrayList;
import java.util.List;

public class RatingFieldWidget extends FocusWidget {

    // API for handle clicks
    public interface StarClickListener {
        void starClicked(int value);
    }

    protected int value = 0;

    private static final String CLASSNAME = "ratingfield";
    protected List<SpanElement> stars = new ArrayList<>(5);

    protected StarClickListener listener;

    protected RatingFieldWidget() {
        DivElement container = DOM.createDiv().cast();
        container.getStyle().setDisplay(Style.Display.INLINE_BLOCK);

        for (int i = 0; i < 5; i++) {
            SpanElement star = DOM.createSpan().cast();

            DOM.insertChild(container, star, i);

            DOM.sinkEvents(star, Event.ONCLICK);

            stars.add(star);
        }

        setElement(container);

        setStylePrimaryName(CLASSNAME);
    }

    public void setValue(int value) {
        this.value = value;
        updateStarsStyle(value);
    }

    @Override
    public void onBrowserEvent(Event event) {
        super.onBrowserEvent(event);

        switch (event.getTypeInt()) {
            case Event.ONCLICK:
                SpanElement element = event.getEventTarget().cast();

                int index = stars.indexOf(element);
                if (index >= 0) {
                    int value = index + 1;

                    setValue(value);

                    if (listener != null) {
                        listener.starClicked(value);
                    }
                }
                break;
        }
    }

    @Override
    public void setStylePrimaryName(String style) {
        super.setStylePrimaryName(style);

        for (SpanElement star : stars) {
            star.setClassName(style + "-star");
        }

        updateStarsStyle(this.value);
    }

    private void updateStarsStyle(int value) {
        for (SpanElement star : stars) {
            star.removeClassName(getStylePrimaryName() + "-star-selected");
        }

        for (int i = 0; i < value; i++) {
            stars.get(i).addClassName(getStylePrimaryName() + "-star-selected");
        }
    }
}
