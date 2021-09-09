package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*

import org.junit.Test

class StatisticsUtilsTest {

    //If there's no completed task and one active task,
    //then there are 100% percent active tasks and 0% completed tasks
    @Test
    fun getActiveAndCompletedStats_noCompleted_returnsZeroHundred() {
        //GIVEN
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = false)
        )

        //WHEN
        val result = getActiveAndCompletedStats(tasks)


        //THEN
        assertThat(result.completedTasksPercent, `is`(0f))
        //assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)

    }

    //If there's 1 completed task and 0 active tasks,
    //then there are 0% percent active tasks and 100% completed tasks

    @Test
    fun getActiveAndCompletedStatus_noActive_returnsZeroHundred() {
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = true)
        )
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.activeTasksPercent)
        assertEquals(100f, result.completedTasksPercent)
    }


    //If there's 2 completed tasks and 3 active tasks,
    //then there are 60% percent active tasks and 40% completed tasks
    @Test
    fun getActiveAndCompletedStats_60Active_40Completed() {
        val tasks = listOf<Task>(
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = false),
            Task("title", "description", isCompleted = true),
            Task("title", "description", isCompleted = false)
        )
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(40f, result.completedTasksPercent)
        assertEquals(60f, result.activeTasksPercent)

    }

    @Test
    fun getActiveAndCompletedStats_empty_returnsZeros() {
        val tasks = emptyList<Task>()
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)

    }

    @Test
    fun getActiveAndCompletedStats_null_returnsZeros() {
        val tasks = null
        val result = getActiveAndCompletedStats(tasks)

        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)

    }
}