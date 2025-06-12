package tokyo.trmotors.evergagesplit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.evergage.android.Evergage
import tokyo.trmotors.evergagesplit.databinding.ProductDetailLayoutBinding

/**
 * 商品詳細情報を表示するFragment
 */
class ProductDetailFragment : Fragment() {

    private var _binding: ProductDetailLayoutBinding? = null
    private val binding get() = _binding!!

    // 表示する商品情報をクラスのプロパティとして保持します
    private var product: Product? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductDetailLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- ★★★ デバッグログ強化 ★★★ ---
        Log.d("ProductDetailDebug", "--- onViewCreated ---")

        if (arguments == null) {
            Log.e("ProductDetailDebug", "エラー: argumentsがnullです。IDを受け取れていません。")
            return
        }

        // argumentsから商品IDを取得
        val productId = arguments?.getString("PRODUCT_ID")
        Log.d("ProductDetailDebug", "受け取った商品ID (PRODUCT_ID): $productId")

        if (productId == null) {
            Log.e("ProductDetailDebug", "エラー: 商品IDがnullです。")
            return
        }

        // IDを使って商品情報を取得
        this.product = ProductRepository.findProductById(productId)
        Log.d("ProductDetailDebug", "リポジトリから取得した商品: ${this.product}") // ここでnullかどうか確認

        if (this.product == null) {
            Log.e("ProductDetailDebug", "エラー: ID '$productId' に一致する商品がリポジトリに見つかりません。")
        }
        // --- ★★★ デバッグここまで ★★★ ---

        // 取得した情報でUIを更新します
        updateUi()

        binding.addToCartButton.setOnClickListener {
            val prodctItem = com.evergage.android.promote.Product(this.product?.id!!)
            prodctItem.price = this.product?.price!!.toDouble()
            val lineItem = com.evergage.android.promote.LineItem(prodctItem,1)
            Evergage.getInstance().getScreenForActivity(requireActivity())?.addToCart(lineItem)
            Toast.makeText(context, "${product?.name ?: "商品"}をカートに追加しました", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        // トラッキング処理はproductが正しく取得できてから実装します
        Log.d("ProductDetailDebug", "--- onResume ---")
        Log.d("ProductDetailDebug", "現在のProduct情報: ${this.product}")
        val screen = Evergage.getInstance().getScreenForActivity(requireActivity())
        val prodctItem = com.evergage.android.promote.Product(this.product?.id!!)
        prodctItem.name = product?.name
        prodctItem.description = product?.description
        if (screen != null) {
            screen?.viewItem(prodctItem)
        }

    }

    private fun updateUi() {
        // 取得した商品情報でUIを更新
        product?.let { currentProduct ->
            binding.productNameDetail.text = currentProduct.name
            binding.productPriceDetail.text = currentProduct.price
            binding.productDescriptionDetail.text = currentProduct.description

            val imageResId = resources.getIdentifier(currentProduct.imageName, "drawable", requireContext().packageName)
            if (imageResId != 0) {
                binding.productImageDetail.setImageResource(imageResId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
