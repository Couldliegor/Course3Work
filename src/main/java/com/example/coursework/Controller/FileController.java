package com.example.coursework.Controller;

import com.example.coursework.Service.FileInfoService;
import com.example.coursework.Service.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/socks/file")
@AllArgsConstructor
@Tag(name = "Файловый Сервис", description = "Система для операций с файлами")
public class FileController {
    private final FileService fileService;
    private final FileInfoService fileInfoService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Загрузка списка носков сразу на сервер"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл успешно загружен!",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "ОШИБКА, что то не так с файлом!",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    }
    )
    public ResponseEntity<Void> importFile(@RequestParam MultipartFile file) {
        fileService.checkingConstructionCreateFile(file);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Получение файла с сервера в формате .json"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Файл успешно получен!",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "ОШИБКА, файл где-то потерялся на сервере!",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    }
            )
    }
    )
    public ResponseEntity<InputStreamResource>exportFile() throws FileNotFoundException {
        File file = fileService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksList.json\"")// тип
                    .body(resource);
        }
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/info")
    public ResponseEntity<InputStreamResource> exportInfoFile() throws FileNotFoundException {
        File file = fileInfoService.getDataFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"SocksInfoList.json\"")// тип
                    .body(resource);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importInfoFile(@RequestParam MultipartFile file) {
        fileInfoService.checkingConstructionCreateFile(file);
        return ResponseEntity.ok().build();
    }
}
