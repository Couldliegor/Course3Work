package com.example.coursework.Repository;

import com.example.coursework.Model.Socks;
import com.example.coursework.Model.SocksBatch;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface SocksRepository {
    String getMapInString() throws JsonProcessingException;

    Map<Socks, Integer> getAll();

    void save(SocksBatch socksBatch);

    int remove(SocksBatch socksBatch);

}
