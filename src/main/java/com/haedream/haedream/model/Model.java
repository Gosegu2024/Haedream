package com.haedream.haedream.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "TB_LOG")
public class Model {
    @Id
    private String logIdx;
    private String modelName;
    private String inputCode;
    private String outputCode;
    private LocalDateTime logDate;
}
