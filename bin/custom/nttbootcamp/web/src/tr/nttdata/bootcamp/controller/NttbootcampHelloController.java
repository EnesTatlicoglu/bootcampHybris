/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.controller;

import static tr.nttdata.bootcamp.constants.NttbootcampConstants.PLATFORM_LOGO_CODE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import tr.nttdata.bootcamp.service.NttbootcampService;


@Controller
public class NttbootcampHelloController
{
	@Autowired
	private NttbootcampService nttbootcampService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(final ModelMap model)
	{
		model.addAttribute("logoUrl", nttbootcampService.getHybrisLogoUrl(PLATFORM_LOGO_CODE));
		return "welcome";
	}
}
