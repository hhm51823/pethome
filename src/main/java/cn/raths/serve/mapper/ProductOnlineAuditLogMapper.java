package cn.raths.serve.mapper;

import cn.raths.serve.domain.ProductOnlineAuditLog;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Mapper
public interface ProductOnlineAuditLogMapper extends BaseMapper<ProductOnlineAuditLog> {

    void batchSave(List<ProductOnlineAuditLog> productOnlineAuditLogs);
}
