package ru.nechaev.sprint8.ReportDemoApp;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReportController {
    @GetMapping("/check")
    public String check() {
        System.out.println("Hello my dear frontend how are yoy?");
        return "Hello prothetic_user take your report: report ";
    }

    @GetMapping("/reports")
    public String getReports() {
        System.out.println("Hello my dear frontend how are yoy?");
        return "Hello prothetic_user take your report: report ";
    }
}
