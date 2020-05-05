package com.rhtyme.coroutinevideocompressor.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.rhtyme.reactivevideocompressor.data.repo.GalleryRepo
import com.rhtyme.reactivevideocompressor.datasource.FakeDataSource
import com.rhtyme.reactivevideocompressor.model.AlbumFile
import com.rhtyme.reactivevideocompressor.model.Resource
import com.rhtyme.reactivevideocompressor.viewmodel.GalleryViewModel
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
class GalleryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var galleryViewModel: GalleryViewModel

    private lateinit var context: Context

    @Mock
    private lateinit var observerAlbumFiles: Observer<Resource<List<AlbumFile>>>

    @Mock
    private lateinit var galleryRepo: GalleryRepo

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        context = InstrumentationRegistry.getInstrumentation().targetContext
        galleryViewModel = GalleryViewModel(context, galleryRepo)
        galleryViewModel.rxSingleSchedulers = RxSingleSchedulers.TEST_SCHEDULER
        galleryViewModel.rxObservableSchedulers = RxObservableSchedulers.TEST_SCHEDULER
        galleryViewModel.albumFilesLiveData.observeForever(observerAlbumFiles)


    }


    @Test
    fun testLoadGallery() {
        val albums = FakeDataSource.albumFiles()
        val resp = Resource.Success(albums)
        `when`(galleryRepo.getAllMedia(context)).thenAnswer { Single.just(resp) }
        galleryViewModel.loadGallery()

        verify(observerAlbumFiles).onChanged(resp)
    }

    @After
    fun cleanUp() {

    }

}