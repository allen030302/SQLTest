package com.example.sqltest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ErrorManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSqlSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSqlSelect extends Fragment {

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

    private static ListView listView;
    private Context context;
    public FragmentSqlSelect(Context context) {
        // Required empty public constructor
        this.context=context;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSqlSelect.
     */
    // TODO: Rename and change types and number of parameters
//    public static FragmentSqlSelect newInstance(String param1, String param2) {
//        FragmentSqlSelect fragment = new FragmentSqlSelect();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


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
        View view =  inflater.inflate(R.layout.fragment_sql_select, container, false);

        // 建立SQLiteOpenHelper物件
        sqlDataBaseHelper = new SqlDataBaseHelper(this.getContext(),DataBaseName,null,DataBaseVersion,DataBaseTable);
        db = sqlDataBaseHelper.getWritableDatabase(); // 開啟資料庫
        listView = view.findViewById(R.id.accountShowList);

        SqlGetData();
        ListView_Customer(this.getContext());

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

    public void ListView_Customer(Context context){
        listView.setAdapter(null);
        if(AccountID.length == 0){
            Toast.makeText(context,"查無資料!!",Toast.LENGTH_SHORT).show();
        }
        else {
            SimpleAdapter adapter = new SimpleAdapter(context, getData(), R.layout.account_list, new String[]{"ID", "Account", "Password"}, new int[]{R.id.accountID, R.id.listAccount, R.id.listPassword});
            listView.setAdapter(adapter);
        }
    }

    public List getData() {
        List list = new ArrayList();
        Map map = new HashMap();
        for(int i = 0;i<AccountID.length;i++){
            map = new HashMap();
            map.put("ID", AccountID[i].toString());
            map.put("Account", AccountArray[i].toString());
            map.put("Password", PasswordArray[i].toString());
            list.add(map);
        }
        return list;
    }




}