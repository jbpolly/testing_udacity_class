package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [29])
class StatisticsViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    lateinit var statisticsViewModel: StatisticsViewModel
    private lateinit var tasksRepository: FakeTestRepository

    //substituted by the rule
    //val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){

        // We initialise the tasks to 3, with one active and two completed
        tasksRepository = FakeTestRepository()
        val task1 = Task("Title1", "Description1")
        val task2 = Task("Title2", "Description2", true)
        val task3 = Task("Title3", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        statisticsViewModel = StatisticsViewModel(tasksRepository)

    }

    @Test
    fun loadTasks_loading(){

        //this will allow the refresh to not execute completely before we assert that loading is showing
        mainCoroutineRule.pauseDispatcher()

        statisticsViewModel.refresh()
        // Then progress indicator is shown.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        mainCoroutineRule.resumeDispatcher()
        // Then progress indicator is hidden.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }


    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay(){

        //Make the repository return an error
        tasksRepository.setReturnError(true)

        statisticsViewModel.refresh()

        //Then an error message is shown
        assertThat(statisticsViewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(statisticsViewModel.error.getOrAwaitValue(), `is`(true))
    }


}