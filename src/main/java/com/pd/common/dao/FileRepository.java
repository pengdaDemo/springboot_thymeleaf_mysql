package com.pd.common.dao;

import com.pd.common.domin.FileDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 文件上传
 */
public interface FileRepository extends JpaRepository<FileDO, Long>, JpaSpecificationExecutor<FileDO> {

    int deleteByIdIn(Long[] ids);
}
