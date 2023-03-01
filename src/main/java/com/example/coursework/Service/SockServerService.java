package com.example.coursework.Service;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface SockServerService {
    void accept(SocksBatch socksBatch) throws JsonProcessingException;

    int issuance(SocksBatch socksBatch) throws JsonProcessingException;

    int reject(SocksBatch socksBatch) throws JsonProcessingException;

    int getCount(Color color, Size size, int cottonMin, int cottonMax);

    void checkSockValidate(SocksBatch socksBatch);
}
