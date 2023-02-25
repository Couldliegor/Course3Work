package com.example.coursework.Service.Impl;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;
import com.example.coursework.Service.ValidationService;
import org.springframework.stereotype.Service;

@Service //нужен для проверки корректности данных
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean validate(SocksBatch socksBatch) {
        return socksBatch.getSocks() != null &&
               socksBatch.getQuantity() > 0 &&
               socksBatch.getSocks().getColor() != null &&
               socksBatch.getSocks().getSize() != null &&
               checkCotton(socksBatch.getSocks().getCottonPart(), socksBatch.getSocks().getCottonPart());
    }

    @Override
    public boolean validate(Color color, Size size, int cottonMin, int cottonMax) {
        return color != null && size != null && checkCotton(cottonMin, cottonMax);
    }

    @Override
    public boolean checkCotton(int cottonMin, int cottonMax) {
        return cottonMin >= 0 && cottonMin <= 100 && cottonMax >= 0 && cottonMax <= 100;
    }

    @Override
    public void checkJsonFile() {

    }
}
