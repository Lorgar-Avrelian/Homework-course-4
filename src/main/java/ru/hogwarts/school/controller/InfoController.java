package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.PortInfo;

@RestController
@RequestMapping("/getPort")
public class InfoController {
    @Autowired
    private PortInfo portInfo;

    @GetMapping
    public int getPortNumber() {
        return portInfo.portInfo();
    }
}
