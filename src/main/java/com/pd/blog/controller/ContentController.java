package com.pd.blog.controller;

import com.pd.blog.domain.ContentDO;
import com.pd.blog.service.ContentService;
import com.pd.common.utils.PageUtils;
import com.pd.common.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/blog/bContent")
public class ContentController {

    @Autowired
    ContentService bContentService;

    @GetMapping()
    @RequiresPermissions("blog:bContent:bContent")
    public String bContent() {
        return "blog/bContent/bContent";
    }

    @GetMapping("/list")
    @ResponseBody
    @RequiresPermissions("blog:bContent:bContent")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = null;
        try {
            Query query = new Query(params);
            pageUtils = bContentService.findAllByPage(query, ContentDO.class, "cid");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageUtils;
    }
}
