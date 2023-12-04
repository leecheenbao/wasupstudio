package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.ScriptQuestionEntity;
import com.wasupstudio.model.query.QuestionReportQuery;
import com.wasupstudio.model.query.QuestionResultQuery;
import com.wasupstudio.model.query.ScoreDistributionQuery;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScriptQuestionMapper extends CommonMapper<ScriptQuestionEntity> {

    @Select("SELECT * FROM wa_script_question WHERE task_id = #{taskId}")
    List<ScriptQuestionEntity> findByTaskId(@Param("taskId") Integer taskId);

    List<QuestionReportQuery> findReportForEnding();

    List<ScoreDistributionQuery> scoreDistribution(@Param("taskId") Integer taskId);
}
