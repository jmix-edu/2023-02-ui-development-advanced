package com.company.jmixpm.screen.project;

import com.company.jmixpm.entity.Project;
import com.company.jmixpm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {

    private GroupTable<Task> tasksTable;

    @Autowired
    private DataManager dataManager;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private Notifications notifications;

//    @Subscribe
//    public void onInitEntity(InitEntityEvent<Project> event) {
//        tasksTable.setEnabled(false);
//    }

//    @Install(to = "tasksTable.create", subject = "initializer")
//    private void tasksTableCreateInitializer(Task task) {
//        task.setProject(getEditedEntity());
//    }

//    @Subscribe(target = Target.DATA_CONTEXT)
//    public void onPostCommit(DataContext.PostCommitEvent event) {
//        tasksTable.setEnabled(true);
//    }

    public void onTasksTableCreate(Action.ActionPerformedEvent event) {
        Task task = dataManager.create(Task.class);
        task.setProject(getEditedEntity());

        screenBuilders.editor(tasksTable)
                .newEntity(task)
                .withParentDataContext(getScreenData().getDataContext())
                .show();
    }

    public void onTasksTableEdit(Action.ActionPerformedEvent event) {
        Task selected = tasksTable.getSingleSelected();
        if (selected == null) {
            return;
        }

        screenBuilders.editor(tasksTable)
                .editEntity(selected)
                .withParentDataContext(getScreenData().getDataContext())
                .show();
    }

    @Subscribe("tabSheet")
    public void onTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        if ("tasksTab".equals(event.getSelectedTab().getName())) {
            initTasks();
        }
    }

    private void initTasks() {
        if (tasksTable != null) {
            return;
        }

        tasksDl.setParameter("project", getEditedEntity());
        tasksDl.load();

        tasksTable = (GroupTable<Task>) getWindow().getComponentNN("tasksTable");
        ((BaseAction) tasksTable.getActionNN("create")).addActionPerformedListener(this::onTasksTableCreate);
        ((BaseAction) tasksTable.getActionNN("edit")).addActionPerformedListener(this::onTasksTableEdit);
    }

    @Subscribe(id = "tasksDc", target = Target.DATA_CONTAINER)
    public void onTasksDcCollectionChange(CollectionContainer.CollectionChangeEvent<Task> event) {
        notifications.create()
                .withCaption("[tasksDc] CollectionChangeEvent")
                .show();
    }


}