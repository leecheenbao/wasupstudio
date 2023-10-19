package com.wasupstudio.service.Impl;

import com.google.gson.Gson;
import com.wasupstudio.constant.BaseRedisKeyConstant;
import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.constant.UserRoleConstants;
import com.wasupstudio.converter.MemberConverter;
import com.wasupstudio.enums.ResultCode;
import com.wasupstudio.mapper.MemberMapper;
import com.wasupstudio.model.BasePageInfo;
import com.wasupstudio.model.dto.MemberDTO;
import com.wasupstudio.model.entity.LicenseEntity;
import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.query.AdminLoginLogQuery;
import com.wasupstudio.model.query.AdminLoginQuery;
import com.wasupstudio.model.query.AgeDistributionsQuery;
import com.wasupstudio.model.query.CategoryQuery;
import com.wasupstudio.model.vo.AgeDistributionsVo;
import com.wasupstudio.model.vo.MemberAgeVo;
import com.wasupstudio.model.vo.CategoryVo;
import com.wasupstudio.service.AbstractService;
import com.wasupstudio.service.LicenseService;
import com.wasupstudio.service.MemberService;
import com.wasupstudio.util.AesUtils;
import com.wasupstudio.util.DateUtils;
import com.wasupstudio.util.JwtUtils;
import com.wasupstudio.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MemberServiceImpl extends AbstractService<MemberEntity> implements MemberService {
    @Autowired
    public MemberMapper memberMapper;

    @Autowired
    public MemberConverter memberConverter;

    @Autowired
    public LicenseService licenseService;

    @Resource
    public RedisUtil redisUtil;
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
            memberEntity.setRegistionTime(new Date());
            memberEntity.setStatus(ProjectConstant.SystemAdminStatus.NOT_ENABLED);
            memberEntity.setRole(MemberEntity.Role.valueOf(memberDTO.getRole().toString()));
            memberEntity.setVerificationCode(AesUtils.encrypt(memberDTO.getEmail()));
            memberEntity.setGender(memberDTO.getGender());
            memberEntity.setCategory(memberDTO.getCategory());
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
    public BasePageInfo findLoginFor7Day() {
        List<MemberEntity> list = memberMapper.findByLastLoginFor7Day();
        BasePageInfo pageInfo = new BasePageInfo<>();
        pageInfo.setList(list);
        pageInfo.setTotal(list.size());
        return pageInfo;
    }

    @Override
    public AgeDistributionsQuery findAgeDistributions() {
        List<MemberAgeVo> list = memberMapper.findAgeDistributions();
        List<AgeDistributionsVo> ageDistributionsList = memberMapper.findAgeDistributionsStatistics();
        AgeDistributionsQuery<MemberAgeVo> ageDistributionsQuery = new AgeDistributionsQuery<MemberAgeVo>();
        ageDistributionsQuery.setAgeDistributions(ageDistributionsList);
        ageDistributionsQuery.setList(list);
        ageDistributionsQuery.setTotal(list.size());
        return ageDistributionsQuery;
    }

    @Override
    public CategoryQuery findCategory() {
        List<MemberEntity> list = memberMapper.findCategory();
        List<CategoryVo> categoryVoList = memberMapper.findCategoryStatistics();
        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.setCategoryDistributions(categoryVoList);
        categoryQuery.setList(list);
        categoryQuery.setTotal(list.size());
        return categoryQuery;
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
            memberEntity.setLastLogin(memberDTO.getLastLogin());
            memberEntity.setStatus(memberDTO.getStatus());
            memberEntity.setCategory(memberDTO.getCategory());
            memberEntity.setGender(memberDTO.getGender());
        }

        this.update(memberEntity);
    }

    @Override
    public void updatePwd(MemberDTO memberDTO) {
        MemberEntity memberEntity = this.findOne(memberDTO.getId());
        if (memberEntity != null){
            memberEntity.setPwd(AesUtils.encrypt(memberDTO.getPwd()));
        }
        this.update(memberEntity);
    }

    @Override
    public String login(AdminLoginQuery adminLoginQuery, AdminLoginLogQuery adminLoginLogQuery) {

        MemberEntity memberEntity = memberMapper.findAccount(adminLoginQuery.getEmail());

        Boolean isTure = checkoutPassword(adminLoginQuery, memberEntity);

        if (isTure) {
            HashMap<String, Object> dataMap = new HashMap<>();
            dataMap.put("user", adminLoginQuery);
            // 如果用户角色为空，则默认赋予 ROLE_USER 角色
            if (memberEntity.getRole() == null) {
                memberEntity.setRole(MemberEntity.Role.valueOf(UserRoleConstants.ROLE_USER));
            }
            // TODO 測試將remember預設為true讓token時效維持7天
            String token = JwtUtils.generateToken(memberEntity, adminLoginQuery.getIsRemember());
            memberEntity.setLastLogin(new Date());
            update(memberConverter.ItemToDTO(memberEntity));

            checkLicense(memberEntity);
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

            checkLicense(memberEntity);
            return token;
        }

        return null;
    }


    public Boolean checkoutPassword(AdminLoginQuery adminLoginQuery, MemberEntity memberEntity){
        if (memberEntity != null){

            String dbData = AesUtils.decrypt(memberEntity.getPwd());
            String password = adminLoginQuery.getPassword();

            return dbData.equals(password);
        }
        return false;
    }

    public Boolean checkLicense(MemberEntity member){
        redisUtil.delete(String.format(BaseRedisKeyConstant.LOGIN_CHECKED,member.getId()));
        List<LicenseEntity> list = licenseService.findByEmailAndActivated(member.getEmail());
        if (list.isEmpty()){
            return false;
        }

        Long timeDifferenceInMillis = 0L;
        for (LicenseEntity entity : list){
            Long now = DateUtils.currentTimeMillis();
            Long exp = DateUtils.getMillis(entity.getExpirationDate());
            long res = exp - now;
            if (timeDifferenceInMillis < res){
                timeDifferenceInMillis = res;
            }
        }

        // 將啟動碼是否啟動存在redis
        long timeDifferenceInSeconds = timeDifferenceInMillis / 1000;
        boolean check = list.size() > 0;
        redisUtil.setExpire(BaseRedisKeyConstant.LOGIN_CHECKED , Boolean.toString(check), timeDifferenceInSeconds, member.getId());
        return list.size() > 0;
    }


    public static void main(String[] args) {
        String loginChecked = BaseRedisKeyConstant.LOGIN_CHECKED;
        String key = "123";
        String tset =String.format(loginChecked, key);

        System.out.println(tset);

    }
}

