package com.company.jmixpm.screen.userinfo;

import com.company.jmixpm.app.PostService;
import com.google.common.collect.ImmutableMap;
import io.jmix.core.LoadContext;
import io.jmix.core.common.util.Preconditions;
import io.jmix.ui.action.Action;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.*;
import com.company.jmixpm.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

@Route("user-info")
@UiController("UserInfo.edit")
@UiDescriptor("user-info-edit.xml")
public class UserInfoEdit extends Screen {
    @Autowired
    private PostService postService;

    private Long userId;
    @Autowired
    private UrlRouting urlRouting;

    public void setUserId(Long userId) {
        Preconditions.checkNotNullArgument(userId);
        this.userId = userId;
    }

    @Install(to = "userInfoDl", target = Target.DATA_LOADER)
    private UserInfo userInfoDlLoadDelegate(LoadContext<UserInfo> loadContext) {
        // Here you can load entity from an external store by ID passed in LoadContext
        return postService.fetchUserInfo(userId);
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        String serializeId = UrlIdSerializer.serializeId(userId);
        urlRouting.replaceState(this, ImmutableMap.of("id", serializeId));
    }

    @Subscribe
    public void onUrlParamsChanged(UrlParamsChangedEvent event) {
        String serializedId = event.getParams().get("id");
        userId = (Long) UrlIdSerializer.deserializeId(Long.class, serializedId);
    }



}