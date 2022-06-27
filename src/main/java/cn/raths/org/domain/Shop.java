package cn.raths.org.domain;

import cn.raths.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Shop extends BaseDomain {


    /** 店铺名称 */
    private String name;

    /** 电话 */
    private String tel;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime = new Date();

    /** 状态 */
    private Integer state;

    /** 地址 */
    private String address;

    /** logo地址 */
    private String logo;

    /** 店长id */
    private Long admin_id;

    /** 店长 */
    private Employee boss;

}
