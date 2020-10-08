package com.example.sqltest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSqlInsert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSqlInsert extends Fragment {

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

    private SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;
    private EditText edit_Account,edit_Password;
    private String edit_Account_Text,edit_Password_Text;
    private Button btn_Insert;

    public FragmentSqlInsert() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSqlInsert.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSqlInsert newInstance(String param1, String param2) {
        FragmentSqlInsert fragment = new FragmentSqlInsert();
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
        View view = inflater.inflate(R.layout.fragment_sql_insert, container, false);

        edit_Account = (EditText) view.findViewById(R.id.insertAccount);
        edit_Password = (EditText) view.findViewById(R.id.insertPassword);
        // 建立SQLiteOpenHelper物件
        sqlDataBaseHelper = new SqlDataBaseHelper(this.getContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫

        btn_Insert = view.findViewById(R.id.buttonSqlInsert);
        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_Account_Text = edit_Account.getText().toString();
                edit_Password_Text = edit_Password.getText().toString();
                if(edit_Account_Text != null && !edit_Account_Text.equals("") && edit_Password_Text != null && !edit_Password_Text.equals("")) {
                    if(SqlAccountCheck(edit_Account_Text) > 0){
                        Toast.makeText(view.getContext(),"輸入帳號已存在!!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        long id;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("account",edit_Account_Text);
                        contentValues.put("password",edit_Password_Text);
                        id = db.insert(DataBaseTable,null,contentValues);
                        Toast.makeText(view.getContext(),"帳號新增成功:"+id,Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(view.getContext(),"請輸入帳號密碼",Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    public Integer SqlAccountCheck(String newAccount){
        Cursor c = db.rawQuery("SELECT * FROM " + DataBaseTable + " WHERE account = '" +newAccount+"'",null);
        c.moveToFirst();
        return c.getCount();
    }

}