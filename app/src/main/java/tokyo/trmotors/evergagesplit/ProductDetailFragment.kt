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
// ★★★重要★★★
// このコードが正しく動作するには、ファイル上部でEvergage SDKの
// 正しいProductクラスとLineItemクラスがインポートされている必要があります。
// 例: import com.evergage.android.promote.Product
//     import com.evergage.android.promote.LineItem
import com.evergage.android.promote.Product
import com.evergage.android.promote.LineItem
import com.evergage.android.promote.Order


/**
 * 商品詳細情報を表示するFragment
 */
class ProductDetailFragment : Fragment() {

    private var _binding: ProductDetailLayoutBinding? = null
    private val binding get() = _binding!!

    // 表示する商品情報をクラスのプロパティとして保持します
    private var product: tokyo.trmotors.evergagesplit.Product? = null

    // カートに入れる数量
    private var quantity = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // ★修正点: レイアウトファイル名を新しいものに変更します
        _binding = ProductDetailLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getString("PRODUCT_ID")
        this.product = ProductRepository.findProductById(productId)
        Log.d("ProductDetailDebug", "リポジトリから取得した商品: ${this.product}")

        updateUi()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()

        val screen = Evergage.getInstance().getScreenForActivity(requireActivity())
        if (screen != null) {
            product?.let { currentProduct ->
                // ご提示のロジックに準拠し、Itemを作成してトラッキングします
                val item = Product(currentProduct.id)
                item.name = currentProduct.name
                item.description = currentProduct.description
                item.price = currentProduct.price.toDoubleOrNull()

                screen.viewItem(item)
                Log.d("ProductDetailDebug", "viewItemを送信: ID=${item.id}")
            }
        }
    }

    private fun setupClickListeners() {
        // 「カートに入れる」ボタンの処理
        binding.addToCartButton.setOnClickListener {
            product?.let { currentProduct ->
                // ご提示のロジックに準拠し、LineItemを作成します
                val productItem = Product(currentProduct.id)
                productItem.price = currentProduct.price.toDoubleOrNull()
                val lineItem = LineItem(productItem, quantity)

                // addToCartイベントを送信します
                val screen = Evergage.getInstance().getScreenForActivity(requireActivity())
                screen!!.addToCart(lineItem)

                Toast.makeText(context, "${currentProduct.name}を${quantity}個カートに追加しました", Toast.LENGTH_SHORT).show()
                Log.d("ProductDetailDebug", "addToCartを送信: ${lineItem}")
            }
        }

        // 数量を増やすボタン
        binding.quantityAddButton.setOnClickListener {
            quantity++
            binding.quantityText.text = quantity.toString()
        }

        // 数量を減らすボタン
        binding.quantityRemoveButton.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.quantityText.text = quantity.toString()
            }
        }

        // =======================================================
        // ★★★ ここに「購入する」ボタンの処理を追加しました ★★★
        // =======================================================
        binding.purchaseButton.setOnClickListener {
            product?.let { currentProduct ->
                // 1. 購入する商品(LineItem)リストを作成します
                val productItem = Product(currentProduct.id)
                productItem.price = currentProduct.price.toDoubleOrNull()
                val lineItem = LineItem(productItem, quantity)
                val lineItems = listOf(lineItem) // Orderで渡すためにリスト化

                // 2. ユニークなオーダーIDを生成します (例: 現在時刻を利用)
                val newOrderId = "ORD-${System.currentTimeMillis()}"

                // 3. Orderオブジェクトを生成します
                // コンストラクタ: Order(orderId, lineItems, totalValue)
                // totalValueをnullにすると、SDKがlineItemsから合計金額を自動計算します。
                val order = Order(newOrderId, lineItems, null)

                // 4. 通貨を日本円(JPY)に設定します
                order.totalValueCurrency = "JPY"

                // 5. purchaseイベントを送信します
                val screen = Evergage.getInstance().getScreenForActivity(requireActivity())
                screen!!.purchase(order)

                // 処理の完了をログとToastで通知します
                Log.d("ProductDetailDebug", "purchaseイベントを送信しました: orderId=${order.orderId}")
                Toast.makeText(context, "購入が完了しました", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUi() {
        // 取得した商品情報でUIを更新
        product?.let { currentProduct ->
            binding.productNameDetail.text = currentProduct.name
            binding.productPriceDetail.text = "￥${currentProduct.price}"
            binding.productDescriptionDetail.text = currentProduct.description
            binding.quantityText.text = quantity.toString()

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