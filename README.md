# Simple YouTube App

Simple YouTube App 是一個基於 Android 的應用程式，利用 YouTube Data API 來取得 YouTube 頻道的影片播放清單、頻道資訊、影片詳細資訊以及影片留言，並將資料整合於一個流暢的用戶介面上。此應用使用 Jetpack Compose 作為 UI 框架，整合 Retrofit、Hilt 依賴注入，以及 [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player) 作為影片播放引擎。

## 目錄

- [功能介紹](#功能介紹)
- [技術架構](#技術架構)
- [安裝與設定](#安裝與設定)
  - [申請 API 金鑰](#申請-api-金鑰)
  - [設定 local.properties](#設定-localproperties)

## 功能介紹

此應用提供以下主要功能：
- **播放清單顯示 (Playlist Screen)：**
  - 透過 YouTube Data API 的 `playlistItems` 端點取得指定頻道的播放清單資料。
  - 初次載入 30 筆影片資料，滑動到底部會自動載入後續 20 筆影片。
  - 每個影片項目會顯示縮圖、影片標題、頻道名稱、上傳時間，並同時顯示頻道擁有者的圖片。
  - 搜尋功能：可根據影片標題進行快速過濾。

- **影片播放 (Video Player Screen)：**
  - 點擊播放清單中的影片後，將進入影片播放頁面。
  - 使用 [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player) 來播放影片，確保合法且穩定的播放體驗。
  - 顯示影片標題、頻道名稱、上傳時間，以及影片描述。
  - 留言區塊：從 YouTube API 的 `commentThreads` 端點取得影片留言，並於播放頁面下方以滾動區塊呈現。

## 技術架構

- **語言與框架：** Kotlin、Jetpack Compose
- **依賴注入：** Hilt
- **網路請求：** Retrofit + GsonConverterFactory
- **影片播放：** [android-youtube-player](https://github.com/PierfrancescoSoffritti/android-youtube-player)
- **單元測試：** JUnit、kotlinx-coroutines-test

## 安裝與設定

### 申請 API 金鑰

1. 前往 [Google Cloud Console](https://console.cloud.google.com/)，建立專案。
2. 啟用 YouTube Data API v3。
3. 在 API & Services 的憑證區，建立 API 金鑰。
4. 複製金鑰以備後續使用。

### 設定 local.properties

在專案根目錄中的 `local.properties` 檔案加入以下行：

```properties
YOUTUBE_API_KEY=YOUR_ACTUAL_API_KEY_HERE
```

最後附上此應用使用的螢幕錄影 [link](https://drive.google.com/file/d/1yJNL0IDvD9vqMVL6g2wVB5lkm-K4e9ng/view?usp=sharing)
