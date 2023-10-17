package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.StudentConfiglEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentConfigMapper extends CommonMapper<StudentConfiglEntity> {

    @Select("SELECT * FROM wa_script_student_config WHERE script_detail_id=#{scriptDetailId}")
    List<StudentConfiglEntity> findByScriptDetailId(@Param("scriptDetailId") Integer scriptDetailId);

    void batchInsert(@Param("list") List<StudentConfiglEntity> list, @Param("scriptDetailId") Integer scriptDetailId);

    @Delete("SELECT * FROM wa_script_student_config WHERE script_detail_id=#{scriptDetailId}")
    void deleteByScriptDetailId(Integer scriptDetailId);
}
