package com.pd.blog.service.impl;

import com.pd.blog.dao.ContentRepository;
import com.pd.blog.domain.ContentDO;
import com.pd.system.service.base.AbsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import com.pd.blog.service.ContentService;

import javax.transaction.Transactional;

@Service
public class ContentServiceImpl extends AbsCommonService<ContentDO> implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Override
    @Transactional
    public void deleteByIds(Long... ids) {
        contentRepository.deleteByCidIn(ids);
    }

    @Override
    public JpaRepository<ContentDO, Long> getDao() {
        return contentRepository;
    }

    @Override
    public JpaSpecificationExecutor<ContentDO> getDao2() {
        return contentRepository;
    }


}
