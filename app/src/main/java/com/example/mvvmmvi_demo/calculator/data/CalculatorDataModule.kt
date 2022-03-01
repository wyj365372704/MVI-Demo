package com.example.mvvmmvi_demo.calculator.data

import com.example.mvvmmvi_demo.calculator.data.network.model.CalculateResultJson
import com.example.mvvmmvi_demo.calculator.data.network.service.CalculatorNetworkService
import com.example.mvvmmvi_demo.calculator.domain.repository.CalculateRepository
import kotlinx.coroutines.delay
import org.json.JSONObject
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.lang.Exception
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

internal val dataModule = Kodein.Module("data") {
    bind<CalculateRepository>() with singleton { CalculatorRepositoryImpl(instance()) }

    bind() from singleton {
        object : CalculatorNetworkService {
            override suspend fun calculateFromNet(input: String): CalculateResultJson {
                delay(1000)//模拟网络延时
                val result = getResult(input)
                return CalculateResultJson(input, result.toString())
            }

            @Throws(Exception::class)
            private fun doubleCal(a1: Double, a2: Double, operator: Char): Double {
                when (operator) {
                    '+' -> return a1 + a2
                    '-' -> return a1 - a2
                    '*' -> return a1 * a2
                    '/' -> return a1 / a2
                    else -> {
                    }
                }
                throw Exception("illegal operator!")
            }

            @Throws(Exception::class)
            private fun getPriority(s: String?): Int {
                if (s == null) return 0
                when (s) {
                    "(" -> return 1
                    "+" -> {
                        return 2
                    }
                    "-" -> return 2
                    "*" -> {
                        return 3
                    }
                    "/" -> return 3
                    else -> {
                    }
                }
                throw Exception("illegal operator!")
            }

            @Throws(Exception::class)
            private fun getResult(expr: String): Double {
                println("计算$expr")
                /*数字栈*/
                val number: Stack<Double> = Stack<Double>()
                /*符号栈*/
                val operator: Stack<String> = Stack<String>()
                operator.push(null) // 在栈顶压人一个null，配合它的优先级，目的是减少下面程序的判断

                /* 将expr打散为运算数和运算符 */
                val p: Pattern =
                    Pattern.compile("(?<!\\d)-?\\d+(\\.\\d+)?|[+\\-*/()]") // 这个正则为匹配表达式中的数字或运算符
                val m: Matcher = p.matcher(expr)
                while (m.find()) {
                    val temp: String = m.group()
                    if (temp.matches(Regex("[+\\-*/()]"))) { //遇到符号
                        if (temp == "(") { //遇到左括号，直接入符号栈
                            operator.push(temp)
                            println("符号栈更新：$operator")
                        } else if (temp == ")") { //遇到右括号，"符号栈弹栈取栈顶符号b，数字栈弹栈取栈顶数字a1，数字栈弹栈取栈顶数字a2，计算a2 b a1 ,将结果压入数字栈"，重复引号步骤至取栈顶为左括号，将左括号弹出
                            var b: String? = null
                            while (operator.pop().also { b = it } != "(") {
                                println("符号栈更新：$operator")
                                val a1: Double = number.pop()
                                val a2: Double = number.pop()
                                println("数字栈更新：$number")
                                println("计算$a2$b$a1")
                                number.push(doubleCal(a2, a1, b!![0]))
                                println("数字栈更新：$number")
                            }
                            println("符号栈更新：$operator")
                        } else { //遇到运算符，满足该运算符的优先级大于栈顶元素的优先级压栈；否则计算后压栈
                            while (getPriority(temp) <= getPriority(operator.peek())) {
                                val a1: Double = number.pop()
                                val a2: Double = number.pop()
                                val b: String = operator.pop()
                                println("符号栈更新：$operator")
                                println("数字栈更新：$number")
                                println("计算$a2$b$a1")
                                number.push(doubleCal(a2, a1, b[0]))
                                println("数字栈更新：$number")
                            }
                            operator.push(temp)
                            println("符号栈更新：$operator")
                        }
                    } else { //遇到数字，直接压入数字栈
                        number.push(java.lang.Double.valueOf(temp))
                        println("数字栈更新：$number")
                    }
                }
                while (operator.peek() != null) { //遍历结束后，符号栈数字栈依次弹栈计算，并将结果压入数字栈
                    val a1: Double = number.pop()
                    val a2: Double = number.pop()
                    val b: String = operator.pop()
                    println("符号栈更新：$operator")
                    println("数字栈更新：$number")
                    println("计算$a2$b$a1")
                    number.push(doubleCal(a2, a1, b[0]))
                    println("数字栈更新：$number")
                }
                return number.pop()
            }
        }
    }
}