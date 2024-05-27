package com.pro.magistracy.tool.checker;

import java.time.LocalDateTime;

public interface DateTimeChecker {
    boolean checkDateTime(LocalDateTime startDate, LocalDateTime endDate);
}
