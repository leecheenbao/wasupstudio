package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ParentConfigMapper extends CommonMapper<ParentConfiglEntity> {
    @Select("SELECT * FROM wa_script_parent_config WHERE script_detail_id=#{scriptDetailId}")
    List<ParentConfiglEntity> findByScriptDetailId(@Param("scriptDetailId") Integer scriptDetailId);


    void batchInsert(@Param("list") List<ParentConfiglEntity> list, @Param("scriptDetailId") Integer scriptDetailId);
}
