package cn.techaction.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionUserService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.vo.ActionUserVo;

@Controller
@RequestMapping("/mgr/user")
public class ActionUserController {
	@Autowired
	private ActionUserService actionUserService;
	
	
	/**
	 * 登录
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public SverResponse<User> doLogin(HttpSession session,String account,String password) {
		//1.调用Sevice层方法登录
		SverResponse<User> respons=actionUserService.doLogin(account, password);
		//2.判断是否能登录
		if(respons.isSuccess()) {
			
			//3.能登录判断是否是管理员，时管理员信息储存在session中
			User user=respons.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return respons;
			}else if(user.getRole()==ConstUtil.Role.ROLE_CUSTOMER) {
					session.setAttribute(ConstUtil.CUR_USER, user);
					return respons;
			}else
			return SverResponse.createByErrorMessage("不是管理员，无法登录!");
			
		}
		return respons;
	}
	/**
	 * 显示用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionUserService.findUserList();
		}
		
		return SverResponse.createByErrorMessage("您无操作权限!");
	}
	/**
	 * 根据id获得用户信息
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findUser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id){
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断用户是否是管理员
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionUserService.findUser(id);
		}
		return SverResponse.createByErrorMessage("您无操作权限");
	}
	/**
	 * 更新用户信息
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/updateUser.do")
	@ResponseBody
	public SverResponse<String> updateUser(HttpSession session,ActionUserVo userVo){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断用户是否是管理员
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法更新用户信息
					return actionUserService.updateUserInfo(userVo);
				}
		return SverResponse.createByErrorMessage("您无操作权限！");
	}
	/**
	 * 删除用户
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUsers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.判断用户是否登录
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
					
				}
				//2.判断用户是否是管理员
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.调用service中的方法删除用户信息
					return actionUserService.delUser(id);
				}
		
		return SverResponse.createByErrorMessage("您无操作权限！");
	}
	/**
	 * 显示用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping("/getuserinfo.do")
	@ResponseBody
	public SverResponse<List<User>> getUserInfo(HttpSession session) {
		//1.判断用户是否登录
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登陆后操作!");
			
		}
		//2.判断是否是管理员
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.调用service中的方法获取用户信息
			return actionUserService.findUserInfo();
		}
		
		return SverResponse.createByErrorMessage("您无操作权限!");
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 登录
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/do_login.do")
	@ResponseBody
	public SverResponse<User> do_Login(HttpSession session,String account,String password) {
		//1.调用Sevice层方法登录
		SverResponse<User> respons=actionUserService.doLogin(account, password);
		//2.判断是否能登录
		if(respons.isSuccess()) {
			
			//3.能登录判断是否是用户，信息储存在session中
			User user=respons.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_CUSTOMER) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return respons;
			}
			return SverResponse.createByErrorMessage("未注册，无法登录!");
			
		}
		return respons;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 用户验证
	 * @param info
	 * @param type
	 * @return
	 */
	@RequestMapping( value = "/do_check_info.do", method = RequestMethod.POST)
    @ResponseBody
    public SverResponse<String> checkValidUserInfo(String info, String type) {
        return actionUserService.checkValidation(info, type);
    }
	
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return actionUserService.doRegister(user);
	}
	
	/**
	 * 验证用户，获得用户对象
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getuserbyaccount.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByAccount(String account){
		
		return actionUserService.findUserByAccount(account);
	}
	
	/**
	 * 验证用户密码提示问题答案
	 * @param account
	 * @param question
	 * @param asw
	 * @return
	 */
	@RequestMapping(value="/checkuserasw.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> checkUserAnswer(String account,String question,String asw){
		return actionUserService.checkUserAnswer(account,question,asw);
	}
	
	/**
	 * 重置密码
	 * @param userId
	 * @param newpwd
	 * @return
	 */
	@RequestMapping(value="/resetpassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> resetPassword(Integer userId,String newpwd){
		return actionUserService.resetPassword(userId,newpwd);
	}
	
	/**
	 * 根据id获得用户信息
	 * @param session
	 * @param id
	 * @return
	 */
//	@RequestMapping(value="/finduser.do",method=RequestMethod.POST)
//	@ResponseBody
//	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id){
//		//1.判断用户是否登录
//		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
//		if(user==null) {
//			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "请登录后再进行操作！");
//		}
//		//2.用户是不是管理员
//		SverResponse<String> response=actionUserService.isAdmin(user);
//		if(response.isSuccess()) {
//			//3.调用Service中的方法获得相应id的用户信息
//			return actionUserService.findUser(id);
//		}
//		return SverResponse.createByErrorMessage("抱歉，您无操作权限！");
//	}
	
//	/**
//	 * 更新用户信息
//	 * @param session
//	 * @param userVo
//	 * @return
//	 */
//	@RequestMapping(value="/updateuserinfo.do",method=RequestMethod.POST)
//	@ResponseBody
//	public SverResponse<User>  updateUser(HttpSession session,ActionUserVo userVo){
//		//1.判断用户是否登录
//			User curUser = (User)session.getAttribute(ConstUtil.CUR_USER);
//			if(curUser==null) {
//					return SverResponse.createByErrorMessage("请登录后再进行操作！");
//			}
//			//2.用户是不是管理员
//			userVo.setId(curUser.getId());
//			userVo.setAccount(curUser.getAccount());
//			SverResponse<User> resp = actionUserService.updateUserInfo(userVo);
//			if(resp.isSuccess()) {
//				//3.调用Service中的方法更新用户信息
//				session.setAttribute(ConstUtil.CUR_USER, resp.getData());
//			}
//			return resp;
//	}
	
	/**
	 * 修改密码
	 * @param session
	 * @param newwd
	 * @param oldwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session,String newpwd,String oldpwd) {
		//将session取出
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if (user==null) {
			return SverResponse.createByErrorMessage("请先登录！");
		}
		SverResponse<String> result=actionUserService.updatePassword(user,newpwd,oldpwd);
		//修改后将session清空
		if (result.isSuccess()) {
			session.removeAttribute(ConstUtil.CUR_USER);
		}
		return  result;
	}
	/**
	 * 获得用户信息
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/getuserinfo.do",method = RequestMethod.GET)
    @ResponseBody
    public SverResponse<User> getUserInfoByUser(HttpSession session) {
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        return user != null ? SverResponse.createRespBySuccess(user) : SverResponse.createByErrorMessage("无法获取用户信息！");
    }
/**
	 * 用户登出
	 * @param session
	 * @return
	 */
	 @RequestMapping("/do_logout.do")
	 @ResponseBody
	 public SverResponse<String> loginOut(HttpSession session){
		 session.removeAttribute(ConstUtil.CUR_USER);
		 return SverResponse.createRespBySuccess();
	 }
}
