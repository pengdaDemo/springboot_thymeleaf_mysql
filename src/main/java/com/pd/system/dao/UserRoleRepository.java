package com.pd.system.dao;

import com.pd.system.domain.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleDO, Long>, JpaSpecificationExecutor<UserRoleDO> {
    @Query(value = "select role_id from sys_user_role where user_id = ?1", nativeQuery = true)
    List<Long> listRoleIdByUserId(Long userId);

    int deleteByUserId(Long userId);

    int deleteByRoleId(Long roleId);

    int deleteByUserIdIn(Long[] ids);

    int deleteByIdIn(Long[] ids);
}
