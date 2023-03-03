package com.company.jmixpm.widgets.client.component.ratingfield;

import com.vaadin.shared.communication.ServerRpc;

public interface RatingFieldServerRpc extends ServerRpc {

    void starClicked(int value);
}
