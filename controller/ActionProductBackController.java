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
	 * 查询商品信息
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/productslist.do")
	@ResponseBody
	public SverResponse<List<ActionProductListVo>> findProducts(HttpSession session,ActionProduct product){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法获取产品信息
					return actionProductService.findProducts(product);
				}
		return SverResponse.createByErrorMessage("无操作权限");
	}
	/**
	 * 修改商品状态：上下架，热销
	 * @param session
	 * @param product_id
	 * @param status
	 * @param is_hot
	 * @return
	 */
	@RequestMapping("/setstatus.do")
	@ResponseBody
	public SverResponse<String> modigyStatus(HttpSession session,Integer product_id,Integer status,Integer is_hot){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法更新状态信息
			return actionProductService.updateStatus(product_id, status, is_hot);
		}
return SverResponse.createByErrorMessage("无操作权限");
	}
	/**
	 * 新增商品
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/saveproduct.do")
	@ResponseBody
	public SverResponse<String> saveProduct(HttpSession session,ActionProduct product){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法保存商品信息
					return actionProductService.saveOrUpdateProduct(product);
				}
		return SverResponse.createByErrorMessage("无操作权限");
			
	}
	/**
	 * 查询所有商品信息
	 * @param session
	 * @param product
	 * @return
	 */
	@RequestMapping("/productinfolist.do")
	@ResponseBody
	public SverResponse<List<ActionProduct>> findAllProducts(HttpSession session,ActionProduct product){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法获取产品信息
					return actionProductService.findAllProducts(product);
				}
		return SverResponse.createByErrorMessage("无操作权限");
	}
}
