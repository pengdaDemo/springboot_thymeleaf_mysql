package com.pd.system.controller;

import com.pd.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/sysDept")
public class DeptController extends BaseController {
    private String prefix = "system/dept";

    @GetMapping("/treeView")
    String treeView() {
        return  prefix + "/deptTree";
    }

}
