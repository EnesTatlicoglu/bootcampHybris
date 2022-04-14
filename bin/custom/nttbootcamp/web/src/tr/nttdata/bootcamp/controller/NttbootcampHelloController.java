/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.controller;

import static tr.nttdata.bootcamp.constants.NttbootcampConstants.PLATFORM_LOGO_CODE;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import tr.nttdata.bootcamp.service.NttbootcampService;
import tr.nttdata.bootcamp.service.ProductBadgeService;


@Controller
public class NttbootcampHelloController
{
	@Autowired
	private ProductBadgeService productBadgeService;

	@Autowired
	private NttbootcampService nttbootcampService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model, @RequestParam String badgeCode)
	{
		model.addAttribute("logoUrl", nttbootcampService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		if(StringUtils.isNotEmpty(badgeCode)) {
			productBadgeService.deleteBadgeForCode(badgeCode);
			productBadgeService.createBadgeForCode(badgeCode);
			if (productBadgeService.getProductBadge(badgeCode) != null) {
				productBadgeService.deleteBadgeForCode(badgeCode);
			}
		}
		return "welcome";
	}
}
