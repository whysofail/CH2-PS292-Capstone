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

tag_sh_studi_kasus = {
    "pelecehan seksual tempat kerja": 'Pengacara kategori A',
    "pemerkosaan": 'Pengacara kategori B',
    "pelecehan seksual online": 'Pengacara kategori C',
    "kekerasan seksual keluarga": 'Pengacara kategori D',
    "eksploitasi seksual": 'Pengacara kategori E',
    "pelecehan seksual masyarakat": 'Pengacara kategori F',
    "pelecehan seksual transport umum": 'Pengacara kategori G',
    "pelecehan seksual kampus": 'Pengacara kategori H',
    "pelecehan seksual keluarga": 'Pengacara kategori I',
    "pelecehan seksual sekolah": 'Pengacara kategori J',
    "takut melapor": 'Pengacara kategori K',
    "mensupport": 'Pengacara Kategori L',
    "antisipasi tempat kerja": 'Pengacara Kategori M',
    "ingin dapat bantuan hukum": 'Pengacara Kategori N',
    "pelecehan seksual tempat pendidikan": 'Pengacara Kategori O',
    "tanyasoalhukum": 'Pengacara Kategori P',
    "intimidasi seksual": 'Pengacara Kategori Q',
    "solusi pelecehan seksual tempat pendidikan": 'Pengcara Kategori S',
    "solusi pelecehan seksual tempat kerja": 'Pengacara Kategori T',
    "solusi pelecehan seksual keluarga": 'Pengacara Kategori U',
    "solusi pelecehan seksual masyarakat": 'Pengacara Kategori V',
    "pelecehan seksual keluarga": 'Pengacara Kategori W'
}


tag_sh_teori= {
    "tanyasoalhukum": 'Pengacara Kategori 1', 
    "ingin dapat bantuan hukum": 'Pengacara Kategori 2', 
    "kekerasan seksual": 'Pengacara Kategori 3',
    "kekerasan seksual keluarga": 'Pengacara Kategori 4', 
    "mensupport": 'Pengacara Kategori 5', 
    "pelecehan seksual anak": 'Pengacara Kategori 6',
    "pelecehan seksual anak": 'Pengacara Kategori 7', 
    "pelecehan seksual disabilitas": 'Pengacara Kategori 8',
    "pelecehan seksual keluarga": 'Pengacara Kategori 9', 
    "pelecehan seksual online": 'Pengacara Kategori 10',
    "pelecehan seksual sesama jenis": 'Pengacara Kategori 11', 
    "pelecehan seksual tempat kerja": 'Pengacara Kategori 12',
    "pelecehan seksual tempat pendidikan": 'Pengacara Kategori 13'
}

sh_tags = (tag_sh_studi_kasus, tag_sh_teori)

# passing user tags accordding the input in this variable
user_tags = ["tanyasoalhukum"]

found = False

for tags in sh_tags:
    if user_tags[0] in tags:
        print(tags[user_tags[0]])
        found = True
        break

if not found:
    print('Kategori tidak ditemukan')

# Checking either the tags are on debts  or sexual harrasment
#found = False

#for tags in sh_tags:
    #if any(tag in tags for tag in user_tags):
        #print("Kategori Pengacara Pelecehan seksual")
        #found = True
        #break

#if not found:
    #for tags in debts_tags:
        #if any(tag in tags for tag in user_tags):
            #print("Kategori Pengacara Hutang piutang")
            #found = True
            #break

#if not found:
    #print("Tidak ada pengacara yang sesuai dengan kasus anda")





    
