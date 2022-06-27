package cn.raths.basic.query;

import lombok.Data;

@Data
public class BaseQuery {

    private Integer currentPage = 1;

    private Integer pageSize = 5;

    /** 关键字 */
    private String keyword;

    /**
     * 获取分页起始位置
     * @return
     */
    public Integer getBegin(){
        return (this.currentPage - 1) * this.pageSize;
    }
}
