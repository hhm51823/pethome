package cn.raths.serve.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.basic.utils.CodeGenerateUtils;
import cn.raths.serve.domain.*;
import cn.raths.serve.dto.PurchaseDto;
import cn.raths.serve.mapper.*;
import cn.raths.serve.service.IProductService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;


    @Autowired
    private ProductOnlineAuditLogMapper productOnlineAuditLogMapper;

    @Autowired
    private OrderProductMapper orderProductMapper;


    @Autowired
    private OrderProductLogMapper orderProductLogMapper;


    @Override
    public void remove(Long id) {
        productMapper.remove(id);
        productDetailMapper.removeByProductId(id);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
        ProductDetail detail = productDetailMapper.loadByProductId(product.getId());
        productDetailMapper.update(detail);
    }

    @Override
    public void save(Product product) {
        productMapper.save(product);
        ProductDetail detail = product.getDetail();
        detail.setProductId(product.getId());
        productDetailMapper.save(detail);
    }

    @Override
    public void onsale(List<Product> products) {

        List<ProductOnlineAuditLog> productOnlineAuditLogs = new ArrayList<>();

        // 保存合规且状态为下架的的id
        List<Long> ids = new ArrayList<>();

        for (Product product : products){
            // 过滤掉已上架的宠物
            if(product.getState() == 1){
                continue;
            }
            // 用来拼接不合规的信息
            StringBuffer logStr = new StringBuffer();
            // 百度AI审核名称和图片
            if(!BaiduAuditUtils.TextCensor(product.getName())){
                logStr.append("<名称不合规>：" + product.getName());
            }
            String[] resources = product.getResources().split(",");
            for (String path : resources){
                if(!BaiduAuditUtils.petImgCensor(path)){
                    logStr.append("<图片不合规>：" + path);
                }
            }
            if(logStr.length() > 0){
                ProductOnlineAuditLog temp = new ProductOnlineAuditLog();
                temp.setProductId(product.getId());
                temp.setNote(logStr.toString());
                temp.setState(0);
                productOnlineAuditLogs.add(temp);
                continue;
            }
            ids.add(product.getId());
        }
        if(ids.size() > 0){
            productMapper.onsale(ids);
        }
        if (productOnlineAuditLogs.size() > 0){
            productOnlineAuditLogMapper.batchSave(productOnlineAuditLogs);
        }


    }

    /**
     * @Title: offsale
     * @Description: 批量下架
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/7/16 20:20
     * @Parameters: [ids]
     * @Return void
     */
    @Override
    public void offsale(List<Long> ids) {
        productMapper.offsale(ids);
    }

    @Override
    public ProductDetail loaddetail(Long productId) {
        return productDetailMapper.loadByProductId(productId);
    }

    /**
     * @Title: purchase
     * @Description: 服务订单
     * @Author: Lynn
     * @Version: 1.0
     * @Date:  2022/7/21 12:18
     * @Parameters: [purchaseDto]
     * @Return void
     */
    @Override
   public void purchase(PurchaseDto purchaseDto) {
        OrderProduct orderProduct = purchaseDto2OrderProduct(purchaseDto);

        // 先保存数据
        orderProductMapper.save(orderProduct);
        // 审核字段
        StringBuffer str = new StringBuffer();
        str.append(purchaseDto.getPet().getName())
                .append(purchaseDto.getAddress());
        if(!BaiduAuditUtils.TextCensor(str.toString())){
            // 审核未通过
            // 修改状态为驳回
            orderProduct.setState(-2);
            // 添加审核日志
            OrderProductLog orderProductLog = new OrderProductLog();
            orderProductLog.setProductorderId(orderProduct.getId());
            orderProductLog.setNote("文字审核不通过.");
            orderProductLogMapper.save(orderProductLog);

            // 修改
            orderProductMapper.update(orderProduct);
            // 可以短信或邮箱提示用户

            throw new BusinessException("文字审核不通过");
        }else{
            // 审核通过
            orderProduct.setState(0);

            // 修改
            orderProductMapper.update(orderProduct);
        }

    }

    private OrderProduct purchaseDto2OrderProduct(PurchaseDto purchaseDto) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductId(purchaseDto.getProductId());

        orderProduct.setPrice(purchaseDto.getPrice());
        orderProduct.setAddress(purchaseDto.getAddress());
        orderProduct.setDigest(purchaseDto.getDigest());
        // 工具类生成订单编号
        String orderSn = CodeGenerateUtils.generateOrderSn(purchaseDto.getUserId());
        orderProduct.setOrderSn(orderSn);
        orderProduct.setLastPayTime(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
        orderProduct.setShopId(purchaseDto.getShopId());
        orderProduct.setUserId(purchaseDto.getUserId());
        return orderProduct;
    }

}
