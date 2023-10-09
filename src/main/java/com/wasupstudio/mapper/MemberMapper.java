package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.model.vo.AgeDistributionsVo;
import com.wasupstudio.model.vo.MemberAgeVo;
import com.wasupstudio.model.vo.CategoryVo;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapper extends CommonMapper<MemberEntity> {

    @ResultMap("BaseResultMap")
    MemberEntity findAccount(@Param("email") String email);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM wa_member WHERE verificationCode = #{vericationCode}")
    MemberEntity findByVerificationCode(@Param("vericationCode") String vericationCode);

    @ResultMap("BaseResultMap")
    @Select("SELECT * FROM wa_member WHERE lastLogin >= DATE_SUB(NOW(), INTERVAL 7 DAY)")
    List<MemberEntity> findByLastLoginFor7Day();

    @Select("SELECT id, birthday, name, email, category, organization, status, TIMESTAMPDIFF(YEAR, birthday, CURDATE()) as age " +
            "FROM wa_member wm WHERE birthday IS NOT NULL")
    List<MemberAgeVo> findAgeDistributions();

    List<AgeDistributionsVo> findAgeDistributionsStatistics();

    @Select("SELECT * FROM wa_member wm WHERE organization IS NOT NULL AND organization != ''")
    List<MemberEntity> findCategory();

    List<CategoryVo> findCategoryStatistics();

}
