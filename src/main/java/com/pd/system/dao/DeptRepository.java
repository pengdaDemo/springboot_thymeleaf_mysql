package com.pd.system.dao;

import com.pd.system.domain.DeptDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeptRepository extends JpaRepository<DeptDO, Long>, JpaSpecificationExecutor<DeptDO> {
    int countByDeptId(Long deptId);

    int deleteByDeptIdIn(Long[] ids);
}
