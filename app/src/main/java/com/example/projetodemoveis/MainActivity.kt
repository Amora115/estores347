import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.projetodemoveis.MainViewModel
import com.example.projetodemoveis.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Observa mudanças nos dados do clima
        viewModel.weatherData.observe(this) { data ->
            textView.text = data
        }

        // Busca os dados ao iniciar
        viewModel.fetchWeatherData()
    }
}
