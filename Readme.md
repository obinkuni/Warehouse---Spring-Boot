How to run the application : 
1. Open the project in vs code.
   Pastikan sebelumnya bisa menjalankan java spring di VS Code.
2. Buka any file .java kemudian Run.
   Database H2 otomatis kebentuk dengan sample data nya.
3. Buka swagger-ui untuk test semua endpoint Rest API nya.

Design decisions you made and why
1. Project menggunakan H2 database, supaya lebih simple saat initial data dan generate samplenya saat app di jalankan.
2. Struktur project sebagai berikut
   Controller : Sebagai endpoint dari rest api nya.
   Dto : Sebagai object untuk proses request dan response.
   Exception : Supaya error handling bisa di jadikan di 1 tempat, tidak tersebar.
   Model : Sebagai data model, yang nanti akan digunakan H2 database juga.
   Repository : Sebagai interface dari JPA 
   Service : Sebagai business logic dari controller.
3. Perlu setup swagger-ui juga, untuk mengetahui endpoint apa saja yang available dan memudahkan untuk test.

Any assumptions you made :
1. Harga di set nya di level variant bukan di item.
2. Karena di level variant, baik nya di masing' variant ada versi nya, 
   jadi jika ada perubahan harga atau ganti supplier
   Tidak perlu menggunakan nama variant baru, tetap variant lama, namun saat penjualan 
   Tetap akan memotong stok variant lama terlebih dahulu.

API endpoint examples :
1. Lengkapnya sudah ada di swagger-ui.
