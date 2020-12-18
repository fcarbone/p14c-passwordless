package com.pingidentity.oidclogin.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pingidentity.oidclogin.Consts;
import com.pingidentity.oidclogin.data.PairingKey;
import com.pingidentity.oidclogin.data.User;
import com.pingidentity.oidclogin.service.UserService;


@Controller
public class RegistrationController extends BaseController {
		
	Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	private static final String PAIRING_KEY = "pairing_key";
	private static final String USER = "user";
	
	@Value("${ping-config.population-id}")
	private String populationId;
	
	@Autowired
	UserService userService;
	
    @GetMapping("/register")
    public String showRegistrationPage(Model model, @AuthenticationPrincipal OidcUser principal) {
    	logger.debug("Starting showRegistrationPage " + principal);
    	setCommonAttributes(model, principal);
    	model.addAttribute("user", new User ());
        return "register";
    }
   
    
    @PostMapping("/register")
    public String startRegistration (Model model, @ModelAttribute @Valid User user,@AuthenticationPrincipal OidcUser principal,HttpSession session) {
    	logger.debug("Starting startRegistration " + principal);
    	setCommonAttributes(model, principal);
    	
    	// create user
    	User resultUser = userService.createUser(user,populationId);
		logger.debug("User created : " + resultUser.getId());
		
		// create pairing key
		PairingKey pairingKey = userService.createPairingKey(resultUser);
		logger.debug("Pairing key created : " + pairingKey.getCode());
		
		session.setAttribute(PAIRING_KEY, pairingKey);
		session.setAttribute(USER, resultUser);
		
		model.addAttribute("user", resultUser);
		model.addAttribute("pairingKey", pairingKey.getCode());
		
        return "pair-wait";
    }
    
    @GetMapping("/register-pairing-wait")
    public ResponseEntity<PairingKey> pairingWait (Model model, @AuthenticationPrincipal OidcUser principal,HttpSession session) {
    	logger.debug("Starting pairingWait");
    	setCommonAttributes(model, principal);
		
    	PairingKey key = (PairingKey)session.getAttribute(PAIRING_KEY);
    	User user = (User)session.getAttribute(USER);
    	logger.debug("Pairing key from session: " + key);
    	logger.debug("Pairing key from session: " + user);
    	
    	if (key != null) {
    		key = userService.getPairingKey(user,key);
			if (Consts.CLAIMED.equals(key.getStatus())) {
				logger.debug("Pairing key completed: " + key.getId());
				session.setAttribute(PAIRING_KEY, null);
				session.setAttribute(USER, null);
			} 
		}
		
        return new ResponseEntity<PairingKey>(key, HttpStatus.OK);
    }
    
}