package com.dicoding.lawmate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.lawmate.data.Article

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _article = MutableLiveData<List<Article>>().apply {
        value = listOf(
            Article(
                1,
                "Pencegahan dan Penanganan Kekerasan Seksual: Membangun Masyarakat yang Aman dan Peduli",
                "Kekerasan seksual adalah tantangan serius yang memerlukan perhatian dan upaya kolektif dari masyarakat. Untuk membangun masyarakat yang aman dan peduli, penting bagi kita untuk fokus pada pendidikan, pencegahan, dan dukungan bagi para korban. Artikel ini membahas langkah-langkah kunci yang dapat diambil untuk menciptakan lingkungan yang bebas dari kekerasan seksual.\n" +
                        "\n" +
                        "1. Pendidikan sebagai Landasan Utama:\n" +
                        "Pendidikan memiliki peran sentral dalam pencegahan kekerasan seksual. Dengan memahami konsep persetujuan, batasan pribadi, dan hak asasi manusia, kita dapat membentuk generasi yang sadar akan pentingnya hubungan yang sehat dan penghormatan terhadap orang lain.\n" +
                        "\n" +
                        "2. Pentingnya Kesetaraan Gender:\n" +
                        "Kesetaraan gender adalah kunci untuk mengatasi kekerasan seksual. Artikel ini menyoroti pentingnya mengatasi ketidaksetaraan dan mengajarkan nilai-nilai yang merespekkan hak-hak individu, tanpa memandang jenis kelamin.\n" +
                        "\n" +
                        "3. Pencegahan Melalui Teknologi:\n" +
                        "Dalam era digital, risiko kekerasan seksual melalui platform online semakin meningkat. Edukasi mengenai keamanan digital, perlindungan privasi, dan etika online adalah langkah penting dalam melindungi masyarakat dari ancaman ini.\n" +
                        "\n" +
                        "4. Pentingnya Melaporkan dan Mencari Bantuan:\n" +
                        "Artikel ini menggarisbawahi pentingnya melaporkan kejadian kekerasan seksual dan mencari bantuan. Menyediakan informasi tentang langkah-langkah pertama setelah terjadinya kekerasan seksual serta sumber daya yang dapat memberikan dukungan sangat penting.\n" +
                        "\n" +
                        "5. Peran Sekolah dan Institusi Pendidikan:\n" +
                        "Lingkungan pendidikan memainkan peran kunci dalam pencegahan kekerasan seksual. Artikel ini membahas strategi untuk menciptakan lingkungan sekolah yang aman, melibatkan orang tua, dan memberikan pemahaman kepada siswa tentang pentingnya hubungan yang sehat.\n" +
                        "\n" +
                        "6. Keterlibatan Komunitas:\n" +
                        "Masyarakat yang terlibat aktif dapat membentuk pertahanan yang kuat terhadap kekerasan seksual. Diskusi tentang peran individu dalam mencegah kekerasan, menghilangkan stigma, dan mendukung korban merupakan aspek utama dari edukasi ini.\n" +
                        "\n" +
                        "7. Kesehatan Mental dan Pemulihan:\n" +
                        "Kekerasan seksual dapat berdampak serius pada kesehatan mental korban. Artikel ini menyoroti pentingnya akses terhadap layanan kesehatan mental dan dukungan psikologis dalam proses pemulihan.\n" +
                        "\n" +
                        "8. Membangun Lingkungan yang Aman:\n" +
                        "Menyusun rencana aksi untuk membangun masyarakat yang aman dan peduli melibatkan kerjasama antara pemerintah, lembaga swadaya masyarakat, dan individu. Artikel ini mengajak pembaca untuk berkontribusi aktif dalam menciptakan perubahan positif.\n" +
                        "\n" +
                        "Dengan memahami, mencegah, dan mendukung para korban kekerasan seksual, kita dapat bersama-sama menciptakan masyarakat yang berkomitmen untuk melindungi dan menghormati setiap individu. Edukasi ini adalah langkah awal menuju perubahan positif dan masyarakat yang lebih aman.",
                "30-11-2023",
                "https://tribratanews.polri.go.id/web/image/blog.post/51237/image"
            ),
            Article(
                2,
                "Mengakhiri Kekerasan Seksual: Menuju Masyarakat yang Aman dan Berempati",
                "Kekerasan seksual merupakan realitas yang memerlukan tindakan serius dan kolaboratif untuk mengakhiri dampak negatifnya. Dalam artikel ini, kita akan menjelajahi strategi dan pendekatan yang dapat membantu menciptakan masyarakat yang aman, berempati, dan bebas dari kekerasan seksual.\n" +
                        "\n" +
                        "1. Pemahaman Mendalam tentang Kekerasan Seksual:\n" +
                        "Menelusuri akar penyebab kekerasan seksual dan mendapatkan pemahaman yang mendalam tentang kompleksitasnya adalah langkah pertama menuju penanggulangan yang efektif.\n" +
                        "\n" +
                        "2. Menyoroti Peran Pria dalam Pencegahan:\n" +
                        "Fokus pada peran positif pria dalam pencegahan kekerasan seksual, termasuk bagaimana mendukung kesetaraan gender dan menciptakan budaya yang menolak tindakan kekerasan.\n" +
                        "\n" +
                        "3. Pentingnya Pendidikan Komprehensif tentang Seksualitas:\n" +
                        "Membahas perlunya pendidikan seksual yang komprehensif, melibatkan komunitas, keluarga, dan sekolah untuk membantu membentuk sikap yang sehat terhadap seksualitas.\n" +
                        "\n" +
                        "4. Mendorong Keterlibatan Lembaga Keagamaan:\n" +
                        "Menyelidiki bagaimana lembaga keagamaan dapat berperan sebagai kekuatan positif dalam memberikan pendidikan, dukungan, dan perlindungan terhadap korban kekerasan seksual.\n" +
                        "\n" +
                        "5. Pentingnya Keterbukaan dalam Melaporkan:\n" +
                        "Menggali tantangan dan stigma seputar melaporkan kekerasan seksual, dan mengedukasi masyarakat tentang pentingnya keterbukaan untuk mendukung korban dan mencegah tindakan berulang.\n" +
                        "\n" +
                        "6. Menghadirkan Narasi Korban:\n" +
                        "Memberikan suara kepada korban untuk berbicara tentang pengalaman mereka dapat membantu mengurangi stigma, meningkatkan pemahaman masyarakat, dan memicu perubahan sosial.\n" +
                        "\n" +
                        "7. Menanggapi Kekerasan Seksual di Lingkungan Kerja:\n" +
                        "Mendiskusikan langkah-langkah konkrit yang dapat diambil oleh perusahaan dan organisasi untuk menciptakan lingkungan kerja yang bebas dari kekerasan seksual.\n" +
                        "\n" +
                        "8. Pentingnya Pendekatan Holistik:\n" +
                        "Memperkenalkan pendekatan holistik yang melibatkan pendidikan, dukungan psikologis, advokasi, dan perubahan kebijakan untuk mencapai dampak positif yang berkelanjutan.\n" +
                        "\n" +
                        "9. Mendorong Solidaritas dan Tanggung Jawab Bersama:\n" +
                        "Mengajak masyarakat untuk bersatu, menolak kekerasan seksual, dan mendorong tanggung jawab bersama dalam menciptakan perubahan nyata.\n" +
                        "\n" +
                        "10. Melibatkan Media dan Kreativitas:\n" +
                        "Mengeksplorasi bagaimana media dan seni dapat digunakan sebagai alat untuk meningkatkan kesadaran, menginspirasi perubahan sikap, dan mendukung korban.\n" +
                        "\n" +
                        "Melalui pemahaman yang lebih dalam, keterlibatan aktif, dan tindakan kolaboratif, kita dapat bersama-sama menciptakan masyarakat yang aman, berempati, dan bebas dari kekerasan seksual.",
                "02-12-2023",
                "https://asset.kompas.com/crops/hOgVrRADZFChdZCxBDcHJI-WWds=/229x0:3495x2177/750x500/data/photo/2021/10/09/616172adcedc3.jpg"
            ),
            Article(
                3,
                "Manajemen Keuangan yang Bijak: Pahami dan Kelola Hutang Piutang dengan Cermat",
                "Pengelolaan keuangan yang bijak menjadi kunci kesuksesan finansial individu dan keluarga. Salah satu aspek penting yang perlu dipahami dan dikelola dengan cermat adalah hutang piutang. Dalam artikel ini, kita akan menjelajahi strategi dan prinsip-prinsip untuk memahami, mengelola, dan mengatasi hutang piutang.\n" +
                        "\n" +
                        "1. Pahami Jenis-jenis Hutang:\n" +
                        "\n" +
                        "Identifikasi jenis-jenis hutang seperti hutang konsumen, kredit kendaraan, dan kredit rumah, serta pemahaman mengenai perbedaan bunga tetap dan bunga mengambang.\n" +
                        "2. Edukasi Tentang Bunga dan Biaya Tambahan:\n" +
                        "\n" +
                        "Pahami cara perhitungan bunga dan biaya tambahan pada setiap jenis hutang untuk membuat keputusan yang informasional dan terencana.\n" +
                        "3. Penyusunan Anggaran dan Rencana Keuangan:\n" +
                        "\n" +
                        "Bagaimana menyusun anggaran dan rencana keuangan yang realistis untuk mencegah terjebak dalam lebih banyak hutang.\n" +
                        "4. Prioritaskan Pembayaran Hutang:\n" +
                        "\n" +
                        "Strategi untuk memprioritaskan pembayaran hutang berdasarkan tingkat bunga, jumlah, dan urgensi.\n" +
                        "5. Negosiasi dan Restrukturisasi Hutang:\n" +
                        "\n" +
                        "Langkah-langkah untuk bernegosiasi dengan kreditur dan opsi restrukturisasi hutang agar sesuai dengan kemampuan finansial.\n" +
                        "6. Pendidikan Keuangan Sejak Dini:\n" +
                        "\n" +
                        "Pentingnya memberikan pendidikan keuangan sejak dini untuk membangun pemahaman yang kuat tentang hutang dan tanggung jawab keuangan.\n" +
                        "7. Keterampilan Manajemen Keuangan Pribadi:\n" +
                        "\n" +
                        "Keterampilan praktis dalam manajemen keuangan pribadi, termasuk cara menabung, investasi yang bijak, dan pengembangan cadangan dana darurat.\n" +
                        "8. Peran Konselor Keuangan:\n" +
                        "\n" +
                        "Kapan dan bagaimana mencari bantuan dari konselor keuangan profesional untuk mendapatkan panduan yang lebih mendalam.\n" +
                        "9. Pentingnya Disiplin Keuangan:\n" +
                        "\n" +
                        "Cara membangun disiplin keuangan untuk menghindari terjebak dalam utang berkepanjangan.\n" +
                        "10. Pengelolaan Krisis Keuangan:\n" +
                        "Tindakan yang harus diambil dalam menghadapi krisis keuangan, termasuk kehilangan pekerjaan atau kejadian mendadak lainnya.\n" +
                        "\n" +
                        "11. Pemberdayaan Finansial Melalui Pendidikan:\n" +
                        "Menggunakan pendidikan sebagai alat untuk memberdayakan masyarakat dalam pengelolaan hutang dan membangun kestabilan finansial.\n" +
                        "\n" +
                        "12. Pentingnya Transparansi dan Komunikasi:\n" +
                        "Bagaimana transparansi dan komunikasi yang terbuka dengan kreditur dapat membantu dalam situasi keuangan sulit.\n" +
                        "\n" +
                        "Dengan pemahaman yang baik tentang hutang piutang dan penerapan praktik manajemen keuangan yang bijak, individu dapat membangun kestabilan finansial, mengurangi beban hutang, dan mencapai kebebasan finansial yang diinginkan.",
                "01-12-2023",
                "https://awsimages.detik.net.id/community/media/visual/2020/11/23/petaka-utang-piutang-1_169.jpeg?w=1200"
            )
        )
    }
    val article:LiveData<List<Article>> = _article



}