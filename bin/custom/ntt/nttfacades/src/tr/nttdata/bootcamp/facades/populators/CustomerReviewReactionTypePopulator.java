package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.type.TypeService;
import tr.nttdata.bootcamp.core.enums.CustomerReviewReactionType;
import tr.nttdata.bootcamp.facades.product.data.CustomerReviewReactionTypeData;

public class CustomerReviewReactionTypePopulator implements Populator<CustomerReviewReactionType, CustomerReviewReactionTypeData> {

    private TypeService typeService;
    private Converter<MediaModel, ImageData> imageConverter;

    @Override
    public void populate(CustomerReviewReactionType source, CustomerReviewReactionTypeData target) throws ConversionException {
        EnumerationValueModel enumVal = getTypeService().getEnumerationValue(source);
        target.setCode(enumVal.getCode());
        target.setName(enumVal.getName());
        if (enumVal.getIcon() != null){
            target.setIcon(getImageConverter().convert(enumVal.getIcon()));
        }
        target.setSequence(enumVal.getSequenceNumber());
    }

    public TypeService getTypeService() {
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    public Converter<MediaModel, ImageData> getImageConverter() {
        return imageConverter;
    }

    public void setImageConverter(Converter<MediaModel, ImageData> imageConverter) {
        this.imageConverter = imageConverter;
    }
}
