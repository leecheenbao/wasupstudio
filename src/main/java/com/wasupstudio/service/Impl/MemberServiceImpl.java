package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.exception.BussinessException;
import com.wasupstudio.exception.ResultCode;
import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.AbstractService;
import com.wasupstudio.util.AesHelper;
import com.wasupstudio.util.BasePageInfo;
import com.wasupstudio.util.ProjectTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        MemberEntity memberEntity = this.getAdminByEmail(memberDTO.getEmail());
        if (memberEntity == null) {
            memberEntity.setEmail(memberDTO.getEmail());
            memberEntity.setName(memberDTO.getName());
            memberEntity.setPwd(AesHelper.encrypt(memberDTO.getPwd()));
            memberEntity.setPhone(memberDTO.getPhone());
            memberEntity.setBirthday(memberDTO.getBirthday());
            memberEntity.setOrganization(memberDTO.getOrganization());
            memberEntity.setGrade(memberDTO.getGrade());
            memberEntity.setRegistionTime(memberDTO.getRegistionTime());
            memberEntity.setStatus(ProjectConstant.SystemAdminStatus.NORMAL);
            memberEntity.setRole(memberDTO.getRole());
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
    public Map login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery) {
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



}

