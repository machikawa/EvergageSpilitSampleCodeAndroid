package tokyo.trmotors.evergagesplit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evergage.android.Evergage
import tokyo.trmotors.evergagesplit.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ★修正点: 渡すIDを "mobile1" に変更
        binding.productItem1.setOnClickListener {
            val bundle = Bundle().apply {
                putString("PRODUCT_ID", "mobile1")
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ProductDetailFragment, bundle)
        }

        // ★修正点: 渡すIDを "mobile2" に変更
        binding.productItem2.setOnClickListener {
            val bundle = Bundle().apply {
                putString("PRODUCT_ID", "mobile2")
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ProductDetailFragment, bundle)
        }
    }

    override fun onResume() {
        super.onResume()

        // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
        //
        //     商品一覧画面が表示されたことをトラッキングします
        //
        // ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
        val screen = Evergage.getInstance().getScreenForActivity(requireActivity())
        if (screen != null) {
            // この画面が「商品一覧」であることを示すイベントを送信します
            screen.trackAction("Product LIST Page")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
