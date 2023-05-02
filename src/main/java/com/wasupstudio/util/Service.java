package com.wasupstudio.util;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
    /**
     * 保存单条数据
     * @param model
     */
    void save(T model);

    int saveAndFlush(T model);

    /**
     * 批量保存数据
     *
     * @param models
     */
    void save(List<T> models);

    /**
     * 根据主键删除数据
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 通过主鍵刪除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 批量刪除 eg：ids -> “1,2,3,4”
     *
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新
     *
     * @param model
     */
    void update(T model);

    /**
     * 全量更新
     * @param model
     */
    void updateByPrimaryKey(T model);

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    T findById(Integer id);

    T findByIdMaster(long id);
    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    T findById(long id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     *
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    List<T> findAllBy(String fieldName, Object value);

    /**
     * 通过多个ID查找 eg：ids -> “1,2,3,4”
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 根据条件查找
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);

    /**
     * 根据条件查找(TiDB)
     *
     * @param condition
     * @return
     */
    List<T> findByConditionFromTiDB(Condition condition);

    /**
     * 根据条件查找
     *
     * @param condition
     * @return
     */
    List<T> findByConditionMaster(Condition condition);

    /**
     * 获取所有
     *
     * @return
     */
    List<T> findAll();

    /**
     * 根据条件查找数量
     *
     * @param condition
     * @return
     */
    long countByCondition(Condition condition);
}
