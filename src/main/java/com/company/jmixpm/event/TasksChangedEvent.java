package com.company.jmixpm.event;

import org.springframework.context.ApplicationEvent;

public class TasksChangedEvent extends ApplicationEvent {

    public TasksChangedEvent(Object source) {
        super(source);
    }
}
