package com.example.musicplayer

class HistoryQueue(private val capacity: Int) {
    private val queue = arrayOfNulls<Any?>(capacity)
    private var front = -1
    private var rear = -1

    fun enqueue(item: Any) {
        if (isFull()) {
            println("Queue is full. Unable to enqueue $item")
            return
        }

        if (isEmpty()) {
            front = 0
            rear = 0
        } else {
            rear = (rear + 1) % capacity
        }

        queue[rear] = item
    }

    fun dequeue(): Any? {
        if (isEmpty()) {
            println("Queue is empty. Unable to dequeue.")
            return null
        }

        val item = queue[front]
        if (front == rear) {
            front = -1
            rear = -1
        } else {
            front = (front + 1) % capacity
        }

        return item
    }

    fun peek(): Any? {
        if (isEmpty()) {
            println("Queue is empty. Unable to peek.")
            return null
        }
        return queue[front]
    }

    fun isEmpty(): Boolean {
        return front == -1
    }

    fun isFull(): Boolean {
        return (rear + 1) % capacity == front
    }

    fun printQueue() {
        if (isEmpty()) {
            println("Queue is empty.")
            return
        }

        var index = front
        while (index != rear) {
            print("${queue[index]} ")
            index = (index + 1) % capacity
        }
        println(queue[rear])
    }
}