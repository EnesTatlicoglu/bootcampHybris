package tr.nttdata.bootcamp.core.product.service.impl;

import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.BestSellerConfigModel;
import tr.nttdata.bootcamp.core.model.BestSellerProductModel;
import tr.nttdata.bootcamp.core.product.dao.BestSellerConfigDao;
import tr.nttdata.bootcamp.core.product.dao.BestSellerProductDao;
import tr.nttdata.bootcamp.core.product.data.ProductSellData;
import tr.nttdata.bootcamp.core.product.service.BestSellerProductService;

import java.util.List;
import java.util.Map;

public class DefaultBestSellerProductService implements BestSellerProductService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultBestSellerProductService.class);
    private BestSellerProductDao bestSellerProductDao;
    private BestSellerConfigDao bestSellerConfigDao;
    private ModelService modelService;

    @Override
    public BestSellerConfigModel getBestSellerConfig() {
        return getBestSellerConfigDao().getConfig();
    }

    @Override
    public Map<String, Long> getSoldCounts(Integer day, Integer count) {
        return getBestSellerProductDao().getSoldCounts(day, count);
    }

    @Override
    public List<ProductSellData> getSoldCountsData(Integer day, Integer count) {
        return getBestSellerProductDao().getSoldCountsData(day, count);
    }

    @Override
    public BestSellerProductModel getBestSellerProduct(String productCode) {
        return getBestSellerProductDao().getBestSellerProduct(productCode);
    }

    @Override
    public List<BestSellerProductModel> getAllBestSellerProducts() {
        return getBestSellerProductDao().getAllBestSellerProducts();
    }

    @Override
    public void createBestSellerProduct(String productCode, Long soldCount) {
        BestSellerProductModel bestSellerProduct = getModelService().create(BestSellerProductModel.class);
        bestSellerProduct.setProductCode(productCode);
        bestSellerProduct.setSoldCount(soldCount);
        getModelService().save(bestSellerProduct);
    }

    @Override
    public void removeAllBestSellerProducts() {
        List<BestSellerProductModel> bestSellerProducts = getAllBestSellerProducts();
        if (CollectionUtils.isNotEmpty(bestSellerProducts)){
            LOG.info("Removing {} best seller product record", bestSellerProducts.size());
            getModelService().removeAll(bestSellerProducts);
        }else {
            LOG.info("Nothing to remove!");
        }

    }

    public BestSellerProductDao getBestSellerProductDao() {
        return bestSellerProductDao;
    }

    public void setBestSellerProductDao(BestSellerProductDao bestSellerProductDao) {
        this.bestSellerProductDao = bestSellerProductDao;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public BestSellerConfigDao getBestSellerConfigDao() {
        return bestSellerConfigDao;
    }

    public void setBestSellerConfigDao(BestSellerConfigDao bestSellerConfigDao) {
        this.bestSellerConfigDao = bestSellerConfigDao;
    }
}
