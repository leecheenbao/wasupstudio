package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.ParentConfiglEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

@Mapper
public interface ParentConfigMapper extends CommonMapper<ParentConfiglEntity> {

    void batchInsert(@Param("userList") List<ParentConfiglEntity> list);

}
