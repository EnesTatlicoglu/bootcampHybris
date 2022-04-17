package tr.nttdata.bootcamp.facades;

import tr.nttdata.bootcamp.data.ProductData;

import java.util.List;

public interface ProductFacade {

    List<ProductData> getProducts();

    ProductData getProductForCode(String code);

}