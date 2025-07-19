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

### 🔐 Register, Login & Autentikasi
- **Sistem autentikasi terintegrasi** dengan verifikasi kredensial berbasis file
- **Akses ke 2 role melalui login**:
   - `Admin`: Menambahkan dan menghapus game + manajemen GamePass
   - `Customer`: Akses transaksi + koleksi game pribadi
- **Buat `Customer` baru dengan Register**

### 🎮 Manajemen Game
- **Klasifikasi game**:
   - Free: Akses instan tanpa pembayaran
   - Paid: Wajib pembelian/license
- **Operasi `Admin`** 
   - Tambah/hapus game
- **Operasi `Customer`**
  - Menambahkan game Free & membeli game Paid


### 💼 Langganan GamePass
   - Admin: Ubah komposisi/harga GamePass
   - Customer: Berlangganan 1 bulan via saldo

### 💳 Sistem Transaksi
   - Pembelian game individual
   - Berlangganan GamePass bulanan
   - Tercatat: item, harga, waktu, user


### 🧾 Manajemen Data Persisten
 **Penyimpanan berbasis CSV**:

  | File          | Konten                              |
  |---------------|-------------------------------------|
  | `users.csv`   | User Credentials + Balance Customer |
  | `games.csv`   | Detail Game                         |
  | `gamepasses.csv` | Paket GamePass + GameIncluded       |
  | `transactions.csv` | Transaksi Customer                  |
  | `transaction_details.csv` | Detail Transaksi Customer           |
  | `customer_games.csv` | Relasi antar Customer dan Game      |
  | `customer_gamepass.csv` | Relasi antar Customer dan GamePass  |

- **Auto load/save**:
   - Data otomatis dimuat saat aplikasi start
   - Perubahan langsung disimpan ke file

## 📐 Diagram UML
<img src="https://github.com/Sancaaa/GameStore/blob/main/assets/ClassDiagram.png?raw=true" style="width: 100%; height: auto;" alt="UML Diagram">

*Ketuk untuk memperbesar gambar.*

---

[//]: # (<a name="struktur-class-oop"></a>)

[//]: # (## 🗂️ Struktur Class OOP)

[//]: # (### 🎬 [`Main`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Main.java&#41;)

[//]: # (Titik masuk utama aplikasi, menjalankan alur utama program dan mengatur flow antar komponen.)

[//]: # ()
[//]: # (### 🏪 [`GameStore`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/GameStore.java&#41;)

[//]: # (Mengelola data game, user, dan transaksi. Bertindak sebagai pusat logika aplikasi.)

[//]: # ()
[//]: # (### 👤 [`User`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/User.java&#41;)

[//]: # (Kelas induk dari `Admin` dan `Customer`, menyimpan informasi dasar pengguna seperti username dan password.)

[//]: # ()
[//]: # (#### 🛠️ [`Admin`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Admin.java&#41;)

[//]: # (Memiliki hak akses untuk menambahkan dan menghapus game serta GamePass dari sistem.)

[//]: # ()
[//]: # (#### 🧑‍💻 [`Customer`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Customer.java&#41;)

[//]: # (Dapat membeli game, melihat katalog, dan berlangganan GamePass.)

[//]: # ()
[//]: # (### 🔐 [`Authenticator`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Authenticator.java&#41;)

[//]: # (Bertugas menangani proses login dan verifikasi user berdasarkan username dan password.)

[//]: # ()
[//]: # (### 🎮 [`Game`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Game.java&#41;)

[//]: # (Mewakili entitas game di dalam sistem, baik yang gratis maupun berbayar.)

[//]: # ()
[//]: # (### 🎫 [`GamePass`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/GamePass.java&#41;)

[//]: # (Berisi kumpulan game yang bisa diakses oleh pengguna dengan sistem langganan.)

[//]: # ()
[//]: # (### 💳 [`Transaction`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/Transaction.java&#41;)

[//]: # (Mewakili proses transaksi pembelian game atau langganan GamePass, disimpan ke file melalui File I/O.)

[//]: # ()
[//]: # (#### 📄 `TransactionDetail` *&#40;inner class&#41;*)

[//]: # (Menyimpan informasi spesifik per transaksi, seperti item yang dibeli, harga, dan tanggal.)

[//]: # ()
[//]: # (### 📋 [`MenuManager`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/MenuManager.java&#41;)

[//]: # (Mengatur dan menampilkan menu interaktif kepada pengguna &#40;Admin/Customer&#41;.)

[//]: # ()
[//]: # (### 💰 [`PaymentManager`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/PaymentManager.java&#41;)

[//]: # (Menangani proses pembayaran dan validasi saat transaksi dilakukan.)

[//]: # ()
[//]: # (### 🧾 [`DataManager`]&#40;https://github.com/Sancaaa/GameStore/blob/main/src/DataManager.java&#41;)

[//]: # (Bertanggung jawab terhadap proses penyimpanan dan pembacaan data dari file &#40;.csv&#41; menggunakan File I/O.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 🖥️   Alur Penggunaan)

[//]: # ()
[//]: # (1. Pengguna membuka aplikasi dan login)

[//]: # (2. Jika sebagai **Admin**:)

[//]: # (    - Menambahkan game atau GamePass baru)

[//]: # (3. Jika sebagai **Customer**:)

[//]: # (    - Melihat daftar game)

[//]: # (    - Membeli game berbayar atau langganan GamePass)

[//]: # (    - Mengakses game yang telah dibeli atau tersedia lewat GamePass aktif)

[//]: # (4. Semua transaksi **dicatat dan disimpan ke dalam file**)

[//]: # (intinya contoh https://github.com/Sands225/PBO_Tugas_1/)

[//]: # (---)
