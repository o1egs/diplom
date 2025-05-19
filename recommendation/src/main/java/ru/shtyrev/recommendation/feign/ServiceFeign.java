package ru.shtyrev.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.shtyrev.recommendation.dto.ServiceDto;

@FeignClient(
        name = "serviceFeign",
        url = "http://localhost:8082",
        path = "/service"
)
public interface ServiceFeign {

    @GetMapping("/{serviceId}")
    ServiceDto findById(@PathVariable Long serviceId);

}
