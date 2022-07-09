package cn.raths.org.service;

import cn.raths.basic.dto.ShopRegisterDto;
import cn.raths.org.domain.Shop;
import cn.raths.basic.service.IBaseService;
import cn.raths.org.domain.ShopAuditLog;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-01
 */
public interface IShopService extends IBaseService<Shop> {

    Shop loadById(Long id);

    void settlement(ShopRegisterDto shopRegisterDto);

    void reject(ShopAuditLog shopAuditLog) throws MessagingException;

    void pass(ShopAuditLog shopAuditLog) throws MessagingException;

    void prohibit(ShopAuditLog shopAuditLog) throws MessagingException;

    String activation(Long id);

    void exportExcel(HttpServletResponse response);

    void importExcel(MultipartFile file);

    Map<String, Object> echarts();
}
