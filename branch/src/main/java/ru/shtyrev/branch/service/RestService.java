package ru.shtyrev.branch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.shtyrev.branch.dto.service.ServiceDto;

@Service
@RequiredArgsConstructor
public class RestService {
    @Value("${service.url}")
    private String serviceUrl;
    private final RestClient restClient;

    public ServiceDto findServiceById(Long serviceId) {
        return restClient.get()
                .uri(String.format("%s/%d", serviceUrl, serviceId))
                .retrieve()
                .toEntity(ServiceDto.class)
                .getBody();
    }
}
