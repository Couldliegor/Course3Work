package com.example.coursework.Controller;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;
import com.example.coursework.Service.SockServerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
@Tag(name = "API для учета носков", description = "REST сервисы для взаимодействия с импровизированной БД")
@RequiredArgsConstructor
public class SocksStoreController {
    private final SockServerService sockServerService;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Регистрирует приход товара на склад", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта")
    @ApiResponse(responseCode = "200", description = "удалось добавить приход")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<String> addSocksBatchRegistrar(@RequestBody SocksBatch body) { //приход
        try {
            sockServerService.accept(body);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok("Носки были успешно добавлены!");
    }

    @PutMapping("/")
    @Operation(summary = "Регистрирует отпуск носков со склада", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта ")
    @ApiResponse(responseCode = "200", description = "удалось произвести отпуск носков со склада")
    @ApiResponse(responseCode = "400", description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<String> putOutSocksRegistrar(@RequestBody SocksBatch body) {
        int quantity = 0;
        try {
            quantity = sockServerService.issuance(body);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(quantity + "Носков были отправлены со склада");
    }


    @GetMapping("/")
    @Operation(summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<String> getAllSocksInVaultByParam(@RequestParam Color color, @RequestParam Size size, @RequestParam int cottonMin, @RequestParam int cottonMax) {
        int quantity = sockServerService.getCount(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(quantity + "Носков получено по заданным параметрам");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, товар списан со склада")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<String> spoiledSocksRegistrator(@RequestBody SocksBatch socksBatch) {
        int quantity = 0;
        try {
            quantity = sockServerService.reject(socksBatch);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(quantity + "Носков списано");
    }
    // Добавить LocalDateTime.
    //Также реализуйте возможность сохранять операции приемки и выдачи носков в памяти и выгружать их в виде JSON-файла и обратно – загружать данные в приложение из JSON-файла. Для каждой операции нужно сохранять
}
