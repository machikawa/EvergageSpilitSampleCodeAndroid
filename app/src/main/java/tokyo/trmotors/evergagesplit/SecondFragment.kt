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

        // ★★★ ここでUIとクリックリスナーを設定します ★★★
        setupProductViews()
        setupClickListeners()
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

    private fun setupClickListeners() {
        // 商品1のクリックリスナー
        binding.productItem1.setOnClickListener {
            val bundle = Bundle().apply {
                putString("PRODUCT_ID", "mobile1")
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ProductDetailFragment, bundle)
        }

        // 商品2のクリックリスナー
        binding.productItem2.setOnClickListener {
            val bundle = Bundle().apply {
                putString("PRODUCT_ID", "mobile2")
            }
            findNavController().navigate(R.id.action_SecondFragment_to_ProductDetailFragment, bundle)
        }
    }

    private fun setupProductViews() {
        // 商品1の情報を取得してUIにセット
        ProductRepository.findProductById("mobile1")?.let { product ->
            binding.productName1.text = product.name
            binding.productPrice1.text = "￥${product.price}"

            val imageResId = resources.getIdentifier(product.imageName, "drawable", requireContext().packageName)
            if (imageResId != 0) {
                binding.productImage1.setImageResource(imageResId)
            }
        }

        // 商品2の情報を取得してUIにセット
        ProductRepository.findProductById("mobile2")?.let { product ->
            binding.productName2.text = product.name
            binding.productPrice2.text = "￥${product.price}"

            val imageResId = resources.getIdentifier(product.imageName, "drawable", requireContext().packageName)
            if (imageResId != 0) {
                binding.productImage2.setImageResource(imageResId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
