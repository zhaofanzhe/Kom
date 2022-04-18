# Kom - An ORM framework of kotlin

[中文](./README.md) | English (Google translation)

[![Release](https://jitpack.io/v/zhaofanzhe/Kom.svg)](https://jitpack.io/#zhaofanzhe/Kom)

## Explain
Single table CRUD, custom DSL for data query.

If you have any questions, please contact me via [Issues](https://github.com/zhaofanzhe/Kom/issues)

# Task

* [X] DSL
  * [X] DML
    * [X] Query statement
    * [X] Insert statement
    * [X] Update statement
    * [X] Delete statement
  * [ ] DDL
    * [X] Create Table statement
    * [X] Drop Table statement
* [X] Model
  * [X] Query
  * [X] Create
  * [X] Save
  * [X] Delete
* [ ] express
  * [X] and [ and ] [ and ]
  * [X] or [ or ] [ or ]
  * [X] equal [ = ] [ eq ]
  * [X] not equal to [ != ] [ ne ]
  * [X] more than the [ > ] [ gt ]
  * [X] greater or equal to [ >= ] [ geo ]
  * [X] Less than [ < ] [ lt ]
  * [X] Less than or equal to [ <= ] [ loe ]
  * [ ] other...

### Example

[Query statement | DSLQuery.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLQuery.kt)

[Insert statement | DSLInsert.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLInsert.kt)

[Update statement | DSLUpdate.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLUpdate.kt)

[Delete statement | DSLInsert.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/DSLDelete.kt)

[Create Table statement | CreateTable.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/support/mysql/CreateTable.kt)

[Drop Table statement| DropTable.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/support/mysql/DropTable.kt)

[model query | ModelCreate.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelQuery.kt)

[model create | ModelCreate.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelCreate.kt)

[model save | ModelSave.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelSave.kt)

[model delete | ModelDelete.kt](./kom-example/src/test/kotlin/com/github/zhaofanzhe/kom/ModelDelete.kt)

### Database support

* [X] Mariadb [Tested|Mariadb10.5.9]
* [X] MySQL [Not tested, should be supported]
* [X] PostgreSQL [Basic tested.]
* [ ] Other... [Waiting to add]