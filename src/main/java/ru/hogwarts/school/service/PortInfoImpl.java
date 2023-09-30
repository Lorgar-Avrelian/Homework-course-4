package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PortInfoImpl implements PortInfo{
    @Value("${server.port}")
    private int port;
    @Override
    public int portInfo() {
        return port;
    }
}
