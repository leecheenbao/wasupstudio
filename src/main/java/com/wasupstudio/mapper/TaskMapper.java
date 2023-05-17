package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.TaskEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper extends CommonMapper<TaskEntity> {

    @Select("SELECT * FROM wa_task WHERE member_id =#{memberId}")
    List<TaskEntity> getMyTask(Integer memberId);
}
