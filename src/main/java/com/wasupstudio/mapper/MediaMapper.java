package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.MediaEntity;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface MediaMapper extends CommonMapper<MediaEntity> {
    @Select("SELECT * FROM wa_media WHERE script_id=#{scriptId}")
    List<MediaEntity> findByScriptId(Integer scriptId);

    @Select("SELECT * FROM wa_media WHERE script_id=#{scriptId} AND description=#{description}")
    MediaEntity findByScriptIdAndDescription(Integer scriptId, String description);

    @Delete("DELETE FROM wa_media WHERE script_id=#{scriptId} AND description=#{description}")
    void deleteByStringIdAndDescription(Integer scriptId, String description);

    @Update("UPDATE wa_media " +
            "SET media_type=#{mediaType}, update_time=#{updateTime}, file_extension=#{fileExtension} " +
            "WHERE script_id=#{scriptId} AND description=#{description}")
    void updateByScriptIdAndDescription(String mediaType, Date updateTime, String fileExtension, Integer scriptId, String description);

}
