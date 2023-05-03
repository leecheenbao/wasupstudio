package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.MemberEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

@Mapper
public interface MemberMapper extends CommonMapper<MemberEntity> {

    @ResultMap("BaseResultMap")
    MemberEntity findAccount(@Param("email") String email);
}
