package com.example.shi_02.mycalculator; /**
 * Created by Michael on 2015/5/17.
 */
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
public class Calculator {

    //优先级的Map
    private Map<String, Integer> priority;

    public Calculator() {
        initPriority();
    }

    //初始化优先级
    private void initPriority() {
        this.priority = new HashMap<>();

        this.priority.put("#", 0);
        this.priority.put("+", 1);
        this.priority.put("-", 1);
        this.priority.put("*", 2);
        this.priority.put("/", 2);
    }
    //根据操作符得到其优先级
    public int getPriority(String operator) {
        if (operator.matches("[()]")) {
            return -1;
        } else {
            return priority.get(operator);
        }
    }
    //对比两个操作符的优先级
    private boolean isPrior(String one, String another) {
        return getPriority(one) <= getPriority(another);
    }
    //得到栈顶的元素
    private <T> T getTopEle(Stack<T> stack) {
        if (stack == null) {
            return null;
        } else {
            return stack.get(stack.size() - 1);
        }
    }
    //构造表达式队列
    public Queue<String> toSuffix(String expression) {
        Queue<String> operandQueue = new LinkedList<>();
        Stack<String> operatorStack = new Stack<>();
        operatorStack.push("#");
        String current = "";
        String operator = "";
        String number = "";
        int start = 0;
        int end = 0;
        for (int i = 0; i < expression.length(); i++) {
            current = String.valueOf(expression.charAt(i));
            if (current.matches("[\\d\\.]")) {
                if (i == expression.length() - 1) {
                    operandQueue.add(current);
                } else {
                    end++;
                }
            } else {
                if (current.equals("(")) {
                    operatorStack.push(current);
                } else {
                    number = expression.substring(start, end);
                    if (!number.isEmpty()) {
                        operandQueue.add(number);
                    }
                    if (current.equals(")")) {
                        while (!getTopEle(operatorStack).equals("(")) {
                            operandQueue.add(operatorStack.pop());
                        }
                        operatorStack.pop();
                    } else {
                        operator = current;
                        while (isPrior(operator, getTopEle(operatorStack))) {
                            operandQueue.add(operatorStack.pop());
                        }
                        operatorStack.push(operator);
                    }
                }
                start = end = i + 1;
            }
        }
        for (int i = operatorStack.size() - 1; i > 0; i--) {
            operandQueue.add(operatorStack.pop());
        }
        return operandQueue;
    }
    //计算该表达式的结果
    public double getResult(String expression) {
        Queue<String> suffixQueue = toSuffix(expression);
        Stack<String> suffixStack = new Stack<String>();
        String current = "";
        double frontOperand;
        double backOperand;
        double value = 0;
        for (int i = suffixQueue.size(); i > 0; i--) {
            current = suffixQueue.poll();
            if (current.matches("^\\d+(\\.\\d+)*$")) {
                suffixStack.push(current);
            } else {
                backOperand = Double.valueOf(suffixStack.pop());
                frontOperand = Double.valueOf(suffixStack.pop());
                if (current.equals("+")) {
                    value = frontOperand + backOperand;
                } else if (current.equals("-")) {
                    value = frontOperand - backOperand;
                } else if (current.equals("*")) {
                    value = frontOperand * backOperand;
                } else if (current.equals("/")) {
                    try {
                        value = frontOperand / backOperand;
                    } catch (Exception e) {
                        return 0;
                    }
                }else {
                    return 0.0;
                }
                suffixStack.push(String.valueOf(value));
            }
        }
        String result = suffixStack.get(0);
        return Double.valueOf(result);
    }

}
