package com.pd.common.service;

import com.pd.common.domin.FileDO;
import com.pd.system.service.base.ICommonService;

public interface FileService extends ICommonService<FileDO> {
    /**
     * 判断一个文件是否存在
     * @param url FileDO中存的路径
     * @return
     */
    Boolean isExist(String url);
}
