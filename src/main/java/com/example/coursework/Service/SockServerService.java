package com.example.coursework.Service;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;

public interface SockServerService {
    void accept(SocksBatch socksBatch);

    int issuance(SocksBatch socksBatch);

    int reject(SocksBatch socksBatch);

    int getCount(Color color, Size size, int cottonMin, int cottonMax);

    void checkSockValidate(SocksBatch socksBatch);

    boolean saveToFile();
}
