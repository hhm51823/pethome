package cn.raths.sys.domain;

import cn.raths.basic.domain.BaseDomain;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TemdictionaryType extends BaseDomain {

    private String sn;
    private String name;

    private List<TemdictionaryDetail> children = new ArrayList<>();
}
