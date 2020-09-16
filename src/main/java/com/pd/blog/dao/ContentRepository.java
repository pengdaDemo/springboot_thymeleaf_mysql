package com.pd.blog.dao;

import com.pd.blog.domain.ContentDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentRepository extends JpaRepository<ContentDO, Long>, JpaSpecificationExecutor<ContentDO> {
    int deleteByCidIn(Long[] ids);
}
