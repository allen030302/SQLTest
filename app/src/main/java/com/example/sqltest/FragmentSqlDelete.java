package com.example.sqltest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSqlDelete#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSqlDelete extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "Users";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    public static String[] AccountArray,AccountID,PasswordArray;

    private Spinner spinner;
    private Button button;

    public FragmentSqlDelete() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSqlDelete.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSqlDelete newInstance(String param1, String param2) {
        FragmentSqlDelete fragment = new FragmentSqlDelete();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_sql_delete, container, false);
        spinner = view.findViewById(R.id.spinnerSqlAccountDelete);
        button = view.findViewById(R.id.buttonSqlDelete);

        sqlDataBaseHelper = new SqlDataBaseHelper(this.getContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        SqlGetData();

       if(AccountArray.length != 0){
           ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(this.getContext(),R.layout.spinner_item,AccountArray);
           spinner.setAdapter(nAdapter);
       }

       spinner.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               SqlGetData();
               if(AccountArray.length != 0){
                   ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,AccountArray);
                   spinner.setAdapter(nAdapter);
               }
               else{
                   spinner.setAdapter(null);
               }

               return false;
           }
       });

        final DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        int count;
                        count = db.delete(DataBaseTable,"_id="+AccountID[spinner.getSelectedItemPosition()],null);
                        SqlGetData();
                        if(AccountArray.length != 0){
                            ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(getContext(),R.layout.spinner_item,AccountArray);
                            spinner.setAdapter(nAdapter);
                        }
                        else{
                            spinner.setAdapter(null);
                        }
                        Toast.makeText(view.getContext(),"刪除紀錄成功:"+count,Toast.LENGTH_SHORT).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DeleteAccount = spinner.getSelectedItem().toString();
                if(DeleteAccount != null && !DeleteAccount.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("確定要刪除 "+ DeleteAccount +" ?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                }
                else {
                    Toast.makeText(view.getContext(),"請選擇刪除帳號!!",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void SqlGetData(){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable,null);
        AccountArray = new String[c.getCount()];
        AccountID = new String[c.getCount()];
        PasswordArray = new String[c.getCount()];
        c.moveToFirst();
        for(int i=0;i<c.getCount();i++){
            AccountID[i] = c.getString(0);
            AccountArray[i] = c.getString(1);
            PasswordArray[i] = c.getString(2);
            c.moveToNext();
        }
    }



}