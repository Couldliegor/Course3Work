package com.example.coursework.Controller;

import com.example.coursework.Model.Color;
import com.example.coursework.Model.Size;
import com.example.coursework.Model.SocksBatch;
import com.example.coursework.Service.SockServerService;
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
    public ResponseEntity<Object> addSocksBatchRegistrar(@RequestBody SocksBatch body) { //приход
        sockServerService.accept(body);
        return ResponseEntity.ok("Носки были успешно добавлены!");
    }

    @PutMapping("/")
    @Operation(summary = "Регистрирует отпуск носков со склада", description = "Параметры запроса передаются в теле запроса в виде JSON-объекта ")
    @ApiResponse(responseCode = "200", description = "удалось произвести отпуск носков со склада")
    @ApiResponse(responseCode = "400", description = "товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<Object> putOutSocksRegistrar(@RequestBody SocksBatch body) {
        int quantity = sockServerService.issuance(body);
        return ResponseEntity.ok(quantity + "Носков были отправлены со склада");
    }


    @GetMapping("/")
    @Operation(summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<Object> getAllSocksInVaultByParam(@RequestParam(required = false) Color color, @RequestParam(required = false) Size size, @RequestParam(required = false) int cottonMin, @RequestParam(required = false) int cottonMax) {
        int quantity = sockServerService.getCount(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(quantity + "Носков получено по заданным параметрам");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса")
    @ApiResponse(responseCode = "200", description = "запрос выполнен, товар списан со склада")
    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат")
    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны")
    public ResponseEntity<Object> spoiledSocksRegistrator(@RequestBody SocksBatch socksBatch) {
        int quantity = sockServerService.reject(socksBatch);
        return ResponseEntity.ok(quantity + "Носков списано");
    }
 //  добавить LocalDateTime
    //Также реализуйте возможность сохранять операции приемки и выдачи носков в памяти и выгружать их в виде JSON-файла и обратно – загружать данные в приложение из JSON-файла. Для каждой операции нужно сохранять
}
