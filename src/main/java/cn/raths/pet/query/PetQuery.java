package cn.raths.pet.query;
import cn.raths.basic.query.BaseQuery;
import lombok.Data;

/**
 *
 * @author Lynn
 * @since 2022-07-16
 */
@Data
public class PetQuery extends BaseQuery{
    private Long shopId;
    private Integer state;
}