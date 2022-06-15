package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;
import org.hsqldb.persist.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.product.parameter.ProductQuestionParameter;
import tr.nttdata.bootcamp.core.product.service.ProductQuestionService;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionParameterData;

public class ProductQuestionParameterPopulator implements Populator<ProductQuestionParameterData, ProductQuestionParameter> {

    private Logger LOG = LoggerFactory.getLogger(ProductQuestionParameterPopulator.class);

    private ProductService productService;
    private ProductQuestionService productQuestionService;


    @Override
    public void populate(ProductQuestionParameterData source, ProductQuestionParameter target) throws ConversionException {
        ProductModel product = null;
        try{
            product = getProductService().getProductForCode(source.getProduct());
        }catch (Exception e){
            LOG.error("Product with code {} could not be found!", source.getProduct());
            return;
        }

        ProductQuestionCategoryModel category = getProductQuestionService().getQuestionCategoryForCode(source.getCategory());

        target.setProduct(product);
        target.setCategory(category);
        target.setQuestion(source.getQuestion());
        target.setHideUser(source.isHideUser());
        target.setOnlyForUser(source.isOnlyForUser());
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public ProductQuestionService getProductQuestionService() {
        return productQuestionService;
    }

    public void setProductQuestionService(ProductQuestionService productQuestionService) {
        this.productQuestionService = productQuestionService;
    }
}
