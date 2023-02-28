package com.example.coursework.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllInfoSocks {
    private SocksBatch socksBatch;
    private LocalDateTime localDateTime;
    private String typeOfOperation;
}
