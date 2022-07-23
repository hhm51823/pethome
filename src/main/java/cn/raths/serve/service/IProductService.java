package cn.raths.serve.service;

import cn.raths.pet.domain.Pet;
import cn.raths.pet.domain.PetDetail;
import cn.raths.serve.domain.Product;
import cn.raths.basic.service.IBaseService;
import cn.raths.serve.domain.ProductDetail;
import cn.raths.serve.dto.PurchaseDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Lynn
 * @since 2022-07-21
 */
public interface IProductService extends IBaseService<Product> {

    void offsale(List<Long> ids);

    ProductDetail loaddetail(Long id);

    void onsale(List<Product> pets);

    void purchase(PurchaseDto purchaseDto);
}
