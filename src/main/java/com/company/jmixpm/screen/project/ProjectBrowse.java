package com.company.jmixpm.screen.project;

import com.company.jmixpm.component.quill.QuillEditor;
import com.company.jmixpm.component.ratingfield.RatingFieldServerComponent;
import com.company.jmixpm.entity.Project;
import io.jmix.ui.component.VBoxLayout;
import io.jmix.ui.screen.*;
import io.jmix.ui.widget.JmixVerticalActionsLayout;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
    @Autowired
    private VBoxLayout container;

    @Subscribe
    public void onInit(InitEvent event) {
        RatingFieldServerComponent ratingFieldServerComponent = new RatingFieldServerComponent();
        container.unwrap(JmixVerticalActionsLayout.class).addComponent(ratingFieldServerComponent);

        QuillEditor quillEditor = new QuillEditor();
        container.unwrap(JmixVerticalActionsLayout.class).addComponent(quillEditor);

    }
}