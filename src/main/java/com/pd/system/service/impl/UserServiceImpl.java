package com.pd.system.service.impl;

import com.pd.common.domin.Tree;
import com.pd.system.dao.DeptRepository;
import com.pd.system.dao.UserRepository;
import com.pd.system.dao.UserRoleRepository;
import com.pd.system.domain.DeptDO;
import com.pd.system.domain.UserDO;
import com.pd.system.domain.UserVO;
import com.pd.system.service.UserService;
import com.pd.system.service.base.AbsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserServiceImpl extends AbsCommonService<UserDO> implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DeptRepository deptRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserDO findById(Long id) {
        List<Long> roleIds = userRoleRepository.listRoleIdByUserId(id);
        UserDO user = userRepository.getOne(id);
        user.setDeptName(deptRepository.getOne(user.getDeptId()).getName());
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public List<UserDO> listByDeptId(String deptId) {
        return null;
    }

    @Override
    public UserDO resetPwd(UserVO userVO, UserDO userDO) throws Exception {
        return null;
    }

    @Override
    public Tree<DeptDO> getTree() {
        return null;
    }

    @Override
    public UserDO updatePersonal(UserDO userDO) {
        return null;
    }

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        return null;
    }

    @Override
    public JpaRepository<UserDO, Long> getDao() {
        return userRepository;
    }

    @Override
    public JpaSpecificationExecutor<UserDO> getDao2() {
        return userRepository;
    }

    @Override
    public void deleteByIds(Long... ids) {

    }
}
