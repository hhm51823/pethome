package cn.raths.pet.service.impl;

import cn.raths.basic.exception.BusinessException;
import cn.raths.basic.utils.BaiduAuditUtils;
import cn.raths.basic.utils.DistanceUtil;
import cn.raths.basic.utils.Point;
import cn.raths.org.domain.Shop;
import cn.raths.org.mapper.ShopMapper;
import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.pet.domain.SearchMasterMsgAuditLog;
import cn.raths.pet.mapper.SearchMasterMsgAuditLogMapper;
import cn.raths.pet.mapper.SearchMasterMsgMapper;
import cn.raths.pet.service.ISearchMasterMsgService;
import cn.raths.basic.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-19
 */
@Service
public class SearchMasterMsgServiceImpl extends BaseServiceImpl<SearchMasterMsg> implements ISearchMasterMsgService {

    @Autowired
    private SearchMasterMsgMapper searchMasterMsgMapper;

    @Autowired
    private SearchMasterMsgAuditLogMapper searchMasterMsgAuditLogMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void publish(SearchMasterMsg searchMasterMsg) {
        // 先保存数据
        searchMasterMsgMapper.save(searchMasterMsg);
        // 审核字段
        StringBuffer str = new StringBuffer();
        str.append(searchMasterMsg.getTitle())
                .append(searchMasterMsg.getName())
                .append(searchMasterMsg.getCoatColor())
                .append(searchMasterMsg.getAddress());
        if(!BaiduAuditUtils.TextCensor(str.toString())){
            // 审核未通过
            // 修改状态为待审核，默认就是待审核
            // 添加审核日志
            SearchMasterMsgAuditLog searchMasterMsgAuditLog = new SearchMasterMsgAuditLog();
            searchMasterMsgAuditLog.setMsgId(searchMasterMsg.getId());
            searchMasterMsgAuditLog.setNote("文字审核不通过.");
            searchMasterMsgAuditLogMapper.save(searchMasterMsgAuditLog);
            // 设置note,根据审核结果填写
            searchMasterMsg.setNote("文字审核不通过");
            // 修改
            searchMasterMsgMapper.update(searchMasterMsg);
            // 可以短信或邮箱提示用户

            throw new BusinessException("文字审核不通过,已保存到个人中心的草稿箱");
        }else{
            // 找到店铺则设置状态为审核通过
            searchMasterMsg.setState(1);
            searchMasterMsg.setNote("审核通过！");
            // 审核通过
            // 查询所有店铺
            List<Shop> shops = shopMapper.loadAll();
            // 获取用户地址
            Point point = DistanceUtil.getPoint(searchMasterMsg.getAddress());
            // 寻找距离用户50公里范围内最近的店铺
            Shop shop = DistanceUtil.getNearestShop(point, shops);
            if(shop != null){
                // 设置店铺id
                searchMasterMsg.setShopId(shop.getId());
            }
            // 没有合适的店铺则放入寻主池,不设置shopId即可

            // 修改
            searchMasterMsgMapper.update(searchMasterMsg);
        }

    }
}
