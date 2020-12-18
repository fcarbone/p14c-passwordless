package com.pingidentity.oidclogin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pingidentity.oidclogin.service.UserService;


/**
 * Controller for the home page.
 */
@Controller
public class HomeController extends BaseController {
		
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	UserService userService;
	
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
    	logger.debug("Starting home with user " + principal);
    	setCommonAttributes(model, principal);
        return "home";
    }
   
}