package cn.raths.basic.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseDomain implements Serializable {
    /** 主键id */
    private Long id;
}
