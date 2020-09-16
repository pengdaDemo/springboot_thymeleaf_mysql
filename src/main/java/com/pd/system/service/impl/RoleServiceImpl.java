package com.pd.system.service.impl;

import com.pd.system.dao.RoleRepository;
import com.pd.system.dao.UserRoleRepository;
import com.pd.system.domain.RoleDO;
import com.pd.system.service.RoleService;
import com.pd.system.service.base.AbsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleServiceImpl extends AbsCommonService<RoleDO> implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public List<RoleDO> list(Long userId) {
        List<Long> rolesIds = userRoleRepository.listRoleIdByUserId(userId);
        List<RoleDO> roles = roleRepository.findAll();
        for(RoleDO roleDO : roles) {
            roleDO.setRoleSign("false");
            for (Long roleId : rolesIds) {
                if(Objects.equals(roleDO.getRoleId(), roleId)) {
                    roleDO.setRoleSign("true");
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public void deleteByIds(Long... ids) {

    }

    @Override
    public JpaRepository<RoleDO, Long> getDao() {
        return roleRepository;
    }

    @Override
    public JpaSpecificationExecutor<RoleDO> getDao2() {
        return roleRepository;
    }

}
