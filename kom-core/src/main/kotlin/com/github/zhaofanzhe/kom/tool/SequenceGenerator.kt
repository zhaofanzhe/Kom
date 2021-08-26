package com.github.zhaofanzhe.kom.tool

class SequenceGenerator {
    private var sequence = 0;
    fun next(): Int {
        return sequence++
    }
}