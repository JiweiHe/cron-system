package com.github.bone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.bone.quartz.QuartzService;
import com.github.bone.service.JobService;


@Controller
@RequestMapping("jobDetail")
public class JobDetailController {

    @Autowired
    private QuartzService quartzService;
    @Autowired
    private JobService jobService;

    @GetMapping("list")
    public String jobDetails(@RequestParam(required = false) String jobDetailName, Model model) {
        jobService.jobMethod();
        model.addAttribute("jobDetails", quartzService.jobDetails(jobDetailName));
        return "jobDetail/list";
    }

    @PostMapping("pause")
    @ResponseBody
    public ResponseEntity<?> pauseJobDetail(@RequestParam String jobName, @RequestParam String groupName) {
        return ResponseEntity.ok(quartzService.pauseJob(jobName, groupName));
    }

    @PostMapping("resume")
    @ResponseBody
    public ResponseEntity<?> resumeJobDetail(String jobName, String groupName) {
        return ResponseEntity.ok(quartzService.resumeJob(jobName, groupName));
    }

    @PostMapping("trigger")
    @ResponseBody
    public ResponseEntity<?> triggerJobDetail(String jobName, String groupName) {
        return ResponseEntity.ok(quartzService.triggerJob(jobName, groupName));
    }
}

