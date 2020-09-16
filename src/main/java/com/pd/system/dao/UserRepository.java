package com.pd.system.dao;

import com.pd.system.domain.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bootdo-jpa huyidao---123@163.com
 * @email huyidao---123@163.com
 * @date 2019-04-01 10:19:23
 */
public interface UserRepository extends JpaRepository<UserDO, Long>, JpaSpecificationExecutor<UserDO> {

    @Query(value = "select DISTINCT dept_id from sys_user", nativeQuery = true)
    Long[] listAllDept();

    int deleteByUserIdIn(Long[] ids);
}
