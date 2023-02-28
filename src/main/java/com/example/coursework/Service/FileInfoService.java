package com.example.coursework.Service;

import java.io.File;

public interface FileInfoService {

    boolean createFile();

    File getDataFile();

    boolean saveToFile(String json);
}
