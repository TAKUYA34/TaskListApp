package jp.gihyo.projava.tasklist;

import jp.gihyo.projava.tasklist.HomeController.TaskItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskListDao {
    // JdbcTemplate：DBを活用するための様々な機能を提供する
    private final JdbcTemplate jdbcTemplate;

    // @Autowired：Spring BootはTaskListDaoクラスのコンストラクタを呼び出す際に引数として適切なオブジェクトを作成して渡す
    // Springが持っている仕組みをDI(Dependency Injection：依存性の注入)という
    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // SimpleJdbcInsert：テーブルへのデータ追加を行う
    // BeanPropertySqlParameterSource()：SqlParameterSourceの実装クラス。引数に設定したオブジェクトに対して、テーブルに追加したいデータを表すクラスのオブジェクトを渡す。
    // SimpleJdbcInsert().withTableName()：データを追加する対象のDB名を設定する
    public void add(TaskItem taskItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(taskItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasklist");
        insert.execute(param);
    }

    public List<TaskItem> findAll() {
        String query = "SELECT * FROM tasklist";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                                row.get("id").toString(),
                                row.get("task").toString(),
                                row.get("deadline").toString(),
                                (Boolean)row.get("done")))
                        .toList();

        return taskItems;
    }
}
