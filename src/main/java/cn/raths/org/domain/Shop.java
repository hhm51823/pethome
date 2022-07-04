package cn.raths.org.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.raths.basic.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lynn
 * @since 2022-07-01
 */
@Data
public class Shop extends BaseDomain{


    @Excel(name="店铺名称", orderNum = "1", width = 30, isImportField = "true_st")
    private String name;
    @Excel(name="电话", orderNum = "2", width = 30, isImportField = "true_st")
    private String tel;

    @Excel(name="创建", orderNum = "3", width = 30, isImportField = "true_st", format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registerTime = new Date();
    /**
     * 店铺状态：0待审核，1以审核，-1驳回，2待激活
     */
    @Excel(name="状态", replace = {"待审核_0","已审核_1","驳回_-1","待激活_2"}, orderNum = "4", width = 30, isImportField = "true_st")
    private Integer state = 0;

    @Excel(name="地址", orderNum = "5", width = 30, isImportField = "true_st")
    private String address;
    @Excel(name="店铺Logo", orderNum = "6", width = 30, isImportField = "true_st")
    private String logo;
    private Long adminId;
    // 员工对象引用字段，用于接收员工信息
    private Employee admin;

}
