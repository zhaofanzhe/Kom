# Kom - 一个 kotlin 的 ORM 框架

中文 | [English (Google translation)](./README.en.md)

## 说明
目前的进度只是一个玩具,有兴趣的人可以加入进来

不定时更新

有任何问题可以通过 [Issues](https://github.com/zhaofanzhe/Kom/issues) 联系我

# 计划表

* [X] DSL
    * [X] 查询语句
    * [X] 插入语句
    * [X] 更新语句
    * [X] 删除语句
* [X] 模型
    * [X] 查询
    * [X] 创建
    * [X] 保存
    * [X] 删除

### 例子

[查询语句 | DSLQuery.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLQuery.kt)

[插入语句 | DSLInsert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLInsert.kt)

[更新语句 | DSLUpdate.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLUpdate.kt)

[删除语句 | DSLInsert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLDelete.kt)

[模型查询 | ModelQuery.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelQuery.kt)

[模型创建 | ModelCreate.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelCreate.kt)

[模型创建 | ModelSave.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelSave.kt)

[模型删除 | ModelDelete.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelDelete.kt)

### 数据库支持

* [X] Mariadb [已测试|Mariadb10.5.9]
* [X] MySQL [未测试,应该支持]
* [ ] PostgreSQL [未测试，应该不支持]
* [ ] Oracle [未测试]
* [ ] 其他... [等待补充]