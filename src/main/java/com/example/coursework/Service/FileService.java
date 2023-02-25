package com.example.coursework.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface FileService {
    Path createJsonDataFile(String json);

    boolean deleteJsonDataFile();

    boolean cleanDataFile();

    File getDataFile();

    boolean checkingConstructionCreateFile(MultipartFile file);

}
