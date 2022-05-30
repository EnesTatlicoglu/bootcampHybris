package tr.nttdata.bootcamp.core.product.service;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;

public interface ProductViewHistoryService {

    void createProductViewHistory(ProductModel product, UserModel user);
    int getTotalView(ProductModel product);
}
