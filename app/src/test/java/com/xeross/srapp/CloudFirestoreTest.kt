package com.xeross.srapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xeross.srapp.ui.categoryform.CategoryFormViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class CloudFirestoreTest {
    
    private lateinit var viewModel: CategoryFormViewModel
    
    @Mock
    lateinit var observer: LiveData<Boolean>
    
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()//For LiveData
    
    companion object {
        private const val TAG = "CloudFirestoreTest"
    }
    
    @Before
    fun setUp() {
        //    MockitoAnnotations.initMocks(this)
/*        viewModel = Mockito.mock(CategoryFormViewModel::class.java)
        Mockito.`when`(viewModel.getUserId()).thenReturn("uid_test")*/
    }
    
    @Test
    fun test() {
/*        viewModel = Mockito.mock(CategoryFormViewModel::class.java)
        Mockito.`when`(viewModel.getUserId()).thenReturn("uid_test")
        val boolean = LiveDataTestUtil.getValue(viewModel.createCategoryToDatabase("", "", "", ""))
        Assert.assertEquals(false, boolean)*/
    }
    
}