
-- IF NOT EXISTS：初回の起動時のみ実行する条件処理
CREATE TABLE IF NOT EXISTS tasklist (
    id VARCHAR(8)  PRIMARY KEY,
    task VARCHAR(256),
    deadline VARCHAR(10),
    done BOOLEAN
    );