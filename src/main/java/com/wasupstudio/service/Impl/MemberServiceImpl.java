package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.constant.UserRoleConstants;
import com.wasupstudio.exception.BussinessException;
import com.wasupstudio.exception.ResultCode;
import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MemberServiceImpl extends AbstractService<MemberEntity> implements MemberService {
    @Autowired
    public MemberMapper memberMapper;

    @Override
    public String save(MemberDTO memberDTO) {
        if (getAdminByEmail(memberDTO.getEmail()) == null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setEmail(memberDTO.getEmail());
            memberEntity.setName(memberDTO.getName());
            memberEntity.setPwd(AesHelper.encrypt(memberDTO.getPwd()));
            memberEntity.setPhone(memberDTO.getPhone());
            memberEntity.setBirthday(memberDTO.getBirthday());
            memberEntity.setOrganization(memberDTO.getOrganization());
            memberEntity.setGrade(memberDTO.getGrade());
            memberEntity.setRegistionTime(memberDTO.getRegistionTime());
            memberEntity.setStatus(ProjectConstant.SystemAdminStatus.NORMAL);
            memberEntity.setRole(MemberEntity.Role.valueOf(memberDTO.getRole().toString()));
            save(memberEntity);

            return ResultCode.SAVE_SUCCESS.getMessage();
        }
        return ResultCode.CREATE_ACCOUNT_ERROR.getMessage() + ":" + ResultCode.SAVE_REPEAT_FAILD.getMessage();
    }

    @Override
    public MemberEntity findOne(Integer id) {
        return this.findById(id);
    }

    @Override
    public MemberEntity getAdminByEmail(String email) {
        return this.findBy("email", email);
    }

    @Override
    public BasePageInfo findAllData() {
        List<MemberEntity> list = this.findAll();
        BasePageInfo basePageInfo = new BasePageInfo<>();
        basePageInfo.setList(list);
        basePageInfo.setTotal(list.size());
        return basePageInfo;
    }

    @Override
    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = this.findOne(memberDTO.getId());
        if (memberEntity != null){
            memberEntity.setEmail(memberDTO.getEmail());
            memberEntity.setName(memberDTO.getName());
            memberEntity.setPhone(memberDTO.getPhone());
            memberEntity.setBirthday(memberDTO.getBirthday());
            memberEntity.setOrganization(memberDTO.getOrganization());
            memberEntity.setGrade(memberDTO.getGrade());
            memberEntity.setRegistionTime(memberDTO.getRegistionTime());
        }

        this.update(memberEntity);
    }

    /**
     * 登入
     *
     * @param adminLoginQuery
     */
    @Override
    public Map loginV2(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery) {
        String email = adminLoginQuery.getEmail();

        MemberEntity memberEntity = this.getAdminByEmail(email);

        if (null == memberEntity || memberEntity.getStatus() != ProjectConstant.SystemAdminStatus.NORMAL) {
            log.info("[管理員登入][{}][失敗] 原因:{}", email, ResultCode.PASSWOWRD_WRONG.getMessage() );
            throw new BussinessException(ResultCode.PASSWOWRD_WRONG.getMessage());
        }

        Integer memberEntityId = memberEntity.getId();

        // 生成token
        String token = ProjectTokenUtils.genToken();

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("member", memberEntity);

        return map;
    }

    @Override
    public String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery) {

        MemberEntity memberEntity = memberMapper.findAccount(adminLoginQuery.getEmail());
        Boolean isTure = checkoutPassword(adminLoginQuery, memberEntity);

        if (isTure) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", adminLoginQuery);
            memberEntity.getRole();
            // 如果用户角色为空，则默认赋予 ROLE_USER 角色
            if (memberEntity.getRole() == null) {
                memberEntity.setRole(MemberEntity.Role.valueOf(UserRoleConstants.ROLE_USER));
            }
            String token = JwtUtils.generateToken(memberEntity, true);
            return token;
        }

        return null;
    }


    public Boolean checkoutPassword(AdminLoginQuery adminLoginQuery, MemberEntity memberEntity){
        String dbData = AesHelper.decrypt(memberEntity.getPwd());
        String password = adminLoginQuery.getPassword();

        return dbData.equals(password);
    }
}

