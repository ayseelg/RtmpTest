# RtmpTest

Bu proje, `com.github.ayseelg:rtmp-library` modülünü kullanarak Android üzerinde RTMP yayını başlatmak için örnek bir uygulamadır.

## Özellikler
- Kamera ve mikrofon izinlerini dinamik olarak ister.
- RTMP URL girilerek canlı yayın başlatılır ve durdurulur.
- Yayın sırasında kamera değiştirilebilir.
- Modern MVVM mimarisi ve ViewModel kullanımı.

## Kullanılan Kütüphaneler
- [com.github.ayseelg:rtmp-library:1.0.1](https://github.com/ayseelg/rtmp-library)
- AndroidX, Kotlin Coroutines

## Kurulum
1. **build.gradle.kts** dosyanıza ekleyin:
   
   ```kotlin
   implementation("com.github.ayseelg:rtmp-library:1.0.1")
   ```

2. **activity_main.xml** dosyanızda SurfaceView yerine OpenGlView kullanın:
   ```xml
   <com.pedro.library.view.OpenGlView
       android:id="@+id/surfaceView"
       android:layout_width="match_parent"
       android:layout_height="match_parent" />
   ```

3. **MainActivity.kt**'de modüllü ViewModel ile entegrasyon:
   - Kamera ve mikrofon izinleri alınır.
   - ViewModel ile RTMP yayını başlatılır/durdurulur.
   - Kamera değiştirilebilir.

   ```kotlin
   viewModel = ViewModelProvider(this, StreamViewModelFactory())[StreamViewModel::class.java]
   ...
   btnStartStop.setOnClickListener {
       if (viewModel.isStreaming) {
           viewModel.stopStream()
           btnStartStop.text = "Yayını Başlat"
       } else {
           val url = etRtmpUrl.text.toString()
           if (url.isNotEmpty()) {
               viewModel.startStream(url)
               btnStartStop.text = "Yayını Durdur"
           } else {
               Toast.makeText(this, "Lütfen geçerli bir RTMP URL'si girin", Toast.LENGTH_SHORT).show()
           }
       }
   }
   ...
   viewModel.initCamera(surfaceView)
   viewModel.bindLifecycle(lifecycle)
   viewModel.startPreview()
   ```

## Çalıştırma
- Uygulamayı başlatın.
- RTMP URL girin.
- "Yayını Başlat" butonuna tıklayın.
- Yayını durdurmak için tekrar aynı butona tıklayın.
- Kamerayı değiştirmek için "Kamerayı Çevir" butonunu kullanın.

