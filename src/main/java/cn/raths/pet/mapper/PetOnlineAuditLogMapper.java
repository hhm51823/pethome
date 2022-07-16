package cn.raths.pet.mapper;

import cn.raths.pet.domain.PetOnlineAuditLog;
import cn.raths.basic.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Mapper
public interface PetOnlineAuditLogMapper extends BaseMapper<PetOnlineAuditLog> {

    void batchSave(List<PetOnlineAuditLog> petOnlineAuditLogs);
}
