package com.example.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailModel {
    private String from;
    private String to;
    private String name;
    private Map<String, String> content;
    private String bcc;
    private String cc;
    private String subject;
    private String template;
}
