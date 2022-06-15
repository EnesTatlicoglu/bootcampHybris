package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionCategoryData;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionData;

public class ProductQuestionPopulator implements Populator<ProductQuestionModel, ProductQuestionData> {

    private Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> productQuestionCategoryConverter;
    private Converter<CustomerModel, CustomerData> customerConverter;

    @Override
    public void populate(ProductQuestionModel source, ProductQuestionData target) throws ConversionException {
        target.setAnswer(source.getAnswer());
        target.setQuestion(source.getQuestion());
        target.setHideUser(source.isHideUser());
        target.setOnlyForUser(source.isOnlyForUser());

        ProductQuestionCategoryModel category = source.getCategory();
        if (category != null){
            target.setCategory(getProductQuestionCategoryConverter().convert(category));
        }

        UserModel user = source.getUser();
        if (user instanceof CustomerModel){
            target.setUser(getCustomerConverter().convert((CustomerModel) user));
        }
    }

    public Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> getProductQuestionCategoryConverter() {
        return productQuestionCategoryConverter;
    }

    public void setProductQuestionCategoryConverter(Converter<ProductQuestionCategoryModel, ProductQuestionCategoryData> productQuestionCategoryConverter) {
        this.productQuestionCategoryConverter = productQuestionCategoryConverter;
    }

    public Converter<CustomerModel, CustomerData> getCustomerConverter() {
        return customerConverter;
    }

    public void setCustomerConverter(Converter<CustomerModel, CustomerData> customerConverter) {
        this.customerConverter = customerConverter;
    }
}
