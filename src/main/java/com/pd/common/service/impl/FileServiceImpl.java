package com.pd.common.service.impl;

import com.pd.common.config.BootdoJpaConfig;
import com.pd.common.dao.FileRepository;
import com.pd.common.domin.FileDO;
import com.pd.common.service.FileService;
import com.pd.system.service.base.AbsCommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl extends AbsCommonService<FileDO> implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private BootdoJpaConfig bootdoJpaConfig;

    @Override
    public Boolean isExist(String url) {
        Boolean isExist = false;
        if(!StringUtils.isEmpty(url)) {
            String filePath = url.replace("/files/", "");
            filePath = bootdoJpaConfig.getUploadPath() + filePath;
            File file = new File(filePath);
            if(file.exists()) {
                isExist = true;
            }
        }
        return isExist;
    }

    @Override
    public void deleteByIds(Long... ids) {
        fileRepository.deleteByIdIn(ids);
    }
    @Override
    public JpaRepository<FileDO, Long> getDao() {
        return fileRepository;
    }

    @Override
    public JpaSpecificationExecutor<FileDO> getDao2() {
        return fileRepository;
    }

}
