package com.farm.milk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MilkServiceApplication

fun main(args: Array<String>) {
    runApplication<MilkServiceApplication>(*args)
} 