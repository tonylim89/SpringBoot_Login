package com.example.loginapp.Controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.loginapp.Service.UserService;
import com.example.loginapp.exception.UsernameAlreadyExistsException;
import com.example.loginapp.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class UserController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    //For normal users
    @GetMapping("/register")
    public String register(Model model, HttpServletRequest request) {
        Locale currentLocale = localeResolver.resolveLocale(request);
        String nameRequiredMsg = messageSource.getMessage("user.name.required", null, currentLocale);
        String onInvalidAttrValue = nameRequiredMsg;
        model.addAttribute("nameRequiredMsg", nameRequiredMsg);
        model.addAttribute("onInvalidAttrValue", onInvalidAttrValue);
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam("username") String username, 
                            @RequestParam("password") String password,
                            @RequestParam("name") String name,
                            Model model, HttpServletRequest request) {

        Locale currentLocale = localeResolver.resolveLocale(request);

        if (username.trim().isEmpty() || password.trim().isEmpty() || name.trim().isEmpty()) {
            String errorMsg = messageSource.getMessage("user.fields.required", null, currentLocale);
            model.addAttribute("generalError", errorMsg);
            return "register";
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            String errorMsg = messageSource.getMessage("user.username.alphanumeric", null, currentLocale);
            model.addAttribute("usernameError", errorMsg);
            return "register";
        }

        User user = new User(null, username, password, name, "USER"); 
        try {
            userService.save(user);
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException e) {

            String localizedErrorMsg = messageSource.getMessage("user.username.taken", null, currentLocale);
            model.addAttribute("usernameError", localizedErrorMsg);
            
            // Add attributes to the model to populate again for better UX
            model.addAttribute("username", username);
            model.addAttribute("name", name);
            return "register"; 
        }
    }
    //For managers, one time only
    @GetMapping("/registermanager")
    public String registerManager(Model model) {
        boolean managerExists = userService.checkIfManagerExists();
        if (managerExists) {
            return "redirect:/login";  
        }
        return "registermanager";
    }

    @PostMapping("/registermanager")
    public String postRegisterManager(@RequestParam("username") String username, 
                                      @RequestParam("password") String password,
                                      @RequestParam("name") String name) {
        User manager = new User(null, username, password, name, "MANAGER");
        userService.save(manager);
        return "redirect:/login";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username).orElseThrow();
        model.addAttribute("user", user);
        return "welcome";
    }
}