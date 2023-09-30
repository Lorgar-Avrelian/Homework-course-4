package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("student")
public class StudentPortInfo implements PortInfo{
    @Value("${server.port}")
    private int port;
    @Override
    public int portInfo() {
        return port;
    }
}
