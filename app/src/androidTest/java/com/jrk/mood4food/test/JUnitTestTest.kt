package com.jrk.mood4food.test

import junit.framework.Assert.assertEquals
import org.junit.Test

class JUnitTestTest {
    @Test fun helloWorldReturnsPersonalizedMessage() {
        assertEquals("Hello, Molly!", helloWorld("Molly"))
    }

    fun helloWorld(name: String = "World"): String {
        return "Hello, ${name}!"
    }
}