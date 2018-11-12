package com.example.twitter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MainController {


    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;


    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String homePage(Model model) {

        model.addAttribute("messages", messageRepo.findAll());

        return "index";
    }

    @RequestMapping("/register")
    public String signUp(Model model) {

        model.addAttribute("user", new User());


        return "signup";
    }

    @PostMapping("/processform")
    public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult result) {

        if (result.hasErrors()) {
            return "signup";
        }

        userService.saveUser(user);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String loginPage() {

        return "login";
    }

    @GetMapping("/logout")
    public String logout() {

        return "index";
    }

    @GetMapping("/addtext")
    public String addMessage(Model model) {

        model.addAttribute("message", new Message());
        return "addtextform";

    }

    @PostMapping("/processtext")
    public String processText(@Valid @ModelAttribute("message") Message message,@AuthenticationPrincipal User user, BindingResult result) {

        if (result.hasErrors()) {
            return "addtextform";

        }
        message.setUser(user);
        messageRepo.save(message);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") long id, Model model, @AuthenticationPrincipal User user) {

        Message message = messageRepo.findById(id).get();

        model.addAttribute("message", message);

        model.addAttribute("canUpdate", user.getId()==message.getUser().getId());


        return "detail";
    }

    @RequestMapping("/update/{id}")
    public String updateText(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepo.findById(id).get());

        return "addtextform";

    }
}
