package com.JMedia.Data.UserData;

import com.JMedia.Security.PasswordHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Optional;

@Service
public class UserDataController {

    private final UserRepository userRepository;
    private final PasswordHandler passwordHandler;
    private final TwoFactorCodesRepository twoFactorCodesRepository;
    private final SessionTokenRepository sessionTokenRepository;

    @Autowired
    public UserDataController(UserRepository userRepository,
                              PasswordHandler passwordHandler,
                              TwoFactorCodesRepository twoFactorCodesRepository,
                              SessionTokenRepository sessionTokenRepository) {
        this.userRepository = userRepository;
        this.passwordHandler = passwordHandler;
        this.twoFactorCodesRepository = twoFactorCodesRepository;
        this.sessionTokenRepository = sessionTokenRepository;
    }

    /*
        @Brief: This method is used to add a user to the database.
        @Param: username - The username of the user.
        @Param: password - The password of the user.
        @Param: email - The email of the user.
        @Param: firstName - The first name of the user.
        @Param: lastName - The last name of the user.
        @Return: void - Returns nothing.
    */
    public void addUserToDB(String username, String password, String email, String firstName, String lastName) {
        User user = new User();
        String encryptedPassword = passwordHandler.encrypt(password);
        user.setUsername(username);
        user.setPassword(encryptedPassword);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        this.userRepository.save(user);
    }

    /*
        @Brief: This method is used to check the credentials of a user.
        @Param: email - The email of the user to be checked.
        @Param: password - Given password to be checked.
        @Return: boolean - Returns true if the user exists in the database and the password matches.
    */
    public Boolean authorizeUser(String email, String password) {
        Optional<User> lookUpResult = this.userRepository.findByEmail(email);
        if (lookUpResult.isPresent()) {
            User user = lookUpResult.get();
            return passwordHandler.comparePasswords(password, user.getPassword());
        }
        return false;
    }

    /*
        @Brief: This method is used to delete a user from the database.
        @Param: id - The id of the user to be deleted.
        @Return: void - Returns nothing.
    */
    public void deleteUser(Integer id) {
        Optional<User> user = this.userRepository.findById(id);
        user.ifPresent(this.userRepository::delete);
    }

    /*
        @Brief: This method is used to get a user from the database.
        @Param: email - The email of the user to be retrieved.
        @Return: User - Returns the user if it exists in the database.
    */
    public User getUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return user.orElse(null);
    }

    /*
        @Brief: This method is used to check if a user exists in the database.
        @Param: email - The email of the user to be checked.
        @Return: String - Returns "true" if the user exists in the database.
    */
    public Boolean userExists(String email) {
        Optional<User> userByEmail = this.userRepository.findByEmail(email);
        return userByEmail.isPresent();
    }

    /*
        @Brief: This method is used to set a two-factor authentication code for a user.
        @Param: email - The email of the user to be set the code.
        @Param: code - The code to be set.
        @Return: void - Returns nothing.
     */
    public void setTwoFactorCode(String email, String code) {
        Optional<User> user = this.userRepository.findByEmail(email);
        System.out.println("Found user with email: " + email);
        if (user.isPresent()) {
            LocalTime currentTime = LocalTime.now();
            String currentTimeString = currentTime.toString();
            System.out.println("Current time: " + currentTimeString);
            TwoFactorCodes twoFactorCodes = new TwoFactorCodes();
            twoFactorCodes.setEmail(email);
            twoFactorCodes.setCode(code);
            twoFactorCodes.setSetTime(currentTimeString);
            this.twoFactorCodesRepository.save(twoFactorCodes);
        }
    }

    /*
        @Brief: This method is used to get a two-factor authentication code for a user.
        @Param: email - The email of the user whose code is to be retrieved.
        @Return: String - Returns the code.
     */
    public String getTwoFactorCode(String email) {
        Optional<TwoFactorCodes> twoFactorCodes = this.twoFactorCodesRepository.findByEmail(email);
        if (twoFactorCodes.isPresent()) {
            TwoFactorCodes twoFactorCodesObject = twoFactorCodes.get();
            String setTime = twoFactorCodesObject.getSetTime();
            LocalTime setTimeObject = LocalTime.parse(setTime);
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(setTimeObject.plusMinutes(10))) {
                this.twoFactorCodesRepository.delete(twoFactorCodesObject);
                return "";
            }
            return twoFactorCodesObject.getCode();
        } else {
            return "";
        }
    }

    /*
        @Brief: This method is used to delete a two-factor authentication code for a user.
        @Param: email - The email of the user to be deleted the code.
        @Return: void - Returns nothing.
     */
    public void deleteTwoFactorCode(String email) {
        Optional<TwoFactorCodes> twoFactorCodes = this.twoFactorCodesRepository.findByEmail(email);
        twoFactorCodes.ifPresent(this.twoFactorCodesRepository::delete);
    }

    /*
        @Brief: This method is used to set a session token for a user.
        @Param: email - The email of the user to be set the session token.
        @Param: sessionToken - The session token to be set.
        @Return: void - Returns nothing.
     */
    public void setSessionToken(String email, String sessionToken) {
        Optional<SessionToken> sessionTokenOptional = this.sessionTokenRepository.findByEmail(email);
        SessionToken sessionTokenObject;
        if (sessionTokenOptional.isPresent()) {
            sessionTokenObject = sessionTokenOptional.get();
        } else {
            sessionTokenObject = new SessionToken();
            sessionTokenObject.setEmail(email);
        }
        sessionTokenObject.setToken(sessionToken);
        this.sessionTokenRepository.save(sessionTokenObject);
    }

    /*
        @Brief: This method is used to get a session token for a user.
        @Param: email - The email of the user whose session token is to be retrieved.
        @Return: String - Returns the session token.
     */
    public String getSessionToken(String email) {
        Optional<SessionToken> sessionTokenOptional = this.sessionTokenRepository.findByEmail(email);
        if (sessionTokenOptional.isPresent()) {
            return sessionTokenOptional.get().getToken();
        } else {
            return "";
        }
    }

    /*
        @Brief: This method is used to delete a session token for a user.
        @Param: email - The email of the user to be deleted the session token.
        @Return: void - Returns nothing.
     */
    public void deleteSessionToken(String email) {
        Optional<SessionToken> sessionTokenOptional = this.sessionTokenRepository.findByEmail(email);
        sessionTokenOptional.ifPresent(this.sessionTokenRepository::delete);
    }

}
