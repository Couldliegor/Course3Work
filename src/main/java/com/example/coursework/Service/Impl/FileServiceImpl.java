package com.example.coursework.Service.Impl;

import com.example.coursework.Service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
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
    public boolean saveToFile(String json) {
        try {
            Files.writeString(Path.of(pathToDataFile, nameOfDataFile), json); // записываем строку в файл.
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
