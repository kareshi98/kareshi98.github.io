package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.service.ActionParamsService;
@Service
public class ActionParamsServiceImpl implements ActionParamsService {
	@Autowired
	private ActionParamsDao aParamDao;

	@Override
	public SverResponse<List<ActionParam>> findAllParams() {
		//查找一级子节点
		List<ActionParam> parammList=aParamDao.findParamsByParentId(0);
		//递归查询所有子节点
		for(ActionParam param : parammList) {
			findDirectChildren(param);
		}
		return SverResponse.createRespBySuccess(parammList);
	}
	//递归查找
	private void findDirectChildren(ActionParam parentParam) {
		//查找子节点
		List<ActionParam> paramList = aParamDao.findParamsByParentId(parentParam.getId());
		parentParam.setChildren(paramList);
		for(ActionParam p : paramList){
			findDirectChildren(p);
		}
	}
	
}
