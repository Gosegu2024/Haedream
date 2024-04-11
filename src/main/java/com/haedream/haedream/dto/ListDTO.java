package com.haedream.haedream.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListDTO {
    private String projectName;
    private String modelName;
    private LocalDateTime evalLogDate;
    private String logId;

}
