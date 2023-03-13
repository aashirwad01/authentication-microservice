package com.useandsell.authentication.controller;

import com.useandsell.authentication.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthenticateController {
    private static final Logger LOGGER =
            Logger.getLogger(AuthenticateController.class.getName());
    private final AuthenticateService authenticateService;

    @Autowired
    public AuthenticateController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @RequestMapping(path = "api/customer/addUser")
    @PostMapping
    public String registerNewUserCustomer(
            @RequestBody Map<String, String> map
    ) throws Exception {
        try {
            var responseVal = authenticateService.registerNewCustomer(map.get("email"),
                    map.get("password"));
            return responseVal;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);

        }
    }

    @RequestMapping(path = "api/seller/addUser")
    @PostMapping
    public String registerNewUserSeller(
            @RequestBody Map<String, String> map
    ) throws Exception {
        try {
            var responseVal = authenticateService.registerNewSeller(map.get("email"),
                    map.get("password"));
            return responseVal;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);
        }
    }


    @RequestMapping(path = "api/customer/loginUser")
    @PostMapping
    public String loginUserCustomer(
            @RequestBody Map<String, String> map
    ) throws Exception {
        try {
            return authenticateService.loginCustomer(
                    map.get("email"),
                    map.get("password"));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            return e.getMessage();
        }
    }


    @RequestMapping(path = "api/seller/loginUser")
    @PostMapping
    public String loginUserSeller(
            @RequestBody Map<String, String> map
    ) throws Exception {
        try {
            return authenticateService.loginSeller(
                    map.get("email"),
                    map.get("password"));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Already registered as customer. Use different email ", e.getMessage());
            return e.getMessage();

        }
    }


    @RequestMapping(path = "api/isSeller/{email}")
    @GetMapping
    public boolean isGivenUserSeller(
            @PathVariable("email") String email
    ) throws Exception {
        try {
            return authenticateService.isGivenEmailSeller(email);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);
        }
    }

    @RequestMapping(path = "api/isLoggedIn")
    @GetMapping
    public boolean isGivenUserLoggedIn(
            @RequestParam("email") String email
    ) throws Exception {
        try {
            return authenticateService.isGivenEmailLoggedIn(email);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);
        }
    }

    @RequestMapping(path = "api/logout")
    @PutMapping
    public ResponseEntity<String> logoutUser(
            @RequestParam("email") String email
    ) throws Exception {
        try {
            authenticateService.logoutUser(email);
            return new ResponseEntity<>("User Successfully Logged Out.", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred:", e);
        }
    }


}
