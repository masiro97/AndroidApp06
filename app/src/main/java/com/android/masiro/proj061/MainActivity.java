package com.android.masiro.proj061;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView t;
    final int _REQUEST_MSG_CODE = 10;
    final int _REQUEST_MSG_CODE_SECOND = 100;
    ListView list;
    ArrayList<String> list_data = new ArrayList<String>();
    ArrayList<Data> info = new ArrayList<Data>();
    ArrayAdapter<String> adapter;
    Data dataset;
    int number_of_list = 0;
    public void OnButton(View v){

       Intent intent = new Intent(MainActivity.this,Main2Activity.class);
       startActivityForResult(intent,_REQUEST_MSG_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.listview);
        t = (TextView)findViewById(R.id.textView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
        list.setAdapter(adapter);

    }

    public void SetListView(){

        list_data.add(dataset.getName());
        adapter.notifyDataSetChanged();
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                final int pos = position;
                dlg.setTitle("삭제하시겠습니까?")
                        .setPositiveButton("닫기",null)
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                number_of_list = number_of_list -1;
                                t.setText("맛집리스트 (" + number_of_list + "개)");
                                list_data.remove(pos);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();

                return true;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                intent.putExtra("Rmsg",info.get(position));
                startActivityForResult(intent,_REQUEST_MSG_CODE_SECOND);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == _REQUEST_MSG_CODE){
            if(resultCode == RESULT_OK){
                number_of_list ++;
                t.setText("맛집리스트 (" + number_of_list + "개)");
                dataset = data.getParcelableExtra("msg");
                info.add(dataset);
                SetListView();
            }
        }

        if(requestCode == _REQUEST_MSG_CODE_SECOND){
            if(resultCode == RESULT_OK){
                //3번째 액티비티에서 받기
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
