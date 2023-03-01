package com.company.jmixpm.screen.post;

import com.company.jmixpm.app.PostService;
import com.company.jmixpm.screen.userinfo.UserInfoEdit;
import io.jmix.core.LoadContext;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Route("posts")
@UiController("Post.browse")
@UiDescriptor("post-browse.xml")
public class PostBrowse extends Screen {
    @Autowired
    private PostService postService;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private GroupTable<Post> postsTable;

    @Install(to = "postsDl", target = Target.DATA_LOADER)
    private List<Post> postsDlLoadDelegate(LoadContext<Post> loadContext) {
        // Here you can load entities from an external store
        return postService.fetchPosts(
                loadContext.getQuery().getFirstResult(),
                loadContext.getQuery().getMaxResults());
    }

    @Subscribe("postsTable.viewUserInfo")
    public void onPostsTableViewUserInfo(Action.ActionPerformedEvent event) {
        Post selected = postsTable.getSingleSelected();
        if (selected == null || selected.getUserId() == null) {
            return;
        }

        UserInfoEdit userInfoScreen = screenBuilders.screen(this)
                .withScreenClass(UserInfoEdit.class)
                .build();

        userInfoScreen.setUserId(selected.getUserId());

        userInfoScreen.show();
    }

    @Install(to = "pagination", subject = "totalCountDelegate")
    private Integer paginationTotalCountDelegate() {
        return postService.getTotalCount();
    }
}