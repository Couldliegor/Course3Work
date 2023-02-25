package com.example.coursework.Service.Impl;

import com.example.coursework.Exception.ValidationException;
import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.Socks;
import com.example.coursework.Model.SocksBatch;
import com.example.coursework.Repository.SocksRepository;
import com.example.coursework.Service.SockServerService;
import com.example.coursework.Service.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class SockServerServiceImpl implements SockServerService {
    private final SocksRepository socksRepository;
    private final FileInfoServiceImpl fileInfoService;
    private final ValidationService validationService;

    @Override
    public void accept(SocksBatch socksBatch) {
        checkSockValidate(socksBatch);
        socksRepository.save(socksBatch);
        saveToFile();
    }

    @Override
    public int issuance(SocksBatch socksBatch) {
        checkSockValidate(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int reject(SocksBatch socksBatch) {
        checkSockValidate(socksBatch);
        fileInfoService.updateFile();
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int getCount(Color color, Size size, int cottonMin, int cottonMax) {
        if (!validationService.validate(color, size, cottonMin, cottonMax)) {
            throw new ValidationException();
        }
        Map<Socks, Integer> map = socksRepository.getAll();
        for (Map.Entry<Socks, Integer> socksIntegerEntry : map.entrySet()) {
            Socks socks = socksIntegerEntry.getKey();
            if (socks.getColor().equals(color) &&
                socks.getColor().equals(size) &&
                socks.getCottonPart() >= cottonMin ||
                socks.getCottonPart() <= cottonMax){
                return socksIntegerEntry.getValue();
            }
        }

        return 0;
    }

    @Override
    public void checkSockValidate(SocksBatch socksBatch) {
        if (!validationService.validate(socksBatch)) {
            throw new ValidationException();
        }
    }

    @Override
    public boolean saveToFile() {
        try {
            fileInfoService.saveToFile(socksRepository.getMapInString());
            return true;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
