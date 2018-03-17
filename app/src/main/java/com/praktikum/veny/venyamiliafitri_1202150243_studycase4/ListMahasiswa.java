package com.praktikum.veny.venyamiliafitri_1202150243_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {

    //Deklrasi private variable
    private ListView mListView;
    private ProgressBar mProgressBar;
    private Button mStartAsyncTask;
    //Menyimpan string daftar nama mahasiswa ke dalam variable array namaMahasiswa
    private String [] namaMahasiswa= {
            "Veny", "Yudha", "Hendri", "Ria", "Deninta",
            "Desi", "Dela", "Dini", "Dave", "Osas",
            "Akhdan", "Khalif", "Vera", "Tuti", "Riska"
    };
    //Deklarasi private variable untuk menambahkan item ke listview
    private AddItemToListView mAddItemToListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.listView);
        mStartAsyncTask = (Button) findViewById(R.id.button_startAsyncTask);

        //Membuat progressbar visible ketika aplikasi berjalan
        mListView.setVisibility(View.GONE);

        //Setup adapter
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));

        //Memulai async task ketika button diklik
        mStartAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adapter proses dengan async task
                mAddItemToListView = new AddItemToListView();
                mAddItemToListView.execute();
            }
        });
    }

    //Didalam class untuk proses async task
    public class AddItemToListView  extends AsyncTask<Void, String, Void> {

        private ArrayAdapter<String> mAdapter;
        private int counter=1;
        ProgressDialog mProgressDialog = new ProgressDialog(ListMahasiswa.this);

        //Dipanggil di thread UI sebelum tugas dijalankan
        //Langkah ini biasanya digunakan untuk mensetup tugas, misalnya dengan menampilkan progress bar pada user interface
        @Override
        protected void onPreExecute() {
            mAdapter = (ArrayAdapter<String>) mListView.getAdapter(); //casting suggestion

            //Untuk progress dialog
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("Loading Data");
            mProgressDialog.setProgress(0);

            //Menghandle tombol cancel pada dialog
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAddItemToListView.cancel(true);
                    //Menampilkan (Visible) progress bar pada layar dialog setelah diklik cancel
                    mProgressBar.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }
            });
            mProgressDialog.show();
        }

        //Dipanggil di thread latar belakang segera setelah onPreExecute () selesai mengeksekusi.
        //Langkah ini digunakan untuk melakukan perhitungan background yang bisa memakan waktu lama.
        //Parameter tugas asinkron dilewatkan ke langkah ini.
        //Hasil perhitungan harus dikembalikan oleh langkah ini dan akan diteruskan kembali ke langkah terakhir.
        @Override
        protected Void doInBackground(Void... params) {
            for (String item : namaMahasiswa){
                publishProgress(item);
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(isCancelled()){
                    mAddItemToListView.cancel(true);
                }
            }
            return null;
        }

        //Dipanggil di thread UI setelah ada panggilan untuk publishProgress(Progress ...).
        //Waktu eksekusi tidak terdefinisi.
        //Metode ini digunakan untuk menampilkan segala bentuk kemajuan dalam user interface,
        //sedangkan perhitungan background masih dieksekusi.
        //Misalnya, ini bisa digunakan untuk menghidupkan sebuah progress bar atau show logs dalam field teks.
        @Override
        protected void onProgressUpdate(String... values) {
            mAdapter.add(values[0]);

            Integer current_status = (int)((counter/(float)namaMahasiswa.length)*100);
            mProgressBar.setProgress(current_status);

            //Set tampilan progress pada dialog progress
            mProgressDialog.setProgress(current_status);

            //Set message berupa persentase progress pada dialog progress
            mProgressDialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        //Dipanggil di thread UI setelah perhitungan latar belakang selesai.
        //Hasil perhitungan latar belakang dilewatkan ke langkah ini sebagai parameter.
        @Override
        protected void onPostExecute(Void Void) {
            //Menyembunyikan progressbar
            mProgressBar.setVisibility(View.GONE);

            //Menghilangkan progress dialog
            mProgressDialog.dismiss();
            mListView.setVisibility(View.VISIBLE);
        }


    }
}
