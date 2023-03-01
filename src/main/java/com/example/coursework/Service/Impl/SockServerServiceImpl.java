package com.example.coursework.Service.Impl;

import com.example.coursework.Exception.ValidationException;
import com.example.coursework.Model.*;
import com.example.coursework.Repository.SocksRepository;
import com.example.coursework.Service.SockServerService;
import com.example.coursework.Service.ValidationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@Service
public class SockServerServiceImpl implements SockServerService {
    private final SocksRepository socksRepository;
    private final FileInfoServiceImpl fileInfoService;
    private final FileServiceImpl fileService;
    private final ValidationService validationService;
    private final ObjectMapper objectMapper;

    @Override
    public void accept(SocksBatch socksBatch) throws JsonProcessingException {
        checkSockValidate(socksBatch);
        socksRepository.save(socksBatch);
        AllInfoSocks allInfoSocks = new AllInfoSocks(socksBatch, LocalDateTime.now(), TypeOfOperation.accept);
        fileInfoService.saveToFile(objectMapper.writeValueAsString(allInfoSocks));
        fileService.saveToFile(socksRepository.getMapInString());
    }

    @Override
    public int issuance(SocksBatch socksBatch) throws JsonProcessingException {
        checkSockValidate(socksBatch);
        AllInfoSocks allInfoSocks = new AllInfoSocks(socksBatch, LocalDateTime.now(), TypeOfOperation.issuance);
        fileInfoService.saveToFile(objectMapper.writeValueAsString(allInfoSocks));
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int reject(SocksBatch socksBatch) throws JsonProcessingException {
        checkSockValidate(socksBatch);
        AllInfoSocks allInfoSocks = new AllInfoSocks(socksBatch, LocalDateTime.now(), TypeOfOperation.reject);
        fileInfoService.saveToFile(objectMapper.writeValueAsString(allInfoSocks));
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
                socks.getCottonPart() <= cottonMax) {
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
}
