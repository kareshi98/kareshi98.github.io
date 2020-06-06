package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;

@Repository
public class ActionProductDaoImpl implements ActionProductDao{
	@Autowired
	private QueryRunner queryRunner;

	private String alias = "id,name,product_id as productId,"
			+ "parts_id as partsId,icon_url as iconUrl,sub_images as"
			+ " subImages,detail,spec_param as specParam,price,stock"
			+ ",status,is_hot as hot,created,updated";
	
	@Override
	public List<ActionProduct> findHotProducts(Integer num) {
		String sql = "select "+ this.alias +" from action_products where is_hot=1 and status=2 order by updated,id desc";
		if(num !=null) {
			sql+=" limit 0,?";
		}
		try {
			if(num !=null) {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class), num);
			}else {
				return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId) {
		String sql = "select "+this.alias+ " from action_products where product_id = ? and status=2 order by updated desc";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class),categoryId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public ActionProduct findProductById(Integer id) {
		String sql = "select "+this.alias + " from action_products where id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionProduct>(ActionProduct.class),id);
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	

	}

	@Override
	public Integer getTotalCount(ActionProduct product) {
		String sql="select count(id) as num from action_products where 1=1";
		List<Object> params=new ArrayList<>();
		if(product.getId()!=null) {
			sql+=" and id = ?";
			params.add(product.getId());
		}
		if(product.getName()!=null) {
			sql+=" and name like ?";
			params.add("%"+product.getName()+"%");
		}
		if(product.getStatus()!=null) {
			sql+=" and status = ?";
			params.add(product.getStatus());
		}
		if(product.getProductId()!=null) {
			sql+=" and product_id = ?";
			params.add(product.getProductId());
		}
		if(product.getPartsId()!=null) {
			sql+=" and parts_id = ?";
			params.add(product.getPartsId());
		}
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), params.toArray()).get(0).intValue();
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize) {
		String sql = "select "+this.alias+ " from action_products where 1=1";
		List<Object> params = new ArrayList<>();
		if(product.getId()!=null){
			sql+=" and id = ?";
			params.add(product.getId());
		}
		if(product.getName()!=null) {
			sql+=" and name like ?";
			params.add("%"+product.getName()+"%" );
		}
		if(product.getStatus()!=null) {
			sql+=" and status = ?";
			params.add(product.getStatus());
		}
		if(product.getProductId()!=null) {
			sql+=" and product_id = ?";
			params.add(product.getProductId());
		}
		if(product.getPartsId()!=null) {
			sql+=" and parts_id= ?";
			params.add(product.getPartsId());
		}
		sql+=" limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionProduct>(ActionProduct.class),params.toArray());
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
