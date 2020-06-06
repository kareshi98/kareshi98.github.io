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
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法:查询订单
					return actionOrderService.findOrderForNoPages(orderNo);
				}
				return SverResponse.createByErrorMessage("无操作权限");
	}
	/**
	 * 查看订单详情
	 * @param session
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getdetail.do")
	@ResponseBody
	public SverResponse<ActionOrderVo> getDetail(HttpSession session,Long orderNo){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断是否是管理员
				
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法:查询订单
					return actionOrderService.mgrDetail(orderNo);
				}
				return SverResponse.createByErrorMessage("无操作权限");
	}
}
