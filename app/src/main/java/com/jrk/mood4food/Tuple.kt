package com.jrk.mood4food

class Tuple<T>(override val size: Int = 2) :Set<T>{
    private lateinit var tuple : Set<T>

    override fun contains(element: T): Boolean {
        return tuple.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return tuple.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return tuple.isEmpty()
    }

    override fun iterator(): Iterator<T> {
        return tuple.iterator()
    }
}