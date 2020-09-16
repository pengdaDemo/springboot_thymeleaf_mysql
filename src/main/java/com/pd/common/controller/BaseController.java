package com.pd.common.controller;

import com.pd.common.utils.ShiroUtils;
import com.pd.system.domain.UserDO;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public UserDO getUser() {
        return ShiroUtils.getUser();
    }

    public Long getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

}
