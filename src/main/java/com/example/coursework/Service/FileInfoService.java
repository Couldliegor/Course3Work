package com.example.coursework.Service;
public interface FileInfoService extends BaseFileService {

    boolean createFile();

    boolean saveToFile(String json);

}
