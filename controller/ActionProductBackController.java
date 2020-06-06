package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionProductService;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionProductListVo;

@Controller
@RequestMapping("/mgr/products")
public class ActionProductBackController {
	@Autowired
	private ActionProductService actionProductService;
	@Autowired
	private ActionUserService actionUserService;
	/**
	 * ��ѯ��Ʒ��Ϣ
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/productslist.do")
	@ResponseBody
	public SverResponse<List<ActionProductListVo>> findProducts(HttpSession session,ActionProduct product){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ�����ȡ��Ʒ��Ϣ
					return actionProductService.findProducts(product);
				}
		return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
	/**
	 * �޸���Ʒ״̬�����¼ܣ�����
	 * @param session
	 * @param product_id
	 * @param status
	 * @param is_hot
	 * @return
	 */
	@RequestMapping("/setstatus.do")
	@ResponseBody
	public SverResponse<String> modigyStatus(HttpSession session,Integer product_id,Integer status,Integer is_hot){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�������״̬��Ϣ
			return actionProductService.updateStatus(product_id, status, is_hot);
		}
return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
	/**
	 * ������Ʒ
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/saveproduct.do")
	@ResponseBody
	public SverResponse<String> saveProduct(HttpSession session,ActionProduct product){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���������Ʒ��Ϣ
					return actionProductService.saveOrUpdateProduct(product);
				}
		return SverResponse.createByErrorMessage("�޲���Ȩ��");
			
	}
	/**
	 * ��ѯ������Ʒ��Ϣ
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/productinfolist.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findAllProducts(HttpSession session,ActionProduct product){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��Ƿ��ǹ���Ա
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ�����ȡ��Ʒ��Ϣ
					return actionProductService.findAllProducts(product);
				}
		return SverResponse.createByErrorMessage("�޲���Ȩ��");
	}
}
