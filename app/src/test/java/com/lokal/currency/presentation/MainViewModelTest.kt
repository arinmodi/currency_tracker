import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lokal.currency.data.model.currencyDetail.CurrencyDetail
import com.lokal.currency.data.model.currencyDetail.Detail
import com.lokal.currency.data.model.currencyRate.CurrencyRate
import com.lokal.currency.data.repository.currency.datasource.CurrencyRemoteDataSource
import com.lokal.currency.presentation.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var currencyRemoteDataSource: CurrencyRemoteDataSource

    private lateinit var mainViewModel: MainViewModel

    private val accessKey = "4e76250f071e289ee462ca6fe3ba9670"

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mainViewModel = MainViewModel(currencyRemoteDataSource)
    }

    @Test
    fun `getCryptoList success`() = runBlocking {
        `when`(currencyRemoteDataSource.getLive(accessKey))
            .thenReturn(createMockRatesResponse())
        `when`(currencyRemoteDataSource.getList(accessKey))
            .thenReturn(createMockDetailsResponse())

        mainViewModel.getCryptoList()
        delay(100)

        assert(mainViewModel.data.value?.size == 1)
        assert(mainViewModel.dataFailure.value == null)
    }

    @Test
    fun `getCryptoList failure`() = runBlocking {
        `when`(currencyRemoteDataSource.getLive(accessKey))
            .thenReturn(createMockRatesResponse())
        `when`(currencyRemoteDataSource.getList(accessKey))
            .thenReturn(createMockDetailsResponseFail())

        mainViewModel.getCryptoList()
        delay(100)

        assert(mainViewModel.dataFailure.value == true)
    }

    private fun createMockRatesResponse(): Flow<Response<CurrencyRate>> {
        val mapRates = HashMap<String, Double>()
        mapRates["ABC"] = 1.00
        val mockRates = CurrencyRate(mapRates)
        return flow { emit(Response.success(mockRates)) }
    }

    private fun createMockDetailsResponse(): Flow<Response<CurrencyDetail>> {
        val mapDetail = HashMap<String, Detail>()
        mapDetail["ABC"] = Detail("ABC", "ABC_coin", "icon_url")
        val mockList = CurrencyDetail(mapDetail)
        return flow { emit(Response.success(mockList)) }
    }

    private fun createMockRatesResponseFail(): Flow<Response<CurrencyRate>> {
        return flow { emit(Response.error(400, null)) }
    }

    private fun createMockDetailsResponseFail(): Flow<Response<CurrencyDetail>> {
        return flow { emit(Response.error(400, null)) }
    }
}
