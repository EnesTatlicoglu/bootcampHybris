package tr.nttdata.bootcamp.core.product.dao;

import de.hybris.platform.core.model.product.ProductModel;

public interface ProductViewHistoryDao {

    int getTotalView(ProductModel product);
}
