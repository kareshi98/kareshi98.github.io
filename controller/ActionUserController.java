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
	 * ��¼
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.do")
	@ResponseBody
	public SverResponse<User> doLogin(HttpSession session,String account,String password) {
		//1.����Sevice�㷽����¼
		SverResponse<User> respons=actionUserService.doLogin(account, password);
		//2.�ж��Ƿ��ܵ�¼
		if(respons.isSuccess()) {
			
			//3.�ܵ�¼�ж��Ƿ��ǹ���Ա��ʱ����Ա��Ϣ������session��
			User user=respons.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_ADMIN) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return respons;
			}else if(user.getRole()==ConstUtil.Role.ROLE_CUSTOMER) {
					session.setAttribute(ConstUtil.CUR_USER, user);
					return respons;
			}else
			return SverResponse.createByErrorMessage("���ǹ���Ա���޷���¼!");
			
		}
		return respons;
	}
	/**
	 * ��ʾ�û���Ϣ
	 * @param session
	 * @return
	 */
	@RequestMapping("/finduserlist.do")
	@ResponseBody
	public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionUserService.findUserList();
		}
		
		return SverResponse.createByErrorMessage("���޲���Ȩ��!");
	}
	/**
	 * ����id����û���Ϣ
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/findUser.do")
	@ResponseBody
	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��û��Ƿ��ǹ���Ա
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionUserService.findUser(id);
		}
		return SverResponse.createByErrorMessage("���޲���Ȩ��");
	}
	/**
	 * �����û���Ϣ
	 * @param session
	 * @param userVo
	 * @return
	 */
	@RequestMapping("/updateUser.do")
	@ResponseBody
	public SverResponse<String> updateUser(HttpSession session,ActionUserVo userVo){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��û��Ƿ��ǹ���Ա
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ��������û���Ϣ
					return actionUserService.updateUserInfo(userVo);
				}
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");
	}
	/**
	 * ɾ���û�
	 * @param session
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUsers.do")
	@ResponseBody
	public SverResponse<String> delUsers(HttpSession session,Integer id){
		//1.�ж��û��Ƿ��¼
				User user=(User)session.getAttribute(ConstUtil.CUR_USER);
				if(user==null) {
					return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
					
				}
				//2.�ж��û��Ƿ��ǹ���Ա
				SverResponse<String> response=actionUserService.isAdmin(user);
				if(response.isSuccess()) {
					//3.����service�еķ���ɾ���û���Ϣ
					return actionUserService.delUser(id);
				}
		
		return SverResponse.createByErrorMessage("���޲���Ȩ�ޣ�");
	}
	/**
	 * ��ʾ�û���Ϣ
	 * @param session
	 * @return
	 */
	@RequestMapping("/getuserinfo.do")
	@ResponseBody
	public SverResponse<List<User>> getUserInfo(HttpSession session) {
		//1.�ж��û��Ƿ��¼
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if(user==null) {
			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���½�����!");
			
		}
		//2.�ж��Ƿ��ǹ���Ա
		
		SverResponse<String> response=actionUserService.isAdmin(user);
		if(response.isSuccess()) {
			//3.����service�еķ�����ȡ�û���Ϣ
			return actionUserService.findUserInfo();
		}
		
		return SverResponse.createByErrorMessage("���޲���Ȩ��!");
	}
	
	
	
	
	
	
	
	
	
	/**
	 * ��¼
	 * @param session
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping("/do_login.do")
	@ResponseBody
	public SverResponse<User> do_Login(HttpSession session,String account,String password) {
		//1.����Sevice�㷽����¼
		SverResponse<User> respons=actionUserService.doLogin(account, password);
		//2.�ж��Ƿ��ܵ�¼
		if(respons.isSuccess()) {
			
			//3.�ܵ�¼�ж��Ƿ����û�����Ϣ������session��
			User user=respons.getData();
			if(user.getRole()==ConstUtil.Role.ROLE_CUSTOMER) {
				session.setAttribute(ConstUtil.CUR_USER, user);
				return respons;
			}
			return SverResponse.createByErrorMessage("δע�ᣬ�޷���¼!");
			
		}
		return respons;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * �û���֤
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
	 * �û�ע��
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/do_register.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> registerUser(User user){
		return actionUserService.doRegister(user);
	}
	
	/**
	 * ��֤�û�������û�����
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getuserbyaccount.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<User> getUserByAccount(String account){
		
		return actionUserService.findUserByAccount(account);
	}
	
	/**
	 * ��֤�û�������ʾ�����
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
	 * ��������
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
	 * ����id����û���Ϣ
	 * @param session
	 * @param id
	 * @return
	 */
//	@RequestMapping(value="/finduser.do",method=RequestMethod.POST)
//	@ResponseBody
//	public SverResponse<ActionUserVo> findUser(HttpSession session,Integer id){
//		//1.�ж��û��Ƿ��¼
//		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
//		if(user==null) {
//			return SverResponse.createByErrorCodeMessage(ResponseCode.SUCCESS.getCode(), "���¼���ٽ��в�����");
//		}
//		//2.�û��ǲ��ǹ���Ա
//		SverResponse<String> response=actionUserService.isAdmin(user);
//		if(response.isSuccess()) {
//			//3.����Service�еķ��������Ӧid���û���Ϣ
//			return actionUserService.findUser(id);
//		}
//		return SverResponse.createByErrorMessage("��Ǹ�����޲���Ȩ�ޣ�");
//	}
	
//	/**
//	 * �����û���Ϣ
//	 * @param session
//	 * @param userVo
//	 * @return
//	 */
//	@RequestMapping(value="/updateuserinfo.do",method=RequestMethod.POST)
//	@ResponseBody
//	public SverResponse<User>  updateUser(HttpSession session,ActionUserVo userVo){
//		//1.�ж��û��Ƿ��¼
//			User curUser = (User)session.getAttribute(ConstUtil.CUR_USER);
//			if(curUser==null) {
//					return SverResponse.createByErrorMessage("���¼���ٽ��в�����");
//			}
//			//2.�û��ǲ��ǹ���Ա
//			userVo.setId(curUser.getId());
//			userVo.setAccount(curUser.getAccount());
//			SverResponse<User> resp = actionUserService.updateUserInfo(userVo);
//			if(resp.isSuccess()) {
//				//3.����Service�еķ��������û���Ϣ
//				session.setAttribute(ConstUtil.CUR_USER, resp.getData());
//			}
//			return resp;
//	}
	
	/**
	 * �޸�����
	 * @param session
	 * @param newwd
	 * @param oldwd
	 * @return
	 */
	@RequestMapping(value="/updatepassword.do",method=RequestMethod.POST)
	@ResponseBody
	public SverResponse<String> updatePassword(HttpSession session,String newpwd,String oldpwd) {
		//��sessionȡ��
		User user=(User)session.getAttribute(ConstUtil.CUR_USER);
		if (user==null) {
			return SverResponse.createByErrorMessage("���ȵ�¼��");
		}
		SverResponse<String> result=actionUserService.updatePassword(user,newpwd,oldpwd);
		//�޸ĺ�session���
		if (result.isSuccess()) {
			session.removeAttribute(ConstUtil.CUR_USER);
		}
		return  result;
	}
	/**
	 * ����û���Ϣ
	 * @param session
	 * @return
	 */
	@RequestMapping( value = "/getuserinfo.do",method = RequestMethod.GET)
    @ResponseBody
    public SverResponse<User> getUserInfoByUser(HttpSession session) {
        User user = (User)session.getAttribute(ConstUtil.CUR_USER);
        return user != null ? SverResponse.createRespBySuccess(user) : SverResponse.createByErrorMessage("�޷���ȡ�û���Ϣ��");
    }
/**
	 * �û��ǳ�
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
