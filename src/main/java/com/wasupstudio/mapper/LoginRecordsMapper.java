package com.wasupstudio.mapper;

import com.wasupstudio.model.entity.LoginRecordsEntity;
import com.wasupstudio.model.query.LoginRecordsQuery;
import com.wasupstudio.util.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginRecordsMapper extends CommonMapper<LoginRecordsEntity> {
    @Select(" SELECT DATE(login_time) AS login_date, COUNT(*) AS count FROM wa_login_records " +
            " WHERE login_time >= DATE_SUB(NOW(), INTERVAL 7 DAY) " +
            " GROUP BY DATE(login_time) ORDER BY DATE(login_time) ")
    List<LoginRecordsQuery> queryBy7Day();

    @Select(" SELECT DATE(login_time) AS login_date, COUNT(*) AS count FROM wa_login_records " +
            " WHERE login_time BETWEEN #{from} AND #{to} " +
            " GROUP BY DATE(login_time) ORDER BY DATE(login_time) ")
    List<LoginRecordsQuery> queryBDay( String from, String to);

}
