package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
@Repository
public class ActionParamsDaoImpl implements ActionParamsDao{

	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public ActionParam findParamById(Integer id) {
		String sql = "select id,parent_id,name,sort_order,status,created,updated,level from action_params where id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),id);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public List<ActionParam> findParamsByParentId(Integer parentId) {
		String sql = "select id,parent_id,name,sort_order,status,created,updated,level from action_params where "
				+"parent_id = ? order by sort_order";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionParam>(ActionParam.class),parentId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
