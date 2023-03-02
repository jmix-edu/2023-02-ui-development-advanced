package com.company.jmixpm.screen.user;

import com.company.jmixpm.entity.User;
import com.vaadin.server.VaadinSession;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.app.inputdialog.DialogActions;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.NotificationFacet;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@MultipleOpen
@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
@Route("users")
public class UserBrowse extends StandardLookup<User> {
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private GroupTable<User> usersTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private BackgroundWorker backgroundWorker;
    @Autowired
    private NotificationFacet emailTaskDoneNotification;

    /*@Subscribe("usersTable.sendEmail")
    public void onUsersTableSendEmail(Action.ActionPerformedEvent event) {
        InputDialog inputDialog = dialogs.createInputDialog(this)
                .withCaption("Enter email attributes")
                .withParameters(
                        InputParameter.stringParameter("title")
                                .withField(() -> {
                                    TextField<String> textField = uiComponents.create(TextField.NAME);
                                    textField.setCaption("Title");
                                    textField.setRequired(true);
                                    textField.setWidthFull();
                                    return textField;
                                }),
                        InputParameter.stringParameter("body")
                                .withField(() -> {
                                    TextArea<String> textArea = uiComponents.create(TextArea.class);
                                    textArea.setCaption("Body");
                                    textArea.setRequired(true);
                                    textArea.setWidthFull();
                                    return textArea;
                                }))
                .withActions(DialogActions.OK_CANCEL)
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        String title = closeEvent.getValue("title");
                        String body = closeEvent.getValue("body");

                        Set<User> selected = usersTable.getSelected();

                        doSendEmail(title, body, selected);
                    }
                })
                .build();
        inputDialog.show();
    }*/

    @Subscribe("emailDialogFacet")
    public void onEmailDialogFacetInputDialogClose(InputDialog.InputDialogCloseEvent event) {
        if (event.closedWith(DialogOutcome.OK)) {
            String title = event.getValue("title");
            String body = event.getValue("body");

            Set<User> selected = usersTable.getSelected();

            doSendEmail(title, body, selected);
        }
    }

    private void doSendEmail(String title, String body, Collection<User> users) {
//        BackgroundTaskHandler<Void> taskHandler = backgroundWorker.handle(new EmailTask(title, body, users));
//        taskHandler.execute();

        dialogs.createBackgroundWorkDialog(this, new EmailTask(title, body, users))
                .withTotal(users.size())
                .show();
    }

    private class EmailTask extends BackgroundTask<Integer, Void> {
        private String title;
        private String body;
        private Collection<User> users;

        public EmailTask(String title, String body, Collection<User> users) {
            super(10, TimeUnit.MINUTES, UserBrowse.this);
            this.title = title;
            this.body = body;
            this.users = users;
        }

        @Override
        public Void run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
            int i = 0;
            for (User user : users) {
                if (taskLifeCycle.isInterrupted() || taskLifeCycle.isCancelled()) {
                    break;
                }

                TimeUnit.SECONDS.sleep(2);

                i++;

                taskLifeCycle.publish(i);
            }
            return null;
        }

        @Override
        public void done(Void result) {
            super.done(result);
            emailTaskDoneNotification.show();
        }
    }

}