package cn.raths.pet.service;

import cn.raths.pet.domain.SearchMasterMsg;
import cn.raths.basic.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-19
 */
public interface ISearchMasterMsgService extends IBaseService<SearchMasterMsg> {

    void publish(SearchMasterMsg searchMasterMsg);
}
