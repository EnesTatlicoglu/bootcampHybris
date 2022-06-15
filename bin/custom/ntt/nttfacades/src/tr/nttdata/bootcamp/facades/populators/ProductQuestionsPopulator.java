package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.core.product.service.ProductQuestionService;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionData;

import java.util.Collections;
import java.util.List;

public class ProductQuestionsPopulator implements Populator<ProductModel, ProductData> {

    private ProductQuestionService productQuestionService;
    private Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter;

    @Override
    public void populate(ProductModel source, ProductData target) throws ConversionException {
        List<ProductQuestionModel> questions = getProductQuestionService().getQuestionsForProduct(source);
        if (CollectionUtils.isEmpty(questions)){
            target.setQuestions(Collections.emptyList());
        }else {
            target.setQuestions(getProductQuestionConverter().convertAll(questions));
        }
    }

    public Converter<ProductQuestionModel, ProductQuestionData> getProductQuestionConverter() {
        return productQuestionConverter;
    }

    public void setProductQuestionConverter(Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter) {
        this.productQuestionConverter = productQuestionConverter;
    }

    public ProductQuestionService getProductQuestionService() {
        return productQuestionService;
    }

    public void setProductQuestionService(ProductQuestionService productQuestionService) {
        this.productQuestionService = productQuestionService;
    }
}
