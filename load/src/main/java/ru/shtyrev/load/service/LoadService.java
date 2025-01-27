package ru.shtyrev.load.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.shtyrev.load.dto.Data;
import ru.shtyrev.load.dto.LoadPredictionRequestDto;
import ru.shtyrev.load.dto.LoadPredictionResponseDto;
import ru.shtyrev.load.dto.LoadTime;
import ru.shtyrev.load.entity.Load;
import ru.shtyrev.load.repository.LoadRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadService {
    private final RestService restService;

    private final LoadRepository loadRepository;

    public void saveLoad(Long branchId, Integer value, LocalDate date, LocalTime time) {
        Load load = Load.builder()
                .branchId(branchId)
                .date(date)
                .time(time)
                .load(value)
                .build();

        loadRepository.save(load);
    }

    public List<Load> getMonthLoadList(Long branchId) {
        return loadRepository.findByBranchIdAndDateGreaterThan(
                branchId,
                LocalDate.now().minusMonths(1)
        );
    }

    public LoadPredictionResponseDto getPrediction(Long branchId, LocalDate date) {
        List<Data> data = new ArrayList<>();

        getMonthLoadList(branchId).stream()
                .collect(Collectors.groupingBy(Load::getDate))
                .forEach((date_, loads) -> {

                    List<LoadTime> loadTimes = new ArrayList<>();

                    loads.stream()
                            .collect(Collectors.groupingBy(load -> load.getTime().getHour()))
                            .forEach((time, hourLoads) -> {
                                int averageLoadByHour = (int) hourLoads.stream()
                                        .mapToInt(Load::getLoad)
                                        .average()
                                        .orElse(0d);

                                loadTimes.add(
                                        LoadTime.builder()
                                                .time(time.toString())
                                                .load(averageLoadByHour)
                                                .build()
                                );
                            });

                    data.add(
                            Data.builder()
                                    .date(date_)
                                    .load(loadTimes)
                                    .build()
                    );

                });

        LoadPredictionRequestDto loadPredictionRequestDto = LoadPredictionRequestDto.builder()
                .predictDate(date)
                .data(data)
                .build();

        return restService.getPrediction(loadPredictionRequestDto);
    }

}
