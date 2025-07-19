# OOP GameStore

Program ini adalah sebuah **platform distribusi game digital berbasis Java** yang memungkinkan pengguna untuk membeli game secara individu atau melalui sistem langganan **GamePass**, mirip seperti layanan *Steam* atau *Xbox Game Pass*.

Sistem memiliki dua jenis pengguna:
- 👨‍💼 **Admin**: dapat menambahkan dan menghapus game maupun GamePass
- 🧑‍💻 **Customer**: dapat membeli game berbayar, mengakses game gratis, dan berlangganan GamePass untuk mendapatkan akses ke kumpulan game selama periode tertentu
---

## 🙋‍♂️ Kontributor
| Nama                         | NIM        | Kelas   |
|------------------------------|------------|---------|
| Anak Agung Narendera Sancaya | 2405551038 | PBO (F) |
| I Gusti Ngurah Agung Vimala Pratista Putra | 2405551083 | PBO (F) |
| I Gusti Bagus Rama Kusuma Vijaya | 2405551085 | PBO (F) |
| I Wayan Juana Satya Adinata | 2405551091 | PBO (F) |
| Dewa Made Pandu Diotama | 2405551098 | PBO (F) |
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
---

## 📐 Diagram UML
![UML Diagram](https://github.com/Sancaaa/GameStore/blob/main/assets/ClassDiagram.png?raw=true)

*Ketuk untuk memperbesar gambar.*

---

<a name="struktur-class-oop"></a>
## 🗂️ Struktur Class OOP
### 🎬 [`Main`](https://github.com/Sancaaa/GameStore/blob/main/src/Main.java)
Titik masuk utama aplikasi, menjalankan alur utama program dan mengatur flow antar komponen.

### 🏪 [`GameStore`](https://github.com/Sancaaa/GameStore/blob/main/src/GameStore.java)
Mengelola data game, user, dan transaksi. Bertindak sebagai pusat logika aplikasi.

### 👤 [`User`](https://github.com/Sancaaa/GameStore/blob/main/src/User.java)
Kelas induk dari `Admin` dan `Customer`, menyimpan informasi dasar pengguna seperti username dan password.

#### 🛠️ [`Admin`](https://github.com/Sancaaa/GameStore/blob/main/src/Admin.java)
Memiliki hak akses untuk menambahkan dan menghapus game serta GamePass dari sistem.

#### 🧑‍💻 [`Customer`](https://github.com/Sancaaa/GameStore/blob/main/src/Customer.java)
Dapat membeli game, melihat katalog, dan berlangganan GamePass.

### 🔐 [`Authenticator`](https://github.com/Sancaaa/GameStore/blob/main/src/Authenticator.java)
Bertugas menangani proses login dan verifikasi user berdasarkan username dan password.

### 🎮 [`Game`](https://github.com/Sancaaa/GameStore/blob/main/src/Game.java)
Mewakili entitas game di dalam sistem, baik yang gratis maupun berbayar.

### 🎫 [`GamePass`](https://github.com/Sancaaa/GameStore/blob/main/src/GamePass.java)
Berisi kumpulan game yang bisa diakses oleh pengguna dengan sistem langganan.

### 💳 [`Transaction`](https://github.com/Sancaaa/GameStore/blob/main/src/Transaction.java)
Mewakili proses transaksi pembelian game atau langganan GamePass, disimpan ke file melalui File I/O.

#### 📄 `TransactionDetail` *(inner class)*
Menyimpan informasi spesifik per transaksi, seperti item yang dibeli, harga, dan tanggal.

### 📋 [`MenuManager`](https://github.com/Sancaaa/GameStore/blob/main/src/MenuManager.java)
Mengatur dan menampilkan menu interaktif kepada pengguna (Admin/Customer).

### 💰 [`PaymentManager`](https://github.com/Sancaaa/GameStore/blob/main/src/PaymentManager.java)
Menangani proses pembayaran dan validasi saat transaksi dilakukan.

### 🧾 [`DataManager`](https://github.com/Sancaaa/GameStore/blob/main/src/DataManager.java)
Bertanggung jawab terhadap proses penyimpanan dan pembacaan data dari file (.csv) menggunakan File I/O.

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
intinya contoh https://github.com/Sands225/PBO_Tugas_1/
---