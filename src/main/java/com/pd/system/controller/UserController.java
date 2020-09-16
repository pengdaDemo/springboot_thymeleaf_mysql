package com.pd.system.controller;

import com.pd.common.controller.BaseController;
import com.pd.common.utils.PageUtils;
import com.pd.common.utils.Query;
import com.pd.system.domain.RoleDO;
import com.pd.system.domain.UserDO;
import com.pd.system.service.RoleService;
import com.pd.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {
    private String prefix = "system/user";
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageUtils list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = null;
        try {
            Query query = new Query(params);
            pageUtils = userService.findAllByPage(query, UserDO.class, "userId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtils;
    }

    @RequiresPermissions("sys:user:edit")
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        UserDO userDO = userService.findById(id);
        System.out.println("1111111111111111");
        System.out.println(userDO.getDeptName());
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

}
