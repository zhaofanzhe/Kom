# Kom - 一个 kotlin 的 ORM 框架

中文 | [English (Google translation)](./README.en.md)

[![Release](https://jitpack.io/v/zhaofanzhe/Kom.svg)](https://jitpack.io/#zhaofanzhe/Kom)

## 说明

单表CRUD,自定义DSL进行数据查询

有任何问题可以通过 [Issues](https://github.com/zhaofanzhe/Kom/issues) 联系我

# 计划表

* [X] DSL
    * [X] DML
        * [X] 查询语句
        * [X] 插入语句
        * [X] 更新语句
        * [X] 删除语句
    * [ ] DDL
        * [X] 创建表语句
        * [X] 删除表语句
* [X] 模型
    * [X] 查询
    * [X] 创建
    * [X] 保存
    * [X] 删除
* [ ] 表达式
    * [X] 并列 [ and ] [ and ]
    * [X] 或者 [ or ] [ or ]
    * [X] 等于 [ = ] [ eq ]
    * [X] 不等于 [ != ] [ ne ]
    * [X] 大于 [ > ] [ gt ]
    * [X] 大于等于 [ >= ] [ geo ]
    * [X] 小于 [ < ] [ lt ]
    * [X] 小于等于 [ <= ] [ loe ]
    * [ ] 其它...

### 例子

[查询语句 | DSLQuery.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLQuery.kt)

[插入语句 | DSLInsert.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLInsert.kt)

[更新语句 | DSLUpdate.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLUpdate.kt)

[删除语句 | DSLInsert.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLDelete.kt)

[创建表语句 | CreateTable.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/support/mysql/CreateTable.kt)

[删除表语句 | DropTable.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/support/mysql/DropTable.kt)

[模型查询 | ModelQuery.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelQuery.kt)

[模型创建 | ModelCreate.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelCreate.kt)

[模型创建 | ModelSave.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelSave.kt)

[模型删除 | ModelDelete.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelDelete.kt)

### 数据库支持

* [X] Mariadb [已测试|Mariadb10.5.9]
* [X] MySQL [未测试,应该支持]
* [X] PostgreSQL [简单测试]
* [ ] 其他... [等待补充]