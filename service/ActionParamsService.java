package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;

public interface ActionParamsService {
	/**
	 * 获取全换分类信息
	 * @return
	 */
	public SverResponse<List<ActionParam>> findAllParams();
	
}
