package com.pro.magistracy.tool.checker;

import com.pro.magistracy.settings.Settings;
import com.pro.magistracy.settings.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DateTimeCheckerImpl implements DateTimeChecker {

    @Override
    public boolean checkDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(startDate) && currentDateTime.isBefore(endDate);
    }
}
