# Put all the Sexual Harrasment and Debpts tags in one variable

#Debts
debts_teori = ["hukum internasional", "identitas", "jaminan", "kepailitan", "kredit", 
            "masalah pelunasan", "penipuan", "perbankan", "perusahaan", 
            "restrukturisasi", "sengketa e-commerce", "teknis hutang piutang", 
            "transaksi konvensional", "undang-undang", "wanprestasi"]

debts_studi_kasus = ["Arbitrase Utang Piutang", "jaminan hipotek", "kepailitan",
                     "restrukturisasi kredit", "Sengketa E-commerce",
                     "Sengketa Internasional", "sengketa pelunasan", "Sengketa Piutang",
                     "Sengketa Transaksi", "studi kasus hutang piutang", "wanprestasi"]

sh_teori = ["tanyasoalhukum", "ingin dapat bantuan hukum", "kekerasan seksual",
                  "kekerasan seksual keluarga", "mensupport", "pelecehan seksual anak",
                  "pelecehan seksual anak ", "pelecehan seksual disabilitas ",
                  "pelecehan seksual keluarga", "pelecehan seksual online",
                  "pelecehan seksual sesama jenis", "pelecehan seksual tempat kerja",
                  "pelecehan seksual tempat pendidikan"]

#Sexual Harrasment
sh_studi_kasus = ["pelecehan seksual tempat kerja", "pemerkosaan", "pelecehan seksual online",
                  "kekerasan seksual keluarga", "eksploitasi seksual", "pelecehan seksual masyarakat",
                  "pelecehan seksual transport umum", "studi kasus kekerasan seksual", "pelecehan seksual kampus",
                  "pelecehan seksual keluarga", "pelecehan seksual sekolah", "takut melapor", "mensupport",
                  "antisipasi tempat kerja", "ingin dapat bantuan hukum", "pelecehan seksual tempat pendidikan",
                  "tanyasoalhukum", "intimidasi seksual", "solusi pelecehan seksual tempat pendidikan",
                  "solusi pelecehan seksual tempat kerja", "solusi pelecehan seksual keluarga",
                  "solusi pelecehan seksual masyarakat", "pelecehan seksual keluarga"]

debts_tags = (debts_studi_kasus, debts_teori)
sh_tags = (sh_teori, sh_studi_kasus)

# passing user tags accordding the input in this variable
user_tags = []

# Checking either the tags are on debts  or sexual harrasment
found = False

for tags in sh_tags:
    if any(tag in tags for tag in user_tags):
        print("Kategori Pengacara Pelecehan seksual")
        found = True
        break

if not found:
    for tags in debts_tags:
        if any(tag in tags for tag in user_tags):
            print("Kategori Pengacara Hutang piutang")
            found = True
            break

if not found:
    print("Tidak ada pengacara yang sesuai dengan kasus anda")
