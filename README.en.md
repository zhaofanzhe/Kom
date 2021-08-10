# Kom - An ORM framework of kotlin

[中文](./README.md) | English (Google translation)

## Explain
The current progress is just a toy. Interested people can join in.

Update from time to time.

If you have any questions, please contact me via [Issues](https://github.com/zhaofanzhe/Kom/issues)

# Task

* [X] DSL
  * [X] Query statement
  * [X] Insert statement
  * [X] Update statement
  * [X] Delete statement
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

[Query statement | DSLQuery.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLQuery.kt)

[Insert statement | DSLInsert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLInsert.kt)

[Update statement | DSLUpdate.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLUpdate.kt)

[Delete statement | DSLInsert.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/DSLDelete.kt)

[model query | ModelCreate.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelQuery.kt)

[model create | ModelCreate.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelCreate.kt)

[model save | ModelSave.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelSave.kt)

[model delete | ModelDelete.kt](./src/test/kotlin/io/github/zhaofanzhe/kom/ModelDelete.kt)

### Database support

* [X] Mariadb [Tested|Mariadb10.5.9]
* [X] MySQL [Not tested, should be supported]
* [ ] PostgreSQL [Not tested, should not be supported]
* [ ] Oracle [Not tested]
* [ ] Other... [Waiting to add]