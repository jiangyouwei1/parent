/**
 * 
 */
package com.jyw.learn.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import java.util.ArrayList;
import java.util.List;

/**
 * @author jyw
 *
 */
public abstract class BaseEntity<T> extends Model
{
	
	@Override
	public IPage selectPage(IPage page, Wrapper queryWrapper) {
		return super.selectPage(page, queryWrapper);
	}
}
