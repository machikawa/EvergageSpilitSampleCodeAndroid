# EvergageSplit サンプルアプリ

これは Interaction Studio (Evergage) SDK の基本的なイベントトラッキング（商品表示、カート追加、購入）を実装した、サンプルAndroidアプリです。

## 主な機能

本アプリには、以下のInteraction Studio SDKのトラッキングイベントが実装されています。

-   **商品詳細の表示:** `screen.viewItem(item)`
-   **カートへの追加:** `screen.addToCart(lineItem)`
-   **購入処理:** `screen.purchase(order)`

## セットアップとビルド方法

1.  このリポジトリをクローンします。
    ```bash
    git clone [リポジトリのURL]
    ```
2.  Android Studioでプロジェクトを開きます。
3.  Gradleの同期が完了するのを待ちます。
4.  **（重要）Evergageの接続情報を設定します。**
    - `[設定ファイル名やクラス名]` に、ご自身のEvergageアカウントとデータセットを設定してください。
    - （例: `Evergage.getInstance().start(EvergageActivity.EvergageConfigurationBuilder().account("YOUR_ACCOUNT").dataset("YOUR_DATASET").build())` など）
5.  ビルドして、エミュレータまたは実機にインストールします。

defaultではワイのアカウントにとんできます

## プロジェクト構成のポイント

-   `ProductDetailFragment.kt`
    -   商品詳細画面の表示と、SDKの各イベント（viewItem, addToCart, purchase）をトリガーするロジックが実装されています。
-   `ProductRepository.kt`
    -   デモ用の商品データを管理するリポジトリです。
-   `product_detail_layout.xml`
    -   商品詳細画面のレイアウトを定義しています。

## 主な使用ライブラリ

-   Interaction Studio (Evergage) Android SDK
-   Android Jetpack (Fragment, View Binding)
-   Material Components for Android

## ライセンス

このプロジェクトは [ライセンス名] の下で公開されています。

---
最終更新: 2025/06/12