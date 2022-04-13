/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.media.services.MimeService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.io.InputStream;
import java.util.*;

import de.hybris.platform.servicelayer.user.UserConstants;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import org.springframework.beans.factory.annotation.Value;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.NttbootcampService;


public class DefaultNttbootcampService implements NttbootcampService
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultNttbootcampService.class);

	private MediaService mediaService;
	private ModelService modelService;
	private FlexibleSearchService flexibleSearchService;

	@Value("${nttdata.property.with.spring.annotation}")
	private String propertyByAnnotation;

	private Integer propertyByXML;
	public void setPropertyByXML(Integer propertyByXML) {
		this.propertyByXML = propertyByXML;
	}

	private String initialPropertyByConfig = Config.getParameter("nttdata.property.with.config.initial");
	private Boolean initialPropertyByConfigWithDefaultValue = Config.getBoolean("nttdata.property.with.config.initial.with.default", false);

	private MimeService mimeService;
	public void setMimeService(MimeService mimeService) {
		this.mimeService = mimeService;
	}

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Override
	public String getHybrisLogoUrl(final String logoCode)
	{
		final MediaModel media = mediaService.getMedia(logoCode);

		// Keep in mind that with Slf4j you don't need to check if debug is enabled, it is done under the hood.
		LOG.debug("Found media [code: {}]", media.getCode());

		searchForBadgeModels();

		LOG.info("***************************************");
		searchForBadgeCodes();

		LOG.info("***************************************");
		searchForMultipleColumns();

		return media.getURL();
	}

	private void searchForBadgeModels(){
		LOG.info("Searching for ProductBadge Models");
		FlexibleSearchQuery fsq = new FlexibleSearchQuery("SELECT {PK} FROM {ProductBadge} WHERE {status} = ?status");
		fsq.addQueryParameter("status", BadgeStatus.ACTIVE);
		List<ProductBadgeModel> result = flexibleSearchService.<ProductBadgeModel>search(fsq).getResult();
		if(CollectionUtils.isNotEmpty(result)){
			result.forEach(r -> LOG.info("Code: {}", r.getCode()));
		}
	}
	private void searchForBadgeCodes(){
		LOG.info("Searching for ProductBadge codes");
		FlexibleSearchQuery fsq = new FlexibleSearchQuery("SELECT {code} FROM {ProductBadge} WHERE {status} = ?status");
		fsq.addQueryParameter("status", BadgeStatus.ACTIVE);
		fsq.setResultClassList(Collections.singletonList(String.class));
		List<String> result = flexibleSearchService.<String>search(fsq).getResult();
		if(CollectionUtils.isNotEmpty(result)){
			result.forEach(r -> LOG.info("Code: {}", r));
		}
	}

	private void searchForMultipleColumns(){
		LOG.info("Searching for multiple columns");
		FlexibleSearchQuery fsq = new FlexibleSearchQuery("SELECT {code}, {title}, {creationtime} FROM {ProductBadge} WHERE {status} = ?status");
		fsq.addQueryParameter("status", BadgeStatus.ACTIVE);
		fsq.setResultClassList(Arrays.asList(String.class, String.class, Date.class));
		List<List<Object>> result = flexibleSearchService.<List<Object>>search(fsq).getResult();

		if(CollectionUtils.isNotEmpty(result)){
			result.forEach(r -> LOG.info("Code: {}, Title: {}, Creation Time: {}", r.get(0), r.get(1), r.get(2)));
		}
	}

	@Override
	public void createLogo(final String logoCode)
	{
		final Optional<CatalogUnawareMediaModel> existingLogo = findExistingLogo(logoCode);

		final CatalogUnawareMediaModel media = existingLogo.isPresent() ? existingLogo.get()
				: modelService.create(CatalogUnawareMediaModel.class);
		media.setCode(logoCode);
		media.setRealFileName("sap-hybris-platform.png");
		modelService.save(media);

		mediaService.setStreamForMedia(media, getImageStream());
	}

	private final static String FIND_LOGO_QUERY = "SELECT {" + CatalogUnawareMediaModel.PK + "} FROM {"
			+ CatalogUnawareMediaModel._TYPECODE + "} WHERE {" + CatalogUnawareMediaModel.CODE + "}=?code";

	private Optional<CatalogUnawareMediaModel> findExistingLogo(final String logoCode)
	{
		final FlexibleSearchQuery fQuery = new FlexibleSearchQuery(FIND_LOGO_QUERY);
		fQuery.addQueryParameter("code", logoCode);

		try
		{
			return Optional.of(flexibleSearchService.searchUnique(fQuery));
		}
		catch (final SystemException e)
		{
			return Optional.empty();
		}
	}

	private InputStream getImageStream()
	{
		return DefaultNttbootcampService.class.getResourceAsStream("/nttbootcamp/sap-hybris-platform.png");
	}

	@Required
	public void setMediaService(final MediaService mediaService)
	{
		this.mediaService = mediaService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}
}
