package com.github.actions;

import com.github.dto.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.codenarc.CodeNarcRunner;
import org.codenarc.analyzer.FilesystemSourceAnalyzer;
import org.codenarc.report.HtmlReportWriter;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Verify {
    public void code(List<Parameters> parameters) {
        parameters.forEach(parameter -> {
            log.info("Starting code analysis");
            FilesystemSourceAnalyzer codeAnalyzer = new FilesystemSourceAnalyzer();
            codeAnalyzer.setBaseDirectory(parameter.getBasedir().getAbsolutePath());
            codeAnalyzer.setIncludes(parameter.getIncludes());

            String rules = parameter.getRules().stream()
                    .map(rule -> "rulesets/" + rule + ".xml")
                    .collect(Collectors.joining(", "));

            CodeNarcRunner runner = new CodeNarcRunner();
            runner.setSourceAnalyzer(codeAnalyzer);
            runner.setRuleSetFiles(rules);

            HtmlReportWriter htmlReport = new HtmlReportWriter();
            htmlReport.setIncludeSummaryByPackage(true);
            htmlReport.setDefaultOutputFile(parameter.getReport());

            log.info("Printing code analysis report");
            runner.setReportWriters(List.of(htmlReport));
            runner.execute();
        });
    }
}
