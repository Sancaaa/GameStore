# OOP GameStore

Program ini adalah sebuah **platform distribusi game digital berbasis Java** yang memungkinkan pengguna untuk membeli game secara individu atau melalui sistem langganan **GamePass**, mirip seperti layanan *Steam* atau *Xbox Game Pass*.

Sistem memiliki dua jenis pengguna:
- 👨‍💼 **Admin**: dapat menambahkan dan menghapus game maupun GamePass
- 🧑‍💻 **Customer**: dapat membeli game berbayar, mengakses game gratis, dan berlangganan GamePass untuk mendapatkan akses ke kumpulan game selama periode tertentu
---

## 🎯 Fitur Utama
### 🔐 Login & Autentikasi
- Sistem autentikasi berbasis **username dan password**
- Terdapat dua peran pengguna: **Admin** dan **Customer**

### 🎮 Manajemen Game
- Game dibagi menjadi dua jenis: **Free** dan **Paid**
- Admin dapat mengimplementasikan konsep **CRUD** pada game dalam sistem

### 💼 Langganan GamePass
- GamePass adalah **paket langganan** berisi kumpulan game
- Dapat diakses oleh pengguna selama **masa berlangganan aktif**

### 💳 Transaksi
- Customer dapat membeli **game secara langsung** atau **berlangganan GamePass** 
- Semua transaksi **disimpan dan dapat ditinjau kembali**

### 🧾 Manajemen Data
- Data user, game, GamePass, dan transaksi disimpan dalam file `.csv`
- Menggunakan **File I/O** untuk membaca dan menulis data secara persisten

### 🧱 Struktur OOP
- Sistem terdiri dari **[13 class](#🗂️Struktur Class OOP)**
- Menerapkan prinsip **Object-Oriented Programming (OOP)**:
    - Inheritance
    - Abstraksi (`abstract`)
    - Polymorphism
    - Interface
    - Inner class
    - Exception handling (try-catch-finally, custom exception)
---

## 🔧 Teknologi dan Teknik

| Komponen           | Detail                                                              |
|--------------------|---------------------------------------------------------------------|
| 🧠 **Bahasa**       | Java                                                                |
| 📁 **Penyimpanan**  | File `.csv`                                                         |
| 💬 **Input**        | `Scanner` (Command-Line Interface)                                  |
| 🧱 **OOP**          | 10+ class, inheritance, interface, abstraksi, polymorphism          |
| 🚨 **Error Handling** | `try-catch-finally`, `throws`, dan custom exception              |
| 📦 **Java Collection** | `ArrayList`, `HashMap`, `LinkedList`                             |
---

## 📐 Diagram UML
_Gambar_

## 🗂️ Struktur Class OOP

### 🎬 `Main`
Titik masuk utama aplikasi, menjalankan alur utama program dan mengatur flow antar komponen.

---

### 🏪 `GameStore`
Mengelola data game, user, dan transaksi. Bertindak sebagai pusat logika aplikasi.

---

### 👤 `User`
Kelas induk dari `Admin` dan `Customer`, menyimpan informasi dasar pengguna seperti username dan password.

#### 🛠️ `Admin`
Memiliki hak akses untuk menambahkan dan menghapus game serta GamePass dari sistem.

#### 🧑‍💻 `Customer`
Dapat membeli game, melihat katalog, dan berlangganan GamePass.

---

### 🔐 `Authenticator`
Bertugas menangani proses login dan verifikasi user berdasarkan username dan password.

---

### 🎮 `Game`
Mewakili entitas game di dalam sistem, baik yang gratis maupun berbayar.

---

### 🎫 `GamePass`
Berisi kumpulan game yang bisa diakses oleh pengguna dengan sistem langganan.

---

### 💳 `Transaction`
Mewakili proses transaksi pembelian game atau langganan GamePass, disimpan ke file melalui File I/O.

#### 📄 `TransactionDetail` *(inner class)*
Menyimpan informasi spesifik per transaksi, seperti item yang dibeli, harga, dan tanggal.

---

### 📋 `MenuManager`
Mengatur dan menampilkan menu interaktif kepada pengguna (Admin/Customer).

---

### 💰 `PaymentManager`
Menangani proses pembayaran dan validasi saat transaksi dilakukan.

---

### 🧾 `DataManager`
Bertanggung jawab terhadap proses penyimpanan dan pembacaan data dari file (`.csv`) menggunakan File I/O.

---

## 🖥️   Alur Penggunaan

1. Pengguna membuka aplikasi dan login
2. Jika sebagai **Admin**:
    - Menambahkan game atau GamePass baru
3. Jika sebagai **Customer**:
    - Melihat daftar game
    - Membeli game berbayar atau langganan GamePass
    - Mengakses game yang telah dibeli atau tersedia lewat GamePass aktif
4. Semua transaksi **dicatat dan disimpan ke dalam file**
---