package tr.nttdata.bootcamp.facades.product.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.product.service.ProductViewHistoryService;
import tr.nttdata.bootcamp.facades.product.ProductViewHistoryFacade;

public class DefaultProductViewHistoryFacade implements ProductViewHistoryFacade {

    private static Logger LOG = LoggerFactory.getLogger(DefaultProductViewHistoryFacade.class);

    private ProductService productService;
    private UserService userService;
    private ProductViewHistoryService productViewHistoryService;

    @Override
    public void createProductViewHistory(String productCode) {
        ProductModel product = null;
        try{
            product = getProductService().getProductForCode(productCode);
        }catch (Exception e){
            LOG.error("An exception occured while trying to get product for code {}", productCode);
            return;
        }

        if (product == null){
            LOG.error("Product could not be found for code {}", productCode);
            return;
        }

        getProductViewHistoryService().createProductViewHistory(product, getUserService().getCurrentUser());

    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ProductViewHistoryService getProductViewHistoryService() {
        return productViewHistoryService;
    }

    public void setProductViewHistoryService(ProductViewHistoryService productViewHistoryService) {
        this.productViewHistoryService = productViewHistoryService;
    }
}
