package tr.nttdata.bootcamp.core.product.service;

import tr.nttdata.bootcamp.core.model.BestSellerConfigModel;
import tr.nttdata.bootcamp.core.model.BestSellerProductModel;
import tr.nttdata.bootcamp.core.product.data.ProductSellData;

import java.util.List;
import java.util.Map;

public interface BestSellerProductService {

    BestSellerConfigModel getBestSellerConfig();

    Map<String, Long> getSoldCounts(Integer day, Integer count);
    List<ProductSellData> getSoldCountsData(Integer day, Integer count);
    BestSellerProductModel getBestSellerProduct(String productCode);
    List<BestSellerProductModel> getAllBestSellerProducts();
    void createBestSellerProduct(String productCode, Long soldCount);
    void removeAllBestSellerProducts();

}
