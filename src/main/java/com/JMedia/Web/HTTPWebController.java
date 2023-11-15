package com.JMedia.Web;

import com.JMedia.Communication.EmailSender;
import com.JMedia.Data.UserData.User;
import com.JMedia.Data.UserData.UserDataController;
import com.JMedia.Security.CodeGenerator;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController // This means that this class is a Controller
@RequestMapping(path="/userController") // This means URLs start with /userController (after Application path)
public class HTTPWebController {

    private final EmailSender emailSender;
    private final UserDataController userDataController;
    private final CodeGenerator codeGenerator;

    @Autowired
    public HTTPWebController(EmailSender emailSender, UserDataController userDataController, CodeGenerator codeGenerator) {
        this.emailSender = emailSender;
        this.userDataController = userDataController;
        this.codeGenerator = codeGenerator;
    }

    /*
        @Brief: This method is used to test if the server is running.
        @Return: String - Returns "pong" if the server is running.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="ping")
    public String ping() {
        return "pong";
    }

    /*
        @Brief: This method is used to check the credentials of a user.
        @Param: username - The username of the user to be checked.
        @Param: password - The password of the user to be checked.
        @Return: boolean - Returns true if the user exists in the database and the password matches.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/LoginAttempt") // This means that this method is mapped to the URL /LoginAttempt
    public boolean login(@RequestParam (value = "email", defaultValue = "") String email,
                         @RequestParam (value = "password", defaultValue = "") String password) {

        return userDataController.authorizeUser(email, password);
    }

    /*
        @Brief: This method is used to add a new user to the database.
        @Param: username - The username of the user to be added.
        @Param: password - The password of the user to be added.
        @Param: email - The email of the user to be added.
        @Param: firstName - The first name of the user to be added.
        @Param: lastName - The last name of the user to be added.
        @Return: String - Returns "Saved" if the user was successfully added to the database.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/addUser") // Map ONLY POST Requests
    public String addNewUser (  @RequestParam (value = "username") String username,
                                @RequestParam (value = "password") String password,
                                @RequestParam (value = "email") String email,
                                @RequestParam (value = "firstName") String firstName,
                                @RequestParam (value = "lastName") String lastName) {

        userDataController.addUserToDB(username, password, email, firstName, lastName);
        return "Saved";
    }

    /*
        @Brief: This method is used to delete a user from the database.
        @Param: id - The id of the user to be deleted.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/deleteUser")
    public void deleteUser(@RequestParam (value = "id") Integer id) {
        userDataController.deleteUser(id);
    }

    /*
        @Brief: This method is used to get a user from the database.
        @Param: email - The email of the user to be retrieved.
        @Return: User - Returns the user with the specified email.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/getUserByEmail")
    public User getUserByEmail(@RequestParam (value = "email") String email) {
        return userDataController.getUserByEmail(email);
    }

    /*
        @Brief: This method is used to check if a user exists in the database.
        @Param: email - The email of the user to be checked.
        @Return: String - Returns "true" if the user exists in the database.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/userExists")
    public String userExists(@RequestParam (value = "email") String email) {
        return userDataController.userExists(email);
    }

    /*
        @Brief: This method is used to send a forgot password email to a user.
        @Param: email - The email of the user to be sent the email.
        @Return: String - Returns "Sent" if the email was successfully sent.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path="/sendForgotPasswordEmail")
    public String sendForgotPasswordEmail(@RequestParam (value = "email") String email) {
        User user = userDataController.getUserByEmail(email);
        if (user == null) {
            return "User not found";
        }
        emailSender.send(email, "Forgot Password", "Your password is: " + user.getPassword());
        return "Sent";
    }

    /*
        @Brief: This method is used to send a two-factor authentication code to a user.
        @Param: email - The email of the user to be sent the code.
        @Return: String - Returns the code that was sent to the user.
    */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/set-two-factor-authentication-code")
    public String setTwoFactorAuthenticationCode(@RequestParam (value ="email") String email) {
        String code = codeGenerator.generateRandomCode(6);
        emailSender.send(email, "Two-Factor Authentication", "Your code is: " + code);
        userDataController.setTwoFactorCode(email, code);
        return "Set";
    }

    /*
        @Brief: This method is used to check if a two-factor authentication code is correct.
        @Param: email - The email of the user to be checked.
        @Param: code - The code to be checked.
        @Return: String - Returns "true" if the code is correct.
     */
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/two-factor-authentication-check")
    public String getTwoFactorAuthenticationCode(@RequestParam (value ="email") String email,
                                                 @RequestParam (value ="code") String code) {
        String generatedCode = userDataController.getTwoFactorCode(email);
        System.out.println("Comparing codes: " + generatedCode + " and " + code + "...");
        if (generatedCode.equals(code)) {
            return "true";
        } else {
            return "false";
        }
    }
}
