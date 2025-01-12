package jp.gihyo.projava.tasklist;

import org.springframework.beans.factory.annotation.Autowired;
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

    // コンストラクタ追加／初期化
    private final TaskListDao dao;

    @Autowired
    HomeController(TaskListDao dao) {
        this.dao = dao;
    }

    // TaskListDaoクラスのfindAll()メソッド呼び出し、DBから情報を取得する
    @GetMapping(value = "/list")
    String listItems(Model model) {
        List<TaskItem> taskItems = dao.findAll();
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
        dao.add(item); // ListオブジェクトからDBに記録するよう変更する

        // redirect:/list：表示するWebページを指定のパス(/list)にリダイレクトする
        return "redirect:/list";
    }

    @GetMapping("/delete")
    String deleteItem(@RequestParam("id") String id) {
        dao.delete(id);
        return "redirect:/list";
    }

    @RequestMapping(value = "/hello")
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }

    @GetMapping("/update")
    String updateItem(@RequestParam("id") String id,
                      @RequestParam("task")String task,
                      @RequestParam("deadline")String deadline,
                      @RequestParam("done")boolean done) {
        TaskItem taskItem = new TaskItem(id, task, deadline, done);
        dao.update(taskItem);
        return "redirect:/list";
    }
}

