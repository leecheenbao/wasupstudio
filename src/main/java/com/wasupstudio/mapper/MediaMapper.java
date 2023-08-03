package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.MediaEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MediaMapper extends CommonMapper<MediaEntity> {
    @Select("SELECT * FROM wa_media WHERE script_id=#{scriptId}")
    List<MediaEntity> findByScriptId(Integer scriptId);

    @Select("SELECT * FROM wa_media WHERE script_id=#{scriptId} AND media_id=#{mediaId}")
    MediaEntity findByScriptIdAndMediaId(Integer scriptId, Integer mediaId);

    @Select("SELECT * FROM wa_media WHERE script_id=#{scriptId} AND description=#{description}")
    MediaEntity findByScriptIdAndDescription(Integer scriptId, String description);

}
