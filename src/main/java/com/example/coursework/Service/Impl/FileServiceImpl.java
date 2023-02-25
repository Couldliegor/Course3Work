package com.example.coursework.Service.Impl;

import com.example.coursework.Service.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    @Value("${path.to.data.file}")
    private String pathToDataFile;
    @Value("${name.of.data.file}")
    private String nameOfDataFile;

    @PostConstruct
    public void init() throws IOException {
        if (!Path.of(pathToDataFile).toFile().exists()) {
            Files.createTempFile(pathToDataFile, ".json");
        }
    }

    @Override
    public Path createJsonDataFile(String json) {
        try {
            return Files.createTempFile(pathToDataFile, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean deleteJsonDataFile() {
        File file = Path.of(pathToDataFile).toFile();
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    @Override
    public boolean cleanDataFile() {
        Path path = Path.of(pathToDataFile, nameOfDataFile);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDataFile() {
        return new File(pathToDataFile + "/" + nameOfDataFile);
    }

    @Override
    public boolean checkingConstructionCreateFile(MultipartFile file) {
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
