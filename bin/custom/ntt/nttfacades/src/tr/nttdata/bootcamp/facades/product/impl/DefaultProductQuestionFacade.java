package tr.nttdata.bootcamp.facades.product.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.core.product.parameter.ProductQuestionParameter;
import tr.nttdata.bootcamp.core.product.service.ProductQuestionService;
import tr.nttdata.bootcamp.facades.product.ProductQuestionFacade;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionCategoryData;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionData;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionParameterData;

import java.util.Collections;
import java.util.List;

public class DefaultProductQuestionFacade implements ProductQuestionFacade {

    private Logger LOG = LoggerFactory.getLogger(DefaultProductQuestionFacade.class);

    private ProductService productService;
    private ProductQuestionService productQuestionService;
    private Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter;
    private Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> productQuestionCategoryConverter;
    private Converter<ProductQuestionParameterData, ProductQuestionParameter> productQuestionParameterConverter;


    @Override
    public List<ProductQuestionData> getQuestionsForProduct(String productCode) {
        ProductModel product = null;
        try {
            product = getProductService().getProductForCode(productCode);
        }catch (Exception e){
            LOG.error("Product with code {} could not be found!", productCode);
            return Collections.emptyList();
        }

        List<ProductQuestionModel> questions = getProductQuestionService().getQuestionsForProduct(product);
        return CollectionUtils.isNotEmpty(questions) ? getProductQuestionConverter().convertAll(questions)
                : Collections.emptyList();
    }

    @Override
    public List<ProductQuestionCategoryData> getQuestionCategories() {
        List<ProductQuestionCategoryModel> category = getProductQuestionService().getQuestionCategories();
        return CollectionUtils.isNotEmpty(category) ? getProductQuestionCategoryConverter().convertAll(category)
                : Collections.emptyList();
    }

    @Override
    public ProductQuestionData askQuestion(ProductQuestionParameterData parameter) {
        ProductQuestionParameter questionParameter = getProductQuestionParameterConverter().convert(parameter);
        ProductQuestionModel result = getProductQuestionService().askQuestion(questionParameter);
        return result != null ? getProductQuestionConverter().convert(result) : null;
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

    public Converter<ProductQuestionModel, ProductQuestionData> getProductQuestionConverter() {
        return productQuestionConverter;
    }

    public void setProductQuestionConverter(Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter) {
        this.productQuestionConverter = productQuestionConverter;
    }

    public Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> getProductQuestionCategoryConverter() {
        return productQuestionCategoryConverter;
    }

    public void setProductQuestionCategoryConverter(Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> productQuestionCategoryConverter) {
        this.productQuestionCategoryConverter = productQuestionCategoryConverter;
    }

    public Converter<ProductQuestionParameterData, ProductQuestionParameter> getProductQuestionParameterConverter() {
        return productQuestionParameterConverter;
    }

    public void setProductQuestionParameterConverter(Converter<ProductQuestionParameterData, ProductQuestionParameter> productQuestionParameterConverter) {
        this.productQuestionParameterConverter = productQuestionParameterConverter;
    }
}
