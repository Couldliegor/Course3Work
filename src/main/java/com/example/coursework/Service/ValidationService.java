package com.example.coursework.Service;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;

public interface ValidationService {
    boolean validate(SocksBatch socksBatch);

    boolean validate(Color color, Size size, int cottonMin, int cottonMax);
    boolean checkCotton(int cottonMin, int cottonMax);

    void checkJsonFile();
}
