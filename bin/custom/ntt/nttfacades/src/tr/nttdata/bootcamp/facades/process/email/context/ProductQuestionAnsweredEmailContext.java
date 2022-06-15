/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.store.BaseStoreModel;
import org.springframework.beans.factory.annotation.Required;
import tr.nttdata.bootcamp.core.jalo.ProductQuestion;
import tr.nttdata.bootcamp.core.model.ProductQuestionAnsweredProcessModel;
import tr.nttdata.bootcamp.core.model.ProductQuestionModel;
import tr.nttdata.bootcamp.facades.product.data.ProductQuestionData;


/**
 * Velocity context for a customer email.
 */
public class ProductQuestionAnsweredEmailContext extends AbstractEmailContext<ProductQuestionAnsweredProcessModel>
{
	private ProductQuestionData productQuestionData;
	private Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter;
	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;

	@Override
	public void init(final ProductQuestionAnsweredProcessModel process, final EmailPageModel emailPageModel)
	{
		super.init(process, emailPageModel);
		customerData = getCustomerConverter().convert(getCustomer(process));
		productQuestionData = getProductQuestionConverter().convert(process.getQuestion());
	}

	@Override
	protected BaseSiteModel getSite(final ProductQuestionAnsweredProcessModel process)
	{
		return process.getQuestion().getSite();
	}

	@Override
	protected CustomerModel getCustomer(final ProductQuestionAnsweredProcessModel process)
	{
		UserModel user = process.getQuestion().getUser();
		return user instanceof CustomerModel ? (CustomerModel) user : null;
	}

	@Override
	protected LanguageModel getEmailLanguage(final ProductQuestionAnsweredProcessModel process)
	{
		return process.getQuestion().getLanguage();
	}


	public CustomerData getCustomer()
	{
		return customerData;
	}

	public ProductQuestionData getProductQuestion() {
		return productQuestionData;
	}

	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public Converter<ProductQuestionModel, ProductQuestionData> getProductQuestionConverter() {
		return productQuestionConverter;
	}

	public void setProductQuestionConverter(Converter<ProductQuestionModel, ProductQuestionData> productQuestionConverter) {
		this.productQuestionConverter = productQuestionConverter;
	}
}
