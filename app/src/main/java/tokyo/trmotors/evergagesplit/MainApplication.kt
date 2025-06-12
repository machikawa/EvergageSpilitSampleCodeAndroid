package tokyo.trmotors.evergagesplit

import android.app.Application // ← android.app.Application をインポート
import com.evergage.android.ClientConfiguration
import com.evergage.android.Evergage
import com.evergage.android.LogLevel

// "AppCompatActivity" ではなく "Application" を継承します
class MainApplication : Application() {

    // この onCreate の中で初期化を行います
    override fun onCreate() {
        super.onCreate()

        // 1. SDKを初期化
        // このファイル内での `this` は `Application` を指すため、エラーになりません。
        Evergage.initialize(this)

        // 2. Evergageのシングルトンインスタンスを取得
        val evergage = Evergage.getInstance()

        // 3. (推奨) ユーザーIDを設定
        // evergage.setUserId("theAuthenticatedUserId")

        // 4. ClientConfiguration を使用してSDKを起動
        evergage.start(
            ClientConfiguration.Builder()
                .account("hmachikawa1445593") // ← あなたのアカウント名に置き換えてください
                .dataset("sfinteraction") // ← あなたのデータセット名に置き換えてください
                .usePushNotifications(true)
                .build()
        )
        // 詳細をはくしくみ
        Evergage.setLogLevel(LogLevel.ALL)
    }
}