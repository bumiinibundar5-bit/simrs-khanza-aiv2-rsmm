SIMRS KHANZA - BACKUP SOURCE ORIGINAL

Tanggal Backup : 11 Maret 2026
Project        : SIMRS2026
Lokasi Backup  : Backup_Source_code_original/akses.java

DESKRIPSI
Folder ini berisi file source asli `akses.java` sebelum dilakukan perubahan (custom) pada sistem SIMRS Khanza.

FUNGSI FILE
File `akses.java` digunakan untuk mengatur hak akses pengguna terhadap menu, modul, dan fitur yang ada di dalam sistem.

TUJUAN BACKUP
Backup ini dibuat untuk menyimpan source asli sebelum dilakukan perubahan pada pengaturan akses, khususnya untuk penambahan akses ke modul baru yang akan dibuat.

RENCANA PERUBAHAN
File `akses.java` kemungkinan akan dimodifikasi untuk menambahkan hak akses terhadap modul baru:

RMPemeriksaanEEG.java

Hal ini dilakukan agar user tertentu dapat membuka form pemeriksaan EEG dari sistem.

CATATAN
File dalam folder ini merupakan source asli dan tidak boleh dimodifikasi.
Perubahan hanya dilakukan pada file yang berada di folder src project utama.

INSTRUKSI ROLLBACK
Jika terjadi error setelah modifikasi:

1. Copy kembali file `akses.java` dari folder backup ini.
2. Replace file yang ada di folder src project.
3. Compile ulang project.

STATUS
Backup source original sebelum penambahan akses modul pemeriksaan EEG.
