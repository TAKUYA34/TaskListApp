package jp.gihyo.projava.tasklist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Spring boot @Controller:html文書をそのまま返すアノテーション
@Controller
public class HomeController {
    record TaskItem(String id, String task, String deadline, boolean done) {}
    private List<TaskItem> taskItems = new ArrayList<>();

    @GetMapping(value = "/list")
    String listItems(Model model) {
        // 注意：attributeName名と.htmlファイルの属性「${taskList}」の命名が一致していないと反映されない
        model.addAttribute("taskList", taskItems);
        return "home";
    }

    /*  登録のためのエンドポイント */
    @GetMapping("/add")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);

        // redirect:/list：表示するWebページを指定のパス(/list)にリダイレクトする
        return "redirect:/list";
    }

    @RequestMapping(value = "/hello")
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }
}

