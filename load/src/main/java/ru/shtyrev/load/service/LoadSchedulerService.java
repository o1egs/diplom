package ru.shtyrev.load.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.shtyrev.load.dto.branch.BranchDto;
import ru.shtyrev.load.dto.branch.UpdateBranchDto;
import ru.shtyrev.load.entity.Load;
import ru.shtyrev.load.repository.LoadRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoadSchedulerService {
    private final RestService restService;
    private final LoadService loadService;

//    @Scheduled(cron = "0 */5 8-18 * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
    public void checkLoad() {
        BranchDto[] branchDtos = restService.findAllBranches();

        for (BranchDto branchDto : branchDtos) {
            Long branchId = branchDto.getId();

            Integer currentLoad = restService.getCurrentLoad(branchId);

            LocalDateTime now = LocalDateTime.now();

            log.info("Branch {} with load {}", branchId, currentLoad);

            loadService.saveLoad(
                    branchId,
                    currentLoad,
                    now.toLocalDate(),
                    now.toLocalTime()
            );
        }

    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void updateAverageLoad() {
        BranchDto[] branchDtos = restService.findAllBranches();

        for (BranchDto branchDto : branchDtos) {
            Long branchId = branchDto.getId();

            int averageLoad = (int) loadService.getMonthLoadList(branchId).stream()
                    .mapToInt(Load::getLoad)
                    .average()
                    .orElse(0);

            log.info("Branch {} with avg load {}", branchId, averageLoad);

            UpdateBranchDto updateBranchDto = UpdateBranchDto.builder()
                    .averageLoad(averageLoad)
                    .build();

            restService.updateBranchById(branchId, updateBranchDto);
        }
    }

}
