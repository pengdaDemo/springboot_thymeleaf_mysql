package com.pd.system.shiro;

import com.github.wenhao.jpa.Specifications;
import com.pd.common.config.ApplicationContextRegister;
import com.pd.common.utils.ShiroUtils;
import com.pd.system.dao.UserRepository;
import com.pd.system.domain.UserDO;
import com.pd.system.service.MenuService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

/**
 * Realm用于身份识别，
 * 身份验证（getAuthenticationInfo 方法）验证账户和密码，并返回相关信息
 *
 * 权限获取（getAuthorizationInfo 方法） 获取指定身份的权限，并返回相关信息
 *
 * 令牌支持（supports方法）判断该令牌（Token）是否被支持
 */
public class UserRealm extends AuthorizingRealm {
/*	@Autowired
	UserDao userMapper;
	@Autowired
	MenuService menuService;*/

    /**
     * 登录的用户权限录入
     * @param arg0
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        Long userId = ShiroUtils.getUserId();
        MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
        Set<String> perms = menuService.listPerms(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perms);
        return info;
    }

    /**
     * 账号验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        /*
         * Map<String, Object> map = new HashMap<>(16); map.put("username", username);
         */
        Specification<UserDO> spec = Specifications.<UserDO>and().eq("username", username).build();
        String password = new String((char[]) token.getCredentials());

        UserRepository userRepository = ApplicationContextRegister.getBean(UserRepository.class);
        // 查询用户信息
        UserDO user = userRepository.findAll(spec).get(0);

        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        // 密码错误
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

}
