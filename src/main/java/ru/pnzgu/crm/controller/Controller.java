package ru.pnzgu.crm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pnzgu.crm.dto.User;
import ru.pnzgu.crm.store.entity.Role;

import java.util.Arrays;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
@RequestMapping("")
@RequiredArgsConstructor
public class Controller {
    @GetMapping()
    public String getAuthView(Model model) {
        model.addAttribute("authList", Arrays.stream(Role.values()).map(Role::getValue).collect(Collectors.toList()));
        model.addAttribute("user", new User());
        return "auth";
    }

    @PostMapping("/auth")
    public String auth(@ModelAttribute(name = "user") User user) {

        String redirectUrl = switch (user.getRole()) {
            case "Менеджер" -> "/manager/contact";
            case "Директор" -> "/director/manager";
            default -> "";
        };

        return String.format("redirect:%s", redirectUrl);
    }
}
