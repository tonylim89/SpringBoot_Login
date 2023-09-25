package com.example.loginapp.Controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/restricted")
    public String restricted() {
        return "restricted";
    }

    @RequestMapping("/lang")
    public String setLocale(HttpServletRequest request, @RequestParam("lang") String lang, HttpServletResponse response) {
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if ("zh".equals(lang)) {
            localeResolver.setLocale(request, response, new Locale("zh", "CN"));
        } else {
            localeResolver.setLocale(request, response, Locale.ENGLISH);
        }
        String referer = request.getHeader("referer");
        return referer != null ? "redirect:" + referer : "redirect:/";
    }
}