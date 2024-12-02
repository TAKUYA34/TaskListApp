package jp.gihyo.projava.tasklist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class HomeRestController {
    // タスクの情報を保持する
    // deadline：期限
    // done：完了か否か

    record TaskItem(String id, String task, String deadline, boolean done) {}
    private List<TaskItem> taskItems = new ArrayList<>();
    @RequestMapping(value ="/resthello")
    String hello() {
        return """
                Hello.  
                It Works!
                現在時刻は%sです。
                """.formatted(LocalDateTime.now());
    }

    // @RequestParam：サーバーから文字列のデータを取得する
    // UUID.randomUUID()：32バイトの一意の値をランダムに排出する
        @GetMapping("/restadd")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id, task, deadline, false);
        taskItems.add(item);

        return "タスクを追加しました。";
    }

        // /restaddで取得したtaskItemsを整える
        @GetMapping("/restlist")
    String listItems() {
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(", "));
        return result;
    }
}
