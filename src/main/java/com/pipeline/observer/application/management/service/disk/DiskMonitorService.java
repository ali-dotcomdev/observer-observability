package com.pipeline.observer.application.management.service.disk;

import com.pipeline.observer.domain.model.DiskRecord;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class DiskMonitorService {

    public DiskRecord measureDiskMetric() {

        File root = new File("/");
        long totalSpaceGb = root.getTotalSpace() / (1024*1024*1024);
        long freeSpaceGb = root.getFreeSpace() / (1024*1024*1024);
        long usedSpaceGb = totalSpaceGb - freeSpaceGb;

        DiskRecord diskRecord = new DiskRecord(totalSpaceGb, freeSpaceGb, usedSpaceGb);

        return diskRecord;
    }
}
