/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.controller;

import static tr.nttdata.bootcamp.constants.NttbootcampConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.data.ProductData;
import tr.nttdata.bootcamp.facades.ProductBadgeFacade;
import tr.nttdata.bootcamp.facades.ProductFacade;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.NttbootcampService;
import tr.nttdata.bootcamp.service.ProductBadgeService;

import java.util.List;


@Controller
public class NttbootcampHelloController
{
	@Autowired
	private ProductBadgeFacade productBadgeFacade;

	@Autowired
	private ProductFacade productFacade;

	@Autowired
	private NttbootcampService nttbootcampService;

	private static final Logger LOG = LoggerFactory.getLogger(NttbootcampHelloController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model, @RequestParam String badgeCode)
	{
		model.addAttribute("logoUrl", nttbootcampService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		if(StringUtils.isNotEmpty(badgeCode)) {
			productBadgeFacade.deleteProductBadge(badgeCode);
			ProductBadgeData productBadgeData = productBadgeFacade.createProductBadgeForCode(badgeCode);
			LOG.info("Product Badge Data code: {}", productBadgeData.getCode());
			List<ProductData> products = productFacade.getProducts();
			if(CollectionUtils.isNotEmpty(products)){
				products.forEach(p -> LOG.info("Product {} ({})", p.getName(), p.getCode()));
			}
		}
		return "welcome";
	}
}
