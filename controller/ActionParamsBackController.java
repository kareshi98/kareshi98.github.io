package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamService;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionParamVo;


@Controller
@RequestMapping("/mgr/param")
public class ActionParamsBackController {
	@Autowired
	private ActionParamService actionParamService;
	@Autowired
	private ActionUserService actionUserService;
	/**
	 * ��������
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/saveparam.do")
	@ResponseBody
	public SverResponse<String> saveParam(HttpSession session,ActionParam actionParam){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ���:��������
			return actionParamService.addParam(actionParam);
		}
		return SverResponse.createByErrorMessage("�޲���Ȩ��");
	
	}
	/**
	 * �޸�����
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/updateparam.do")
	@ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam actionParam){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���:�޸�����
					return actionParamService.updateParam(actionParam);
				}
				return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
	/**
	 * ɾ����Ʒ����
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/delparam.do")
	@ResponseBody
	public SverResponse<String> delParam(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���:ɾ�����ͷ���
					return actionParamService.delParam(id);
				}
				return SverResponse.createByErrorMessage("�޲���Ȩ��");
			
	}
	/**
	 * ������һ�������Ͱ���������
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findchildren.do")
	@ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue = "0")Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ���:���������ͷ���
			return actionParamService.findParamChildren(id);
		}
		return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
	/**
	 * ��ʾ��Ʒ������Ϣ
	 * @param session
	 * @return
	 */
	@RequestMapping("/findptype.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> getUserDetail(HttpSession session) {
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionParamService.findptype();
		}
		
		return SverResponse.createByErrorMessage("���޲���Ȩ��!");
	}
	/**
	 * ����id�����Ʒ������Ϣ
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findpathparam.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> findParm(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��û��Ƿ��ǹ���Ա
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionParamService.findPathParam(id);
		}
		return SverResponse.createByErrorMessage("���޲���Ȩ��");
	}
	/**
	 * ����id����������
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findpartstype.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> findParmType(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��û��Ƿ��ǹ���Ա
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionParamService.findPathParam(id);
		}
		return SverResponse.createByErrorMessage("���޲���Ȩ��");
	}
}
