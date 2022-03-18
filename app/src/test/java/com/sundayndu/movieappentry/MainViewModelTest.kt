package com.sundayndu.movieappentry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.sundayndu.movieappentry.data.repository.MovieRepository
import com.sundayndu.movieappentry.presentation.MainViewModel
import com.sundayndu.movieappentry.utils.MovieTestModels
import com.sundayndu.movieappentry.utils.ResultState
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        mainViewModel = MainViewModel(movieRepository, testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun requestLatestMoviesUpdatesUiMoviesSharedFlowResultStateSuccess() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedLatestAndUpdate())
            .thenReturn(flowOf(ResultState.Success(MovieTestModels.latestMovie)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.latestMovie()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Success::class, actualItem::class)
            assertEquals(ResultState.Success(MovieTestModels.latestMovie), actualItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestLatestMoviesUpdatesUiMoviesSharedFlowResultStateFailed() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedLatestAndUpdate())
            .thenReturn(flowOf(ResultState.Error(Throwable("Test exception occurred"))))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.latestMovie()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Error::class, actualItem::class)
            assertEquals("Test exception occurred", (actualItem as ResultState.Error).error.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestLatestMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithoutData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedLatestAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(null)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.latestMovie()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNull((actualItem as ResultState.Loading).data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestLatestMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithLatestData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedLatestAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(MovieTestModels.latestMovie)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.latestMovie()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNotNull((actualItem as ResultState.Loading).data)
            assertEquals(actualItem.data?.firstOrNull()?.imdbId, "tt5852644")
            cancelAndConsumeRemainingEvents()
        }
    }

    // Popular Movies tests
    @Test
    fun requestPopularMoviesUpdatesUiMoviesSharedFlowResultStateSuccess() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedPopularAndUpdate())
            .thenReturn(flowOf(ResultState.Success(MovieTestModels.popularMovies)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.popularMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Success::class, actualItem::class)
            assertEquals(ResultState.Success(MovieTestModels.popularMovies), actualItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestPopularMoviesUpdatesUiMoviesSharedFlowResultStateFailed() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedPopularAndUpdate())
            .thenReturn(flowOf(ResultState.Error(Throwable("Test exception occurred"))))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.popularMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Error::class, actualItem::class)
            assertEquals("Test exception occurred", (actualItem as ResultState.Error).error.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestPopularMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithLatestData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedPopularAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(MovieTestModels.latestMovie)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.popularMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNotNull((actualItem as ResultState.Loading).data)
            assertEquals(actualItem.data?.firstOrNull()?.imdbId, "tt5852644")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestPopularMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithoutData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedPopularAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(null)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.popularMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNull((actualItem as ResultState.Loading).data)
            cancelAndConsumeRemainingEvents()
        }
    }

    // Upcoming Tests
    @Test
    fun requestUpcomingMoviesUpdatesUiMoviesSharedFlowResultStateSuccess() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedUpcomingAndUpdate())
            .thenReturn(flowOf(ResultState.Success(MovieTestModels.popularMovies)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.upcomingMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Success::class, actualItem::class)
            assertEquals(ResultState.Success(MovieTestModels.popularMovies), actualItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestUpcomingMoviesUpdatesUiMoviesSharedFlowResultStateFailed() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedUpcomingAndUpdate())
            .thenReturn(flowOf(ResultState.Error(Throwable("Test exception occurred"))))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.upcomingMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Error::class, actualItem::class)
            assertEquals("Test exception occurred", (actualItem as ResultState.Error).error.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestUpcomingMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithLatestData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedUpcomingAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(MovieTestModels.latestMovie)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.upcomingMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNotNull((actualItem as ResultState.Loading).data)
            assertEquals(actualItem.data?.firstOrNull()?.imdbId, "tt5852644")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestUpcomingMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithoutData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitCachedUpcomingAndUpdate())
            .thenReturn(flowOf(ResultState.Loading(null)))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.upcomingMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNull((actualItem as ResultState.Loading).data)
            cancelAndConsumeRemainingEvents()
        }
    }

    // All Movies test
    @Test
    fun requestAllMoviesUpdatesUiMoviesSharedFlowResultStateSuccess() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitAllCachedMovie())
            .thenReturn(ResultState.Success(MovieTestModels.popularMovies))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.allMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Success::class, actualItem::class)
            assertEquals(ResultState.Success(MovieTestModels.popularMovies), actualItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestAllMoviesUpdatesUiMoviesSharedFlowResultStateFailed() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitAllCachedMovie())
            .thenReturn(ResultState.Error(Throwable("Test exception occurred")))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.allMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Error::class, actualItem::class)
            assertEquals("Test exception occurred", (actualItem as ResultState.Error).error.message)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestAllMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithLatestData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitAllCachedMovie())
            .thenReturn(ResultState.Loading(MovieTestModels.latestMovie))

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.allMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNotNull((actualItem as ResultState.Loading).data)
            assertEquals(actualItem.data?.firstOrNull()?.imdbId, "tt5852644")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun requestAllMoviesUpdatesUiMoviesSharedFlowResultStateLoadingWithoutData() = runBlockingTest(testDispatcher) {
        // When
        Mockito.`when`(movieRepository.emitAllCachedMovie())
            .thenReturn(ResultState.Loading())

        mainViewModel.uIMovies.test {
            // Then
            mainViewModel.allMovies()
            val actualItem = awaitItem()
            // Assert
            assertEquals(ResultState.Loading::class, actualItem::class)
            assertNull((actualItem as ResultState.Loading).data)
            cancelAndConsumeRemainingEvents()
        }
    }
}