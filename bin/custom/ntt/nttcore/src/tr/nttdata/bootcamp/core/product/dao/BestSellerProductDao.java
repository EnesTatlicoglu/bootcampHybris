package tr.nttdata.bootcamp.core.product.dao;

import tr.nttdata.bootcamp.core.model.BestSellerProductModel;
import tr.nttdata.bootcamp.core.product.data.ProductSellData;

import java.util.List;
import java.util.Map;

public interface BestSellerProductDao {

    Map<String, Long> getSoldCounts(Integer day, Integer count);
    List<ProductSellData> getSoldCountsData(Integer day, Integer count);
    BestSellerProductModel getBestSellerProduct(String productCode);
    List<BestSellerProductModel> getAllBestSellerProducts();
}
