package com.useandsell.authentication.service;

import com.useandsell.authentication.dto.Authenticate;
import com.useandsell.authentication.repository.AuthenticateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthenticateService {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Logger LOGGER =
            Logger.getLogger(AuthenticateService.class.getName());
    private final AuthenticateRepository authenticateRepository;

    @Autowired
    public AuthenticateService(AuthenticateRepository authenticateRepository) {
        this.authenticateRepository = authenticateRepository;
    }

    public static boolean validate(String emailStr)
            throws Exception {
        try {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
            return matcher.matches();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public String encryptPassword(String password)
            throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hash);

            /* Convert the digest into hex value */
            StringBuilder hexString = new StringBuilder(number.toString(16));

            /* Pad with leading zeros */
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }

            return hexString.toString();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e);
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public String registerNewCustomer(String email, String password)
            throws NoSuchAlgorithmException {
        try {
            if (validate(email)) {
                Optional<Authenticate> authenticateUser = authenticateRepository.findUserByEmail(email);
                if ((authenticateUser.isPresent())) {
                    return "Email already registered";

                } else {
                    Authenticate authenticate = new Authenticate(email, encryptPassword(password), false, true);
                    authenticateRepository.save(authenticate);
                    return email;
                }
            } else {
                LOGGER.log(Level.WARNING, "Enter correct email. ");
                return "Enter correct email. ";


            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            return e.getMessage();
        }

    }

    public String registerNewSeller(String email, String password)
            throws NoSuchAlgorithmException {
        try {
            if (validate(email)) {
                Optional<Authenticate> authenticateUser = authenticateRepository.findUserByEmail(email);
                if ((authenticateUser.isPresent())) {
                    return "Email already registered";
                } else {
                    Authenticate authenticate = new Authenticate(email, encryptPassword(password), true, true);
                    authenticateRepository.save(authenticate);
                    return email;
                }

            } else {
                LOGGER.log(Level.WARNING, "Enter correct email. ");
                return "Enter correct email. ";
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            return e.getMessage();

        }

    }

    public String loginCustomer(String email, String password)
            throws Exception {
        try {
            Optional<Authenticate> authenticateCustomer = authenticateRepository.findUserByEmail(email);


            Authenticate authenticate = authenticateRepository.findUserCredentialsByEmail(email);
            if (authenticate == null) {
                return "Email not found";
            } else {
                if (!(authenticate.isSeller())) {

                    if (encryptPassword((password)).equals(authenticate.getPassword())) {
                        authenticate.setLoggedIn(true);
                        return email;
                    } else {
                        LOGGER.log(Level.WARNING, "Enter correct password. ");
                        return "Enter correct password. ";
                    }
                } else {

                    LOGGER.log(Level.WARNING, "You are not a customer. ");
                    return "You are not a customer. ";
                }
            }


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    public String loginSeller(String email, String password)
            throws Exception {
        try {
            Optional<Authenticate> authenticateSeller = authenticateRepository.findUserByEmail(email);


            Authenticate authenticate = authenticateRepository.findUserCredentialsByEmail(email);
            if (authenticate == null) {
                return "Email not found";
            } else {
                if ((authenticate.isSeller())) {
                    if (encryptPassword(password).equals(authenticate.getPassword())) {
                        authenticate.setLoggedIn(true);
                        return email;
                    } else {
                        LOGGER.log(Level.WARNING, "Enter correct password. ");
                        return "Enter correct password. ";
                    }

                } else {
                    LOGGER.log(Level.WARNING, "You are not a seller. ");
                    return "You are not a seller. ";
                }
            }


        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exception Occurred: ", e.getMessage());
            throw new Exception(e.getMessage());

        }
    }

    public boolean isGivenEmailSeller(String email)
            throws Exception {
        try {
            return authenticateRepository.findIfUserIsSeller(email);
        } catch (Exception e) {
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public boolean isGivenEmailLoggedIn(String email)
            throws Exception {
        try {
            return authenticateRepository.findIfUserIsLoggedIn(email);
        } catch (Exception e) {
            throw new Exception("Exception Occurred: ", e);
        }
    }

    public void logoutUser(String email)
            throws Exception {
        try {
            authenticateRepository.logoutUser(email);
        } catch (Exception e) {
            throw new Exception("Exception Occurred: ", e);
        }
    }
}
