package com.pd.system.service;

import com.pd.system.domain.RoleDO;
import com.pd.system.service.base.ICommonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService extends ICommonService<RoleDO> {
    List<RoleDO> list(Long userId);
}
