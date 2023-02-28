package com.example.coursework.Service.Impl;

import com.example.coursework.Repository.SocksRepository;
import com.example.coursework.Service.FileInfoService;
import com.example.coursework.Service.SockServerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileInfoServiceImpl implements FileInfoService {
    @Value("${path.of.info.data.file}")
    private String pathToInfoFile;
    @Value("${name.of.info.data.file}")
    private String nameToInfoFileFile;

    private final SockServerService sockServerService;

    private final SocksRepository socksRepository;

    public FileInfoServiceImpl(SockServerService sockServerService, SocksRepository socksRepository) {
        this.sockServerService = sockServerService;
        this.socksRepository = socksRepository;
    }

    @PostConstruct
    private void init() {
        if (!getDataFile().exists()) {
            createFile();
        }
    }

    @Override
    public boolean createFile() {
        Path path = Path.of(pathToInfoFile, nameToInfoFileFile);
        try {
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getDataFile() {
        return new File(pathToInfoFile + "/" + nameToInfoFileFile);
    }

    @Override
    public boolean saveToFile(String json) {
        try {
            Files.writeString(Path.of(pathToInfoFile, nameToInfoFileFile), json); // записываем строку в файл.
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean updateFile() {
        try {
            Path path = Path.of(pathToInfoFile, nameToInfoFileFile);
            Files.deleteIfExists(path);
            saveToFile(socksRepository.getMapInString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

//нужно также реализовать доставание из информации из файла в мапу
