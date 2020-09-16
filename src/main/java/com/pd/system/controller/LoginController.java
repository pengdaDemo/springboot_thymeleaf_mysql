package com.pd.system.controller;

import com.pd.common.config.BootdoJpaConfig;
import com.pd.common.controller.BaseController;
import com.pd.common.domin.FileDO;
import com.pd.common.domin.Tree;
import com.pd.common.service.FileService;
import com.pd.common.utils.MD5Utils;
import com.pd.common.utils.RandomValidateCodeUtil;
import com.pd.system.domain.MenuDO;
import com.pd.system.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BootdoJpaConfig bootdoConfig;

    @Autowired
    MenuService menuService;

    @Autowired
    FileService fileService;

    @GetMapping({"/", ""})
    String welcome(Model model) {
        return "redirect:/blog";
    }

    @GetMapping("/index")
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        System.out.println("测试"+getUserId().toString());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.findById(getUser().getPicId());
        if(fileDO != null && fileDO.getUrl() != null) {
            if(fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("username", bootdoConfig.getUsername());
        model.addAttribute("password", bootdoConfig.getPassword());
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    HashMap<String,Object> ajaxLogin(String username, String password, String verify, HttpServletRequest request) {
        HashMap<String ,Object>hashMap=new HashMap<>();
        try {
            //从session中获取随机数
            String random = (String) request.getSession().getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (StringUtils.isBlank(verify)) {
                hashMap.put("msg","请输入验证码");
                hashMap.put("code",1);
                return hashMap;
            }
            if (random.equals(verify)) {
            } else {
                hashMap.put("msg","请输入正确的验证码");
                hashMap.put("code",1);
                return hashMap;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            hashMap.put("msg","操作正确");
            hashMap.put("code",0);
            return hashMap;
        } catch (AuthenticationException e) {
            hashMap.put("msg","用户或密码错误");
            hashMap.put("code",1);
            e.printStackTrace();
            return hashMap;
        }
    }

    @GetMapping("/main")
    String main() {
        return "main";
    }


    /**
     * 获取验证码
     */
    @GetMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
