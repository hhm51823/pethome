package cn.raths.org.query;

import cn.raths.basic.query.BaseQuery;
import lombok.Data;

@Data
public class DepartmentQuery extends BaseQuery {
    private String keyword;
}
