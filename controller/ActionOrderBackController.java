package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionOrderVo;

@Controller
@RequestMapping("/mgr/order")
public class ActionOrderBackController {
	@Autowired
	private ActionOrderService actionOrderService;
	@Autowired
	private ActionUserService actionUserService;
	@RequestMapping("/findorders_nopages.do")
	@ResponseBody
	public SverResponse<List<ActionOrderVo>> findOrder(HttpSession session,Long orderNo){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���:��ѯ����
					return actionOrderService.findOrderForNoPages(orderNo);
				}
				return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
	/**
	 * �鿴��������
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session,Long orderNo){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���:��ѯ����
					return actionOrderService.mgrDetail(orderNo);
				}
				return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
}