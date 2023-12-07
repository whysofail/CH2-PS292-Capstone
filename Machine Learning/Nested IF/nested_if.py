# Inputs tags in one variabel
kriteria = [
    "hukum internasional", "identitas", "jaminan", "kepailitan", "kredit", 
    "masalah pelunasan", "penipuan", "perbankan", "perusahaan", "restrukturisasi", 
    "sengketa e-commerce", "teknis hutang piutang", "transaksi konvensional", 
    "undang-undang", "wanprestasi"
]

# Contoh kriteria yang akan diuji
kriteria_input = ["kepailitan"]

# Nested if untuk mengecek apakah kriteria sesuai
if "hukum internasional" in kriteria:
    if "identitas" in kriteria:
        if "jaminan" in kriteria:
            if "kepailitan" in kriteria:
                if "kredit" in kriteria:
                    if "masalah pelunasan" in kriteria:
                        if "penipuan" in kriteria:
                            if "perbankan" in kriteria:
                                if "perusahaan" in kriteria:
                                    if "restrukturisasi" in kriteria:
                                        if "sengketa e-commerce" in kriteria:
                                            if "teknis hutang piutang" in kriteria:
                                                if "transaksi konvensional" in kriteria:
                                                    if "undang-undang" in kriteria:
                                                        if "wanprestasi" in kriteria:
                                                            print("Kategori: Pengacara A")
