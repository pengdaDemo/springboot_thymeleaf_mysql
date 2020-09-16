package com.pd.system.dao;

import com.pd.system.domain.RoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 角色
 */
public interface RoleRepository extends JpaRepository<RoleDO, Long>, JpaSpecificationExecutor<RoleDO> {
    int deleteByRoleIdIn(Long[] ids);
}
