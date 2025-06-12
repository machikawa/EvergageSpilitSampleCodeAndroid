package tokyo.trmotors.evergagesplit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tokyo.trmotors.evergagesplit.databinding.ActivityMainBinding

import android.app.Application
import com.evergage.android.ClientConfiguration
import com.evergage.android.Evergage

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AppBar関連のコードはすべて不要なため削除しました。
        // これで findNavController を探す必要もなくなります。
    }

    // OptionsMenuもAppBarに属する機能のため、不要になります。
    // override fun onCreateOptionsMenu(menu: Menu): Boolean { ... }
    // override fun onOptionsItemSelected(item: MenuItem): Boolean { ... }

}
