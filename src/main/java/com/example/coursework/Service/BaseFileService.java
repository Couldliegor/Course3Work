package com.example.coursework.Service;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface BaseFileService {
    boolean cleanDataFile();
    File getDataFile();
    default boolean checkingConstructionCreateFile(MultipartFile file) {
        if (!StringUtils.contains(file.getContentType(), "json") || file.getName().isEmpty() || file.getName().isBlank() ) {
            return false;
        }
        cleanDataFile();
        File dataFile = getDataFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
