package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.ScriptDetailEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptDetailMapper extends CommonMapper<ScriptDetailEntity> {

    @Select("SELECT * FROM wa_script_detail WHERE script_id = #{scriptId} AND period = #{period}")
    ScriptDetailEntity findByVerificationCode(@Param("scriptId") Integer scriptId, @Param("period") Integer period);
    @Select("SELECT * FROM wa_script_detail WHERE script_id = #{scriptId}")
    List<ScriptDetailEntity> findByScriptId(@Param("scriptId") Integer scriptId);
}
