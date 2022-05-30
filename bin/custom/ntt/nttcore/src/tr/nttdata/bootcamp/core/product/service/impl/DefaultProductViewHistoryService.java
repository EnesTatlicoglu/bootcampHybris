package tr.nttdata.bootcamp.core.product.service.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import tr.nttdata.bootcamp.core.model.ProductViewHistoryModel;
import tr.nttdata.bootcamp.core.product.dao.ProductViewHistoryDao;
import tr.nttdata.bootcamp.core.product.service.ProductViewHistoryService;

public class DefaultProductViewHistoryService implements ProductViewHistoryService {

    private ProductViewHistoryDao productViewHistoryDao;
    private ModelService modelService;

    @Override
    public void createProductViewHistory(ProductModel product, UserModel user) {
        ProductViewHistoryModel viewHistory = getModelService().create(ProductViewHistoryModel.class);
        viewHistory.setProduct(product);
        viewHistory.setUser(user);
        getModelService().save(viewHistory);
    }

    @Override
    public int getTotalView(ProductModel product) {
        return getProductViewHistoryDao().getTotalView(product);
    }

    public ProductViewHistoryDao getProductViewHistoryDao() {
        return productViewHistoryDao;
    }

    public void setProductViewHistoryDao(ProductViewHistoryDao productViewHistoryDao) {
        this.productViewHistoryDao = productViewHistoryDao;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
}
