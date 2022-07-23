package cn.raths.serve.query;
import cn.raths.basic.query.BaseQuery;
import cn.raths.org.domain.Employee;
import lombok.Data;

/**
 *
 * @author Lynn
 * @since 2022-07-21
 */
@Data
public class OrderProductQuery extends BaseQuery{
    private Integer state;
    private Long empId;
}