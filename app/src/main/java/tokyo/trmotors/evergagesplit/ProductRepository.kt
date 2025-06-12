package tokyo.trmotors.evergagesplit

// 商品データを表現するデータクラス
data class Product(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val imageName: String
)

// サンプル商品データを提供するオブジェクト
object ProductRepository {
    private val products = listOf(
        Product(
            id = "mobile1", // ★修正
            name = "高品質な商品A",
            price = "1980",
            description = "商品Aの詳細説明です。最新の技術を用いて作られており、非常に高い耐久性とデザイン性を兼ね備えています。",
            imageName = "ic_menu_gallery"
        ),
        Product(
            id = "mobile2", // ★修正
            name = "スタイリッシュな商品B",
            price = "2480",
            description = "商品Bの詳細説明です。スタイリッシュな見た目が特徴で、あなたの生活をより豊かに彩ります。",
            imageName = "ic_menu_camera"
        )
    )

    fun findProductById(id: String?): Product? {
        if (id == null) return null
        return products.find { it.id == id }
    }
}
