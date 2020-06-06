package cn.techaction.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
@Service
public class ActionProductServiceImpl implements ActionProductService{
	@Autowired
	private ActionProductDao aProductDao;
	@Autowired
	private ActionParamsDao aParamsDao;

	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		//直接查询所需数据
		List<ActionProduct> products = aProductDao.findHotProducts(num);
		return SverResponse.createRespBySuccess(products);
	}

	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		//创建vo对象
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//一楼数据
		List<ActionProduct> products1 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX);
		vo.setOneFloor(products1);
		//二楼数据
		List<ActionProduct> products2 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX);
		vo.setTwoFloor(products2);
		//三楼数据
		List<ActionProduct> products3 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX);
		vo.setThreeFloor(products3);
		//四楼数据
		List<ActionProduct> products4 = aProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX);
		vo.setFourFloor(products4);
		return SverResponse.createRespBySuccess(vo);
	}
	
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		//判断产品编号是否为空
		if(productId ==null) {
			return SverResponse.createByErrorMessage("产品编号不能为空");
		}
		//查询商品详情
		ActionProduct product =aProductDao.findProductById(productId);
		//判断产品是否下架
		if(product==null) {
			return SverResponse.createByErrorMessage("产品已经下架！");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("产品已经下架！");
		}
		return SverResponse.createRespBySuccess(product);
	}

	@Override
		public SverResponse<PageBean<ActionProductListVo>> findProductsForprotal(Integer productTypeId, Integer partsId,
			String name,int pageNum, int pageSize) {
		// 创建对象
		ActionProduct product =new ActionProduct();
		int totalRecord=0;
		//判断name是否为空
		if(name !=null && !name.equals("")) {
			product.setName(name);
		}
		if(productTypeId!=0) {
			product.setProductId(productTypeId);
		}
		if(partsId!=0) {
			product.setPartsId(partsId);
		}
		//前端显示商品都为在售
		product.setStatus(2);
		//查找符合条件的总记录数
		totalRecord=aProductDao.getTotalCount(product);
		//创建分页对象
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//读取数据
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(),pageSize);
		//封装vo
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
	//封装vo对象
	private ActionProductListVo createProductListVo(ActionProduct product) {
		ActionProductListVo vo = new ActionProductListVo();
		vo.setId(product.getId());
		vo.setName(product.getName());
		vo.setPartsCategory(aParamsDao.findParamById(product.getPartsId()).getName());
		vo.setProductCategory(aParamsDao.findParamById(product.getProductId()).getName());
		vo.setPrice(product.getPrice());
		vo.setStatus(product.getStatus());
		vo.setIconUrl(product.getIconUrl());
		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setHotStatus(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setHot(product.getHot());
		return vo;
	}
}
