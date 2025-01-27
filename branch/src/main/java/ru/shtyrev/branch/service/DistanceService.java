package ru.shtyrev.branch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shtyrev.branch.entity.Branch;
import ru.shtyrev.branch.entity.Coordinate;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class DistanceService {
    public Double getDistance(Branch branch, Coordinate coordinate) {
        return new Random().nextDouble(100, 500);
    }
}
