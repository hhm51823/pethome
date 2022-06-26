package cn.raths.org.query;

import cn.raths.basic.query.BaseQuery;
import lombok.Data;

@Data
public class ShopQuery extends BaseQuery {
    private String keyword;
}
