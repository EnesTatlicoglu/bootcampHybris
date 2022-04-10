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
import java.util.Optional;

import de.hybris.platform.servicelayer.user.UserConstants;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import org.springframework.beans.factory.annotation.Value;
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

		final String mime = mimeService.getMimeFromFirstBytes(mediaService.getDataFromMedia(media));
		LOG.info("Mime of {} is {}", logoCode, mime);

		// Keep in mind that with Slf4j you don't need to check if debug is enabled, it is done under the hood.
		LOG.debug("Found media [code: {}]", media.getCode());

		LOG.info("Configuration property by Spring annotation {}", propertyByAnnotation);
		LOG.info("Configuration property by Spring XML configuration {}", propertyByXML);

		LOG.info("Configuration property by Hybris configuration (initial) {}", initialPropertyByConfig);
		LOG.info("Configuration property by Hybris configuration (initial, with default) {}", initialPropertyByConfigWithDefaultValue);

		Long runtimePropertyByConfig = Config.getLong("nttdata.property.with.config.runtime", 0);
		LOG.info("Configuration property by Hybris configuration (runtime) {}", runtimePropertyByConfig);
		LOG.info("******************************************");
		if(userService.getUserForUID(UserConstants.ANONYMOUS_CUSTOMER_UID) != null){
			LOG.info("Found anonymous user");
		}
		LOG.info("******************************************");

		return media.getURL();
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
