package tr.nttdata.bootcamp.facades;

import tr.nttdata.bootcamp.data.BrandData;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.model.BrandModel;

import java.util.List;

public interface BrandFacade {
    List<BrandData> getAllBrands();
    BrandData getBrandForCode(String code);
}
