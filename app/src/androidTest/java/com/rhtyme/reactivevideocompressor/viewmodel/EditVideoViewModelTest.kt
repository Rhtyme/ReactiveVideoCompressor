package com.rhtyme.coroutinevideocompressor.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.arthenica.mobileffmpeg.MediaInformation
import com.rhtyme.reactivevideocompressor.data.repo.EditVideoRepo
import com.rhtyme.reactivevideocompressor.datasource.FakeDataSource
import com.rhtyme.reactivevideocompressor.model.AlbumFile
import com.rhtyme.reactivevideocompressor.model.Resource
import com.rhtyme.reactivevideocompressor.viewmodel.EditVideoViewModel
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import ru.kwork.app.utils.rx.RxObservableSchedulers
import ru.kwork.app.utils.rx.RxSingleSchedulers

@RunWith(AndroidJUnit4::class)
class EditVideoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var editVideoViewModel: EditVideoViewModel

    private lateinit var context: Context

    @Mock
    private lateinit var observerMediaInformationLiveData: Observer<Resource<MediaInformation>>

    @Mock
    private lateinit var observerCompressInformationLiveData: Observer<Resource<AlbumFile>>

    @Mock
    private lateinit var editVideoRepo: EditVideoRepo

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        context = InstrumentationRegistry.getInstrumentation().targetContext
        editVideoViewModel = EditVideoViewModel(context, editVideoRepo)
        editVideoViewModel.rxSingleSchedulers = RxSingleSchedulers.TEST_SCHEDULER
        editVideoViewModel.rxObservableSchedulers = RxObservableSchedulers.TEST_SCHEDULER
        editVideoViewModel.mediaInformationLiveData.observeForever(observerMediaInformationLiveData)
        editVideoViewModel.compressInformationLiveData.observeForever(
            observerCompressInformationLiveData
        )

    }


    @Test
    fun testFetchMediaInformation() {

        val mediaInfo = FakeDataSource.mediaInfo()
        val resp = Resource.Success(mediaInfo)

        `when`(editVideoRepo.fetchMediaInformation("")).thenAnswer { Single.just(mediaInfo) }
        editVideoViewModel.fetchMediaInformation("")

        verify(observerMediaInformationLiveData).onChanged(resp)
    }

    @Test
    fun testStartCompression() {
        val albumFile = FakeDataSource.albumFile()
        val albumFileResult = FakeDataSource.albumResult(albumFile)
        val progressResult = FakeDataSource.progressResult()
        val compressionConfig = FakeDataSource.compressionConfig()

        val albumFileResp = Resource.Success(albumFile)

        val fakeObservable =
            FakeDataSource.progressiveResultAlbumFile(progressResult, albumFileResult)
        `when`(editVideoRepo.startCompression(context, compressionConfig))
            .thenAnswer { fakeObservable }

        editVideoViewModel.startCompression(context, compressionConfig)

        verify(observerCompressInformationLiveData).onChanged(albumFileResp)
    }

    @After
    fun cleanUp() {

    }

}