package io.github.zhaofanzhe.kom.express

import io.github.zhaofanzhe.kom.express.declare.Declare

class Runtime(
    tables: List<ITable<*>>,
    declares: List<Declare<*>>,
) {

    val tableRuntime: MutableMap<ITable<*>, ITable<*>> = mutableMapOf()

    val declareRuntime: MutableMap<Declare<*>, Declare<*>> = mutableMapOf()

    init {
        setTableRuntime(tables)
        setDeclareRuntime(declares)
    }

    /**
     * 设置table运行环境
     */
    private fun setTableRuntime(tables: List<ITable<*>>) {
        tables.forEach { root ->
            this.tableRuntime[root] = root
            root.refs()?.forEach {
                setTableRuntime(root, it)
            }
        }
    }

    /**
     * 设置table运行环境
     */
    private fun setTableRuntime(root: ITable<*>, table: ITable<*>) {
        tableRuntime[table] = root
        table.refs()?.forEach {
            setTableRuntime(root, it)
        }
    }

    /**
     * 设置declare运行环境
     */
    private fun setDeclareRuntime(declares: List<Declare<*>>) {
        declares.forEach { setDeclareRuntime(it) }
    }

    /**
     * 设置declare运行环境
     */
    private fun setDeclareRuntime(root: Declare<*>) {
        var current: Declare<*>? = root
        while (current != null) {
            declareRuntime[current] = root
            current = current.ref
        }
    }

}