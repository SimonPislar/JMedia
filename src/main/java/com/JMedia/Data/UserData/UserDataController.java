package com.JMedia.Data.UserData;

import com.JMedia.Security.PasswordHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataController {

    private final UserRepository userRepository;
    private final PasswordHandler passwordHandler;

    @Autowired
    public UserDataController(UserRepository userRepository, PasswordHandler passwordHandler) {
        this.userRepository = userRepository;
        this.passwordHandler = passwordHandler;
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
    public String userExists(String email) {
        Optional<User> userByEmail = this.userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            return "true";
        } else {
            return "false";
        }
    }
}
