/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.catalog.model.CatalogUnawareMediaModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.enumeration.EnumerationValueModel;
import de.hybris.platform.core.model.link.LinkModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.media.services.MimeService;
import de.hybris.platform.search.restriction.SearchRestrictionService;
import de.hybris.platform.servicelayer.exceptions.SystemException;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.io.InputStream;
import java.util.*;

import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
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
import tr.nttdata.bootcamp.dao.ProductBadgeDao;
import tr.nttdata.bootcamp.enums.BadgeGroup;
import tr.nttdata.bootcamp.enums.BadgeStatus;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.NttbootcampService;


public class DefaultNttbootcampService implements NttbootcampService
{
	private static final Logger LOG = LoggerFactory.getLogger(DefaultNttbootcampService.class);

	private MediaService mediaService;
	private ModelService modelService;
	private FlexibleSearchService flexibleSearchService;

	private ProductBadgeDao productBadgeDao;

	@Override
	public String getHybrisLogoUrl(final String logoCode)
	{
		final MediaModel media = mediaService.getMedia(logoCode);

		// Keep in mind that with Slf4j you don't need to check if debug is enabled, it is done under the hood.
		LOG.debug("Found media [code: {}]", media.getCode());

		testBadgeSearch();

		return media.getURL();
	}

	private void testBadgeSearch(){
		List<ProductBadgeModel> badges = productBadgeDao.findActiveBadges();
		String leastModifiedBadge = null;
		if(CollectionUtils.isNotEmpty(badges)){
			LOG.info("Printing active badges ordered by ");
			for(int i = 0; i < badges.size(); i++){
				ProductBadgeModel badge = badges.get(i);
				LOG.info("Badge: {}, Status: {}, StartDate: {}, EndDate: {}, ModifiedTime: {}",
						badge.getCode(), badge.getStatus(), badge.getStartDate(), badge.getEndDate(), badge.getModifiedtime());
				if(i == badges.size() - 1){
					leastModifiedBadge = badge.getCode();
				}
			}

			ProductBadgeModel productBadge = productBadgeDao.findBadgeForCode(leastModifiedBadge);
			if(productBadge != null){
				productBadge.setModifiedtime(new Date());
				modelService.save(productBadge);
			}
		}

		List<ProductBadgeModel> badgesAfterModification = productBadgeDao.findActiveBadges();
		if(CollectionUtils.isNotEmpty(badgesAfterModification)){
			LOG.info("Printing badges after modification");
			badgesAfterModification.forEach(badge -> {
				LOG.info("Badge: {}, Status: {}, StartDate: {}, EndDate: {}, ModifiedTime: {}",
						badge.getCode(), badge.getStatus(), badge.getStartDate(), badge.getEndDate(), badge.getModifiedtime());

			});
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

	public void setProductBadgeDao(ProductBadgeDao productBadgeDao) {
		this.productBadgeDao = productBadgeDao;
	}
}
