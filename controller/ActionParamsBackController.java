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
	 * 新增类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/saveparam.do")
	@ResponseBody
	public SverResponse<String> saveParam(HttpSession session,ActionParam actionParam){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法:新增类型
			return actionParamService.addParam(actionParam);
		}
		return SverResponse.createByErrorMessage("无操作权限");
	
	}
	/**
	 * 修改类型
	 * @param session
	 * @param actionParam
	 * @return
	 */
	@RequestMapping("/updateparam.do")
	@ResponseBody
	public SverResponse<String> updateCategory(HttpSession session,ActionParam actionParam){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法:修改类型
					return actionParamService.updateParam(actionParam);
				}
				return SverResponse.createByErrorMessage("无操作权限");
	}
	/**
	 * 删除商品类型
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/delparam.do")
	@ResponseBody
	public SverResponse<String> delParam(HttpSession session,Integer id){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法:删除类型方法
					return actionParamService.delParam(id);
				}
				return SverResponse.createByErrorMessage("无操作权限");
			
	}
	/**
	 * 查找下一级子类型包括根类型
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findchildren.do")
	@ResponseBody
	public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session,@RequestParam(value="id",defaultValue = "0")Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法:查找子类型方法
			return actionParamService.findParamChildren(id);
		}
		return SverResponse.createByErrorMessage("无操作权限");
	}
	/**
	 * 显示商品类型信息
	 * @param session
	 * @return
	 */
	@RequestMapping("/findptype.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> getUserDetail(HttpSession session) {
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionParamService.findptype();
		}
		
		return SverResponse.createByErrorMessage("您无操作权限!");
	}
	/**
	 * 根据id获得商品类型信息
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findpathparam.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> findParm(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断用户是否是管理员
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionParamService.findPathParam(id);
		}
		return SverResponse.createByErrorMessage("您无操作权限");
	}
	/**
	 * 根据id获得配件类型
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findpartstype.do")
	@ResponseBody
	public SverResponse<List<ActionParamVo>> findParmType(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断用户是否是管理员
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionParamService.findPathParam(id);
		}
		return SverResponse.createByErrorMessage("您无操作权限");
	}
}
