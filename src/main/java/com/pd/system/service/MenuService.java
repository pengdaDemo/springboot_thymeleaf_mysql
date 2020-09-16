package com.pd.system.service;

import com.pd.common.domin.Tree;
import com.pd.system.domain.MenuDO;
import com.pd.system.service.base.ICommonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface MenuService extends ICommonService<MenuDO> {

    Tree<MenuDO> getSysMenuTree(Long id);

    List<Tree<MenuDO>> listMenuTree(Long id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(Long id);

    Set<String> listPerms(Long userId);

}
