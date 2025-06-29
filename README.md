# PlusPusApp - Score Keeper Android App

Ứng dụng Android đơn giản để theo dõi điểm số cho nhiều người chơi, được phát triển bằng Kotlin.

## 🎯 Tính năng

- ✅ Thêm/xóa người chơi
- ✅ Tăng/giảm điểm số cho từng người chơi
- ✅ Lịch sử điểm số
- ✅ Xóa tất cả dữ liệu
- ✅ Giao diện Material Design hiện đại
- ✅ Lưu trữ dữ liệu cục bộ với Room Database

## 🛠️ Công nghệ sử dụng

- **Ngôn ngữ**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room Database
- **UI**: Material Design Components
- **Dependency Injection**: Dagger Hilt (nếu có)
- **Minimum SDK**: API 21 (Android 5.0)

## 📱 Screenshots

![Uploading image.png…]()


## 🏗️ Cấu trúc dự án

```
app/src/main/java/com/example/pluspusapp/
├── MainActivity.kt              # Activity chính
├── HistoryActivity.kt          # Activity lịch sử
├── Player.kt                   # Entity Player
├── ScoreHistory.kt             # Entity lịch sử điểm
├── PlayerDao.kt                # Data Access Object cho Player
├── ScoreHistoryDao.kt          # Data Access Object cho lịch sử
├── ScoreKeeperDatabase.kt      # Room Database
├── PlayerRepository.kt         # Repository pattern
├── ScoreKeeperViewModel.kt     # ViewModel
├── PlayerAdapter.kt            # RecyclerView Adapter cho Player
└── HistoryAdapter.kt           # RecyclerView Adapter cho lịch sử
```

## 🚀 Cài đặt và chạy

1. Clone repository:

   ```bash
   git clone https://github.com/linh0526/Learn-Android-Kotlin.git
   ```

2. Mở dự án trong Android Studio

3. Sync Gradle files

4. Chạy ứng dụng trên thiết bị hoặc emulator

## 📋 Yêu cầu hệ thống

- Android Studio Arctic Fox hoặc mới hơn
- Android SDK API 21+
- Kotlin 1.5+

## 🤝 Đóng góp

Mọi đóng góp đều được chào đón! Hãy tạo Pull Request hoặc mở Issue để thảo luận.

## 📄 License

Dự án này được phát hành dưới MIT License. Xem file [LICENSE](LICENSE) để biết thêm chi tiết.

## 👨‍💻 Tác giả

- **linh0526** - [GitHub Profile](https://github.com/linh0526)

## 📞 Liên hệ

Nếu bạn có bất kỳ câu hỏi nào, hãy tạo issue trên GitHub hoặc liên hệ qua email.

---

⭐ Nếu dự án này hữu ích, hãy cho một star nhé! ⭐
