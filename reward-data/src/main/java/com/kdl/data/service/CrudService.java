package com.kdl.data.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.kdl.common.util.UserContextUtil;
import com.kdl.data.IdGen;
import com.kdl.data.dao.CrudDao;
import com.kdl.data.entity.BaseEntity;

@Transactional(rollbackFor = Exception.class)
public class CrudService<D extends CrudDao<T>,T extends BaseEntity<? extends Serializable>> extends BaseService{

	@Autowired
	protected D dao;
	
	public int save(T t) {
		t.setCreateTime(new Date());
		
		if(StringUtils.isEmpty(t.getCreateBy())) {
			t.setCreateBy(UserContextUtil.getUserId());
		}
		t.setUpdateBy(t.getCreateBy());
		// 自增主键的insert sql 不能出现插入id
		if (null == t.getId() || StringUtils.isEmpty(t.getId().toString())) {
			Class<?> clazz = t.getClass();
			Type type = clazz.getGenericSuperclass();
			if(type instanceof ParameterizedType) {
				ParameterizedType parameterizedType =  (ParameterizedType) type;
				if(parameterizedType.getActualTypeArguments()[0].equals(String.class)) {
					Field field = ReflectionUtils.findField(clazz, "id");
					try {
						field.setAccessible(Boolean.TRUE);
						field.set(t, IdGen.uuid());
					} catch (Exception e) {
						logger.error("set id error:",e);
					}
				}
			}
		}
		int no = dao.insert(t);
		if(t.isNeedBak() && no==1) {
			saveBak(t.getId());
		}
		return no;
	}
	
	public int updateSelective(T t) {
		Assert.notNull(t,"对象不能为空");
		Assert.notNull(t.getId(),"更新对象id为空");
		Assert.hasLength(t.getId().toString(),"更新对象id为空");
		
		t.setUpdateTime(new Date());
		if(StringUtils.isEmpty(t.getUpdateBy())) {
			t.setUpdateBy(UserContextUtil.getUserId());
		}
		int no = dao.updateByPrimaryKeySelective(t);
		if(no>0 && t.isNeedBak()) {
			saveBak(t.getId());
		}
		return no;
	}
	
	public int updateById(T t) {
		Assert.notNull(t, "更新对象为空");
		Assert.notNull(t.getId(), "更新对象id为空");
		Assert.hasLength(t.getId().toString(), "更新对象id为空");
		
		t.setUpdateTime(new Date());
		if(StringUtils.isEmpty(t.getUpdateBy())) {
			t.setUpdateBy(UserContextUtil.getUserId());
		}
		int no = dao.updateByPrimaryKey(t);
		if(no>0 && t.isNeedBak()) {
			saveBak(t.getId());
		}
		return no;
	}
	
	@Transactional(readOnly=true)
	public T findById(Serializable id) {
		Assert.notNull(id, "id为空");
		Assert.hasLength(id.toString(), "id为空");
		return dao.selectByPrimaryKey(id);
	}

	public int deleteLogicByPrimaryKey(Serializable id) {
		Assert.notNull(id, "id为空");
		Assert.hasLength(id.toString(), "id为空");
		T t = this.findById(id);
		if(null != t) {
			t.setUpdateTime(new Date());
			t.setUpdateBy(UserContextUtil.getUserId());
			t.setIsDeleted(true);
			return updateSelective(t);
		}
		return  0;
	}
	
	public int deletePhysicalByPrimaryKey(Serializable id) {
		return dao.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public List<T> findList(T t){
		return dao.findList(t);
	}
	
//	@Transactional(readOnly=true)
//	public PageInfo<T> findByPage(T t,int pageNum, int PageSize){
//		PageHelper.startPage(pageNum,PageSize,Boolean.TRUE);
//		List<T> list = findList(t);
//		return new PageInfo<T>(list);
//	}
	
	public void saveBak(Serializable id) {
	}

}
