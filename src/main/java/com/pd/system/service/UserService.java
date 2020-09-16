package com.pd.system.service;

import com.pd.common.domin.Tree;
import com.pd.system.domain.DeptDO;
import com.pd.system.domain.UserDO;
import com.pd.system.domain.UserVO;
import com.pd.system.service.base.ICommonService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface UserService extends ICommonService<UserDO> {
    List<UserDO> listByDeptId(String deptId);

    UserDO resetPwd(UserVO userVO, UserDO userDO) throws Exception;

    Tree<DeptDO> getTree();

    /**
     * 更新个人信息
     * @param userDO
     * @return
     */
    UserDO updatePersonal(UserDO userDO);

    /**
     * 更新个人图片
     * @param file 图片
     * @param avatar_data 裁剪信息
     * @param userId 用户ID
     * @throws Exception
     */
    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;

}
