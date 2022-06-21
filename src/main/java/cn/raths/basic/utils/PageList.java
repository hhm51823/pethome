package cn.raths.basic.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageList<T> {

    // 分页条数
    private Integer total = 0;

    // 分页数据
    private List<T> rows = new ArrayList<>();
}
