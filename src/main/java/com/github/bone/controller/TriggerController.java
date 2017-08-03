package com.github.bone.controller;

import java.util.List;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.github.bone.quartz.QuartzService;


/**
 * Created by mike on 16/12/29.
 */
@Controller
@RequestMapping("trigger")
public class TriggerController {

    @Autowired
    private QuartzService quartzService;

    @GetMapping("list")
    public String triggers(@RequestParam(required = false) String triggerName, @RequestParam(required = false) String jobName,
                           @RequestParam(required = false) String jobGroup, Model model) {
        List triggers = quartzService.triggers(triggerName, jobName, jobGroup);
        model.addAttribute("triggers", triggers);
        return "trigger/list";
    }

    @PostMapping("pause")
    @ResponseBody
    public ResponseEntity<?> pauseTrigger(@RequestParam String triggerName, @RequestParam String groupName) {
        return ResponseEntity.ok(quartzService.pauseTrigger(triggerName, groupName));
    }

    @PostMapping("remove")
    @ResponseBody
    public ResponseEntity<?> removeTrigger(@RequestParam String triggerName, @RequestParam String groupName) {
        return ResponseEntity.ok(quartzService.removeTrigger(triggerName, groupName));
    }

    @PostMapping("resume")
    @ResponseBody
    public ResponseEntity<?> resumeTrigger(@RequestParam String triggerName, @RequestParam String groupName) {
        return ResponseEntity.ok(quartzService.resumeTrigger(triggerName, groupName));
    }

    @PostMapping("edit")
    @ResponseBody
    public ResponseEntity<?> edit(@RequestParam String triggerName, @RequestParam String triggerGroup, @RequestParam(required = false) String jobName,
                                  @RequestParam(required = false) String jobGroup, @RequestParam String cron) {
        if (!CronExpression.isValidExpression(cron)) {
            return ResponseEntity.ok("invalid");
        }
        return ResponseEntity.ok(quartzService.update(jobName, jobGroup, triggerName, triggerGroup, cron));
    }

    @GetMapping("logs")
    public String logs(Model model) {
        model.addAttribute("logs", quartzService.logs());
        return "trigger/logs";
    }
}
