package cn.techaction.controller.protal;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.techaction.pojo.ActionParam;
import cn.techaction.common.SverResponse;
import cn.techaction.service.ActionParamService;

@Controller
@RequestMapping("/mgr/protal/param")
public class ActionParamController {
	@Autowired
	private ActionParamService aParamService;
	
	/**
	 * 查找�?有商品类�?
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/findallparams.do",method=RequestMethod.GET)
	@ResponseBody
	public SverResponse<List<ActionParam>> findAllParams( HttpSession session){
		return aParamService.findAllParams();
	}
}
