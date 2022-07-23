package cn.raths.pet.query;
import cn.raths.basic.query.BaseQuery;
import lombok.Data;

/**
 *
 * @author Lynn
 * @since 2022-07-19
 */
@Data
public class SearchMasterMsgQuery extends BaseQuery{

    private Integer state;

    private Long userId;

    private Long shopId;

}