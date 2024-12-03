package jp.gihyo.projava.tasklist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

// Spring boot @Controller:html文書をそのまま返すアノテーション
@Controller
public class HomeController {
    @RequestMapping(value = "/hello")
    @ResponseBody // 戻り値のStringオブジェクト自体がレスポンス本体として扱われる
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }
}
