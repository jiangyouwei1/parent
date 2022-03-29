/**
 * 
 */
package com.jyw.learn.base;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author jyw
 *
 */
@SuppressWarnings("rawtypes")
public interface IBaseService<T> extends IService {
    /**
     *新增
     * @param record
     * @return
     */
    void insert(T record) throws Exception;
    /**
     * 根据主键修改
     * @param record
     * @return
     */
    void update(T record) throws Exception;

    /**
     * @param entityList
     * @return
     * @throws BusinessException
     * @throws ValidateException
     */
    void updateBatch(List<T> entityList) throws Exception;

    /**
     * 删除批量
     * @param ids
     * @param userId
     */
    void del(List<Long> ids) throws Exception;

    /**
     * 单条删除
     * @param id
     */
    void del(Long id) throws Exception;
    /**
     * 批量删除
     * @param t
     * @return
     */
    void deleteEntitys(List<T> entitys) throws Exception;

    /**
     * 单条删除
     * @param t
     * @return
     */
    void deleteEntity(T t) throws Exception;

    /**
     * 根据条件删除
     * @param columnMap
     * @return
     */
    void deleteByMap(Map<String, Object> columnMap) throws Exception;

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T queryById(Long id);

    /**
     * @param params
     * @return
     */
    List<T> queryList(Map<String, Object> params);

    /**
     * @param ids
     * @return
     */
    List<T> queryList(List<Long> ids);

    /**
     * @param entity
     * @return
     */
    List<T> queryList(T entity);

    /**
     * @param entity
     * @return
     * @throws Exception
     */
    Integer count(T entity) throws Exception;
    /**
     * 
     * @param entity
     * @throws Exception
     */
	void insertAll(List<T> entity) throws Exception;
}
