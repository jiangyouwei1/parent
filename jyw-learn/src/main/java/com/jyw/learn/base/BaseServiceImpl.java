/**
 * 
 */
package com.jyw.learn.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author jyw
 *
 */
@SuppressWarnings("rawtypes")
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl implements IBaseService<T>{


    @Override
    public Integer count(T entity) {
        Wrapper<T> wrapper = new QueryWrapper<T>(entity);
        return baseMapper.selectCount(wrapper);
    }

    @Override
	@SuppressWarnings("unchecked")
    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * <p>
     * 批量操作 SqlSession
     * </p>
     */
    @Override
	protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    @Override
	protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }


	@Override
	public T queryById(Long id) {
		return (T) baseMapper.selectById(id);
	}


    @Override
    public List<T> queryList(T entity) {
    	QueryWrapper wrapper = new QueryWrapper(entity);
		wrapper.orderByDesc("CREATED_TIME");
        return baseMapper.selectList(wrapper);
    }

	@Override
	public List<T> queryList(Map<String, Object> params) {
		return baseMapper.selectByMap(params);
	}

	@Override
	public List<T> queryList(List<Long> ids) {
		return baseMapper.selectBatchIds(ids);
	}
	

	@Override
	public void update(T record) throws Exception {
		baseMapper.updateById(record);
	}

	@Override
	public void updateBatch(List<T> entityList) throws Exception {
		for (T t : entityList) {
			update(t);
		}
	}

	@Override
	public void insert(T entity) throws Exception {
		baseMapper.insert(entity);
	}
	
	@Override
	public void insertAll(List<T> entity) throws Exception {
		for (T t : entity) {
			baseMapper.insert(t);
		}
	}

	@Override
	public void del(List<Long> ids) throws Exception {
		for (Long id : ids) {
			del(id);
		}
	}

	@Override
	public void del(Long id) throws Exception {
		 baseMapper.deleteById(id);
	}

	@Override
	public void deleteEntitys(List<T> entitys) throws Exception {
		for (T t : entitys) {
			deleteEntity(t);
		}
	}

	@Override
	public void deleteEntity(T t) throws Exception {
		 Wrapper<T> wrapper = new UpdateWrapper<T>(t);
	     baseMapper.delete(wrapper);
	}

	@Override
	public void deleteByMap(Map<String, Object> columnMap) throws Exception {
		baseMapper.deleteByMap(columnMap);
	}
}
