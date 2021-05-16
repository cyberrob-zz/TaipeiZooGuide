package com.wang.taipeizooguide.viewmodel

import app.cash.turbine.test
import com.wang.taipeizooguide.collectData
import com.wang.taipeizooguide.data.model.ApiStatus
import com.wang.taipeizooguide.data.model.Zoo
import com.wang.taipeizooguide.data.model.ZooQueryResult
import com.wang.taipeizooguide.data.remote.Response
import com.wang.taipeizooguide.data.remote.ZooRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ZooViewModelTest : BaseViewModelTest() {

    @RelaxedMockK
    private lateinit var zooRepository: ZooRepository


    private lateinit var viewModel: ZooViewModel


    override fun setup() {
        super.setup()

        viewModel = ZooViewModel(zooRepository).apply {
            initCoroutine(this)
        }
    }

    @ExperimentalTime
    @ExperimentalCoroutinesApi
    @Test
    fun `test if receive paged zoo data of 5`() = runBlockingTest {

        val expected = listOf<Zoo>(
            Zoo(
                E_Category = "",
                E_Geo = "",
                E_Info = "",
                E_Memo = "",
                E_Name = "",
                E_Pic_URL = "",
                E_URL = "",
                E_no = "",
                _id = 0
            ),
            Zoo(
                E_Category = "",
                E_Geo = "",
                E_Info = "",
                E_Memo = "",
                E_Name = "",
                E_Pic_URL = "",
                E_URL = "",
                E_no = "",
                _id = 1
            ),
            Zoo(
                E_Category = "",
                E_Geo = "",
                E_Info = "",
                E_Memo = "",
                E_Name = "",
                E_Pic_URL = "",
                E_URL = "",
                E_no = "",
                _id = 2
            ),
            Zoo(
                E_Category = "",
                E_Geo = "",
                E_Info = "",
                E_Memo = "",
                E_Name = "",
                E_Pic_URL = "",
                E_URL = "",
                E_no = "",
                _id = 3
            ),
            Zoo(
                E_Category = "",
                E_Geo = "",
                E_Info = "",
                E_Memo = "",
                E_Name = "",
                E_Pic_URL = "",
                E_URL = "",
                E_no = "",
                _id = 4
            )
        )

        coEvery {
            zooRepository.getZooList(
                queryString = "",
                limit = 5,
                offset = 0
            )
        }.returns(
            Response(
                status = ApiStatus.SUCCESS,
                data = ZooQueryResult(
                    count = 5,
                    limit = 0,
                    offset = 0,
                    sort = "",
                    results = listOf<Zoo>(
                        Zoo(
                            E_Category = "",
                            E_Geo = "",
                            E_Info = "",
                            E_Memo = "",
                            E_Name = "",
                            E_Pic_URL = "",
                            E_URL = "",
                            E_no = "",
                            _id = 0
                        ),
                        Zoo(
                            E_Category = "",
                            E_Geo = "",
                            E_Info = "",
                            E_Memo = "",
                            E_Name = "",
                            E_Pic_URL = "",
                            E_URL = "",
                            E_no = "",
                            _id = 1
                        ),
                        Zoo(
                            E_Category = "",
                            E_Geo = "",
                            E_Info = "",
                            E_Memo = "",
                            E_Name = "",
                            E_Pic_URL = "",
                            E_URL = "",
                            E_no = "",
                            _id = 2
                        ),
                        Zoo(
                            E_Category = "",
                            E_Geo = "",
                            E_Info = "",
                            E_Memo = "",
                            E_Name = "",
                            E_Pic_URL = "",
                            E_URL = "",
                            E_no = "",
                            _id = 3
                        ),
                        Zoo(
                            E_Category = "",
                            E_Geo = "",
                            E_Info = "",
                            E_Memo = "",
                            E_Name = "",
                            E_Pic_URL = "",
                            E_URL = "",
                            E_no = "",
                            _id = 4
                        )
                    )
                ),
                message = ""
            )
        )

        launchTest {
            viewModel.zooList.test(
                timeout = Duration.ZERO,
                validate = {
                    val collectedData = expectItem().collectData()
                    assertEquals(expected.size, collectedData.size)
                    assertEquals(expected, collectedData)
                    expectComplete()
                })
        }
    }
}