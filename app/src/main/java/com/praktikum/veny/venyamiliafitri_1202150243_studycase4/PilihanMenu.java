package com.praktikum.veny.venyamiliafitri_1202150243_studycase4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PilihanMenu extends AppCompatActivity {
    //deklarasi private variable
    private Button list, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan_menu);

        list = (Button)findViewById(R.id.list);
        search = (Button)findViewById(R.id.search);

        //Melakukan eksekusi setelah button diklik
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast ketikan button diklik
                Toast.makeText(PilihanMenu.this,"Anda memilih menu List Nama Mahasiswa",Toast.LENGTH_SHORT).show();
                //Intent ke aktivitas selanjutnya setelah button diklik
                Intent menu1 = new Intent(PilihanMenu.this,ListMahasiswa.class);
                //Perintah memulai aktivitas intent yang sudah dideklarasikan
                startActivity(menu1);
            }
        });

        //Melakukan eksekusi setelah button diklik
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast ketikan button diklik
                Toast.makeText(PilihanMenu.this,"Anda memilih menu Cari Gambar",Toast.LENGTH_SHORT).show();
                //Intent ke aktivitas selanjutnya setelah button diklik
                Intent menu2 = new Intent(PilihanMenu.this,CariGambar.class);
                //Perintah memulai aktivitas intent yang sudah dideklarasikan
                startActivity(menu2);
            }
        });
    }
}