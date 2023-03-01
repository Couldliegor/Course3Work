package com.example.coursework.Repository;
import com.example.coursework.Model.Socks;
import com.example.coursework.Model.SocksBatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
@AllArgsConstructor
public class SockRepositoryImpl implements SocksRepository {
    private final Map<Socks, Integer> socksMap = new LinkedHashMap<>();
    private final ObjectMapper mapper;
    @Override
    public void save(SocksBatch socksBatch) {
        Socks socks = socksBatch.getSocks();
        if (!socksMap.containsKey(socks)) {
            socksMap.put(socks, socksBatch.getQuantity());
        } else {
            socksMap.replace(socks, socksMap.get(socks) + socksBatch.getQuantity());
        }
    }

    @Override
    public String getMapInString() throws JsonProcessingException {
        String json = mapper.writeValueAsString(socksMap);
        return json;
    }
    @Override
    public Map<Socks, Integer> getAll() {
        return socksMap;
    }

    @Override
    public int remove(SocksBatch socksBatch) {
        Socks socks = socksBatch.getSocks();
        if (socksMap.containsKey(socks)) {
            int quantity = socksMap.get(socks);
            if (quantity > socksBatch.getQuantity()) {
                socksMap.replace(socks, quantity - socksBatch.getQuantity());
                return socksBatch.getQuantity(); // сколько выдали
            } else {
                socksMap.remove(socks);
                return quantity;
            }
        }
        return 0;
    }
}
