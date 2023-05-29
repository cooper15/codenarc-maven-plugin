package com.github.actions;

import com.github.dto.Parameters;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.codenarc.CodeNarcRunner;
import org.codenarc.analyzer.FilesystemSourceAnalyzer;
import org.codenarc.report.HtmlReportWriter;
import org.codenarc.results.Results;

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
                    .map(rule -> "rulesets/" + rule.getName() + ".xml")
                    .collect(Collectors.joining(", "));

            CodeNarcRunner runner = new CodeNarcRunner();
            runner.setSourceAnalyzer(codeAnalyzer);
            runner.setRuleSetFiles(rules);

            HtmlReportWriter htmlReport = new HtmlReportWriter();
            htmlReport.setIncludeSummaryByPackage(true);
            htmlReport.setDefaultOutputFile(parameter.getReport());

            runner.setReportWriters(List.of(htmlReport));
            Results results = runner.execute();

            log.info("Checking results");

            val actualPriority1 = results.getNumberOfViolationsWithPriority(1, true);
            checkMaxViolations(actualPriority1, parameter.getMaxPriority1(), 1);

            val actualPriority2 = results.getNumberOfViolationsWithPriority(2, true);
            checkMaxViolations(actualPriority2, parameter.getMaxPriority2(), 2);

            val actualPriority3 = results.getNumberOfViolationsWithPriority(3, true);
            checkMaxViolations(actualPriority3, parameter.getMaxPriority3(), 3);
        });
    }

    private void checkMaxViolations(int actual, int expected, int priority) {
        if (actual > expected)
            failExecution("Priority" + priority + ": expecting less than" + expected + "violations, but found " + actual);
    }

    private void failExecution(String message) {
        log.error(message);
        System.exit(1);
    }
}
