package com.wasupstudio.util;


import com.wasupstudio.exception.BussinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
@Slf4j
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected CommonMapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }

    @Override
    public T findByIdMaster(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int saveAndFlush(T model) {
        return mapper.insertUseGeneratedKeys(model);
    }

    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }

    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteById(String id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public void updateByPrimaryKey(T model) {
        mapper.updateByPrimaryKey(model);
    }

    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findById(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            T target = mapper.selectOne(model);
            return target;
        } catch (ReflectiveOperationException e) {
            log.error("findBy ReflectiveOperationException", e);
            throw new BussinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findAllBy(String fieldName, Object value) {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.select(model);
        } catch (ReflectiveOperationException e) {
            log.error("findAllBy ReflectiveOperationException", e);
            throw new BussinessException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findByConditionFromTiDB(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findByConditionMaster(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }

    @Override
    public long countByCondition(Condition condition) {
        return mapper.selectCountByCondition(condition);
    }

}
