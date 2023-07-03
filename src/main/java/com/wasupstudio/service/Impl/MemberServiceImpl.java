package com.wasupstudio.service.Impl;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.constant.UserRoleConstants;
import com.wasupstudio.converter.MemberConverter;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.util.AesUtils;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class MemberServiceImpl extends AbstractService<MemberEntity> implements MemberService {
    @Autowired
    public MemberMapper memberMapper;

    @Autowired
    public MemberConverter memberConverter;

    @Override
    public String save(MemberDTO memberDTO) {
        if (getAdminByEmail(memberDTO.getEmail()) == null) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setEmail(memberDTO.getEmail());
            memberEntity.setName(memberDTO.getName());
            memberEntity.setPwd(AesUtils.encrypt(memberDTO.getPwd()));
            memberEntity.setPhone(memberDTO.getPhone());
            memberEntity.setBirthday(memberDTO.getBirthday());
            memberEntity.setOrganization(memberDTO.getOrganization());
            memberEntity.setGrade(memberDTO.getGrade());
            memberEntity.setRegistionTime(memberDTO.getRegistionTime());
            memberEntity.setStatus(ProjectConstant.SystemAdminStatus.NOT_ENABLED);
            memberEntity.setRole(MemberEntity.Role.valueOf(memberDTO.getRole().toString()));
            memberEntity.setVerificationCode(AesUtils.encrypt(memberDTO.getEmail()));
            save(memberEntity);

            return ResultCode.SAVE_SUCCESS.getMessage();
        }
        return ResultCode.SAVE_REPEAT_FAILD.getMessage();
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
    public MemberDTO findByVerificationCode(String verificationCode) {
        return memberConverter.ItemToDTO(memberMapper.findByVerificationCode(verificationCode));
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
            memberEntity.setLastIp(memberDTO.getLastIp());
            memberEntity.setRegistionTime(memberDTO.getRegistionTime());
            memberEntity.setLastLogin(memberDTO.getLastLogin());
            memberEntity.setStatus(memberDTO.getStatus());
        }

        this.update(memberEntity);
    }

    @Override
    public String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery) {

        MemberEntity memberEntity = memberMapper.findAccount(adminLoginQuery.getEmail());
//        memberEntity.setLastIp(adminLoginLogQuery.getIp());
//        memberEntity.setLastLogin(new Date());

        Boolean isTure = checkoutPassword(adminLoginQuery, memberEntity);
        if (isTure) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", adminLoginQuery);
            // 如果用户角色为空，则默认赋予 ROLE_USER 角色
            if (memberEntity.getRole() == null) {
                memberEntity.setRole(MemberEntity.Role.valueOf(UserRoleConstants.ROLE_USER));
            }
            // TODO 測試將remember預設為true讓token時效維持7天
            String token = JwtUtils.generateToken(memberEntity, true);
            // TODO PROD記得改回來
            // String token = JwtUtils.generateToken(memberEntity, adminLoginQuery.getIsRemember());

            update(memberConverter.ItemToDTO(memberEntity));
            return token;
        }

        return null;
    }

    @Override
    public String login(String mail) {
        MemberEntity memberEntity = memberMapper.findAccount(mail);
        memberEntity.setLastLogin(new Date());

        if (memberEntity != null) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", memberEntity);
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
        String dbData = AesUtils.decrypt(memberEntity.getPwd());
        String password = adminLoginQuery.getPassword();

        return dbData.equals(password);
    }
}

