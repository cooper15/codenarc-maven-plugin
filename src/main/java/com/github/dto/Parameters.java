package com.github.dto;

import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class Parameters {
    File basedir;
    String includes;
    List<Rule> rules;
    int maxPriority1;
    int maxPriority2;
    int maxPriority3;
    String report;
}
