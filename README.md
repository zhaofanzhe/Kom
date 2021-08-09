# Kom - 一个 kotlin 的 ORM 框架

中文 | [English (Google translation)](./README.en.md)

## 说明
目前的进度只是一个玩具,有兴趣的人可以加入进来

不定时更新

有任何问题可以通过 [Issues](https://github.com/zhaofanzhe/Kom/issues) 联系我

# 计划表

* [ ] DSL
    * [X] 查询语句
    * [X] 插入语句
    * [X] 更新语句
    * [X] 删除语句
* [ ] 模型
    * [ ] 查询
    * [ ] 创建
    * [ ] 保存
    * [ ] 删除

### 例子

[查询语句 | Query.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/Query.kt)

[插入语句 | Insert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/Insert.kt)

[更新语句 | Update.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/Update.kt)

[删除语句 statement | Insert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/Delete.kt)

### 数据库支持

* [X] Mariadb [已测试|Mariadb10.5.9]
* [X] MySQL [未测试,应该支持]
* [ ] PostgreSQL [未测试，应该不支持]
* [ ] Oracle [未测试]
* [ ] 其他... [等待补充]