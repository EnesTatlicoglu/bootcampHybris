package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.core.model.ProductQuestionCategoryModel;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionCategoryData;

public class ProductQuestionCategoryPopulator implements Populator<ProductQuestionCategoryModel, ProductQuestionCategoryData> {

    @Override
    public void populate(ProductQuestionCategoryModel source, ProductQuestionCategoryData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setName(source.getName());
    }
}
