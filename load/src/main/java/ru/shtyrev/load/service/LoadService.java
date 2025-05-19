package ru.shtyrev.load.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shtyrev.load.dto.*;
import ru.shtyrev.load.entity.Load;
import ru.shtyrev.load.repository.LoadRepository;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadService implements ApplicationContextAware {
    private ApplicationContext applicationContext;
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

    @Transactional
    public DayLoadDto findDayWithMaxLoad(Long branchId, Month month, Integer year) {
        Map<LocalDate, List<Load>> dateListMap = applicationContext.getBean(this.getClass()).getMonthLoadList(branchId, month, year).stream()
                .collect(Collectors.groupingBy(Load::getDate));

        long maxLoad = 0L;
        LocalDate maxDate = null;

        for (LocalDate date : dateListMap.keySet()) {
            long avgLoad = dateListMap.get(date).stream()
                    .mapToInt(Load::getLoad)
                    .sum();

            if (avgLoad > maxLoad) {
                maxLoad = Math.round(avgLoad);
                maxDate = date;
            }
        }

        return DayLoadDto.builder()
                .date(maxDate)
                .load(maxLoad)
                .build();
    }

    @Transactional
    public DayLoadDto findDayWithMinLoad(Long branchId, Month month, Integer year) {
        Map<LocalDate, List<Load>> dateListMap = applicationContext.getBean(this.getClass()).getMonthLoadList(branchId, month, year).stream()
                .collect(Collectors.groupingBy(Load::getDate));

        Long maxLoad = null;
        LocalDate maxDate = null;

        for (LocalDate date : dateListMap.keySet()) {
            long avgLoad = dateListMap.get(date).stream()
                    .mapToInt(Load::getLoad)
                    .sum();

            if (maxLoad == null || avgLoad < maxLoad) {
                maxLoad = (long) Math.round(avgLoad);
                maxDate = date;
            }
        }

        if (maxLoad == null && maxDate == null) {
            return DayLoadDto.builder()
                    .date(null)
                    .load(0L)
                    .build();
        }

        return DayLoadDto.builder()
                .date(maxDate)
                .load(maxLoad)
                .build();
    }

    public Long getMonthVisitsCount(Long branchId, Month month, Integer year) {
        return loadRepository.countByIdAndDateBetween(
                branchId,
                LocalDate.of(year, month.getValue(), 1),
                LocalDate.of(year, month.getValue(), month.length(Year.isLeap(year)))
        );
    }

    public List<Load> getMonthLoadList(Long branchId, Month month, Integer year) {
        return loadRepository.findByBranchIdAndDateBetween(
                branchId,
                LocalDate.of(year, month.getValue(), 1),
                LocalDate.of(year, month.getValue(), month.length(Year.isLeap(year)))
        );
    }

    public List<Load> getMonthLoadListAvg(Long branchId, Month month, Integer year) {
        Map<LocalDate, List<Load>> dateListMap = applicationContext.getBean(this.getClass()).getMonthLoadList(branchId, month, year).stream()
                .collect(Collectors.groupingBy(Load::getDate));

        List<Load> loads = new ArrayList<>();

        for (LocalDate date : dateListMap.keySet()) {
            Double avgLoad = dateListMap.get(date).stream()
                    .mapToInt(Load::getLoad)
                    .average()
                    .orElse(0);

            loads.add(
                    Load.builder()
                            .date(date)
                            .load(avgLoad.intValue())
                            .build()
            );
        }

        return loads.stream()
                .sorted(Comparator.comparing(Load::getDate))
                .toList();
    }

    public List<Load> getLastMonthLoadList(Long branchId) {
        return loadRepository.findByBranchIdAndDateGreaterThan(
                branchId,
                LocalDate.now().minusMonths(6)
        );
    }

    public LoadPredictionResponseDto getPrediction(Long branchId, LocalDate date) {
        List<Data> data = new ArrayList<>();

        getLastMonthLoadList(branchId).stream()
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

    public String getBestTime(LoadPredictionResponseDto predict) {
        return predict.getPredictedLoad().stream()
                .min(Comparator.comparing(LoadTime::getLoad))
                .orElse(new LoadTime()).getTime();
    }

    public String getBestTimeByMonth(Long branchId, Month month, Integer year) {
        List<Load> monthLoadList = getMonthLoadList(branchId, month, year);

        LocalTime time = monthLoadList.stream()
                .min(Comparator.comparing(Load::getLoad))
                .map(Load::getTime)
                .orElse(null);

        if (time != null) {
            return time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null;

//        LocalDate start = LocalDate.of(year, month, 1);
//
//        LocalDate end = LocalDate.now();
//
//        List<LoadPredictionResponseDto> predicts = start.datesUntil(end)
//                .map(date -> getPrediction(branchId, date))
//                .toList();
//
//        return predicts.stream()
//                .min(Comparator.comparing(this::getBestTime))
//                .map(this::getBestTime)
//                .orElse(null);
    }

    public String getWorstTimeByMonth(Long branchId, Month month, Integer year) {
        List<Load> monthLoadList = getMonthLoadList(branchId, month, year);

        LocalTime time = monthLoadList.stream()
                .max(Comparator.comparing(Load::getLoad))
                .map(Load::getTime)
                .orElse(null);

        if (time != null) {
            return time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null;

//        LocalDate start = LocalDate.of(year, month, 1);
//
//        LocalDate end = LocalDate.now();
//
//        List<LoadPredictionResponseDto> predicts = start.datesUntil(end)
//                .map(date -> getPrediction(branchId, date))
//                .toList();
//
//        return predicts.stream()
//                .max(Comparator.comparing(this::getBestTime))
//                .map(this::getBestTime)
//                .orElse(null);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
