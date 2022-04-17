/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.controller;

import static tr.nttdata.bootcamp.constants.NttbootcampConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.servicelayer.dto.converter.Converter;
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
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.NttbootcampService;
import tr.nttdata.bootcamp.service.ProductBadgeService;


@Controller
public class NttbootcampHelloController
{
	@Autowired
	@Qualifier("productBadgeConverter")
	private Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter;

	@Autowired
	private ProductBadgeService productBadgeService;

	@Autowired
	private NttbootcampService nttbootcampService;

	private static final Logger LOG = LoggerFactory.getLogger(NttbootcampHelloController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model, @RequestParam String badgeCode)
	{
		model.addAttribute("logoUrl", nttbootcampService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		if(StringUtils.isNotEmpty(badgeCode)) {
			productBadgeService.deleteBadgeForCode(badgeCode);
			ProductBadgeModel productBadgeModel = productBadgeService.createBadgeForCode(badgeCode);
			ProductBadgeData productBadgeData = productBadgeConverter.convert(productBadgeModel);
			LOG.info("Product Badge Data code: {}", productBadgeData.getCode());
		}
		return "welcome";
	}
}
