package com.example.mrfit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import java.util.List;

import com.example.userdb.*;

public class SelectUserActivity extends BaseActivity {
    List<String> list;
    ArrayAdapter<String> adapter;
    ListView lv;
    InputMethodManager imm;
    DatabaseHandler db;
    final Context context = this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_add:
                TableLayout useraddview= (TableLayout) findViewById(R.id.useraddView);
                useraddview.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        lv = (ListView) findViewById(R.id.userlistView);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        db = new DatabaseHandler(this);
        updateUserList(db.getAllUserName());



        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int selectedPos = position;
                if (position != ListView.INVALID_POSITION) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("사용자 삭제");
                    alertDialogBuilder.setMessage("선택한 사용자를 삭제하시겠습니까?");
                    alertDialogBuilder.setPositiveButton("삭제",
                            new DialogInterface.OnClickListener() {
                                public void onClick( DialogInterface dialog, int id) {
                                    String selectedUserName = adapter.getItem(selectedPos);
                                    int selectedUserID = db.getUserID(selectedUserName);
                                    db.deleteUserInfo(selectedUserID);
                                    list.remove(selectedPos);
                                    lv.clearChoices();
                                    adapter.notifyDataSetChanged();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != ListView.INVALID_POSITION) {
                    String selectedUserName = adapter.getItem(position);
                    UserInfo selectedUser = db.getUserInfo(selectedUserName);
                    String[] selectedUserArray = {String.valueOf(selectedUser.getID()), selectedUser.getName(), selectedUser.getHeight(), selectedUser.getFootSize(), selectedUser.getLegLength()};
                    ((Global)getApplicationContext()).set_s_user_info(selectedUserArray);
                    Log.i("onClick", "CallSubActivity");
                    Intent intentSubActivity1 = new Intent(SelectUserActivity.this, ExListActivity.class);
                    startActivity(intentSubActivity1);
                }
            }
        });

        findViewById(R.id.addNewUser).setOnClickListener(clickListener);
    }
        public Button.OnClickListener clickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newUserName = (EditText) findViewById(R.id.newUsername);
                EditText newUserHeight = (EditText) findViewById(R.id.newUserheight);
                EditText newUserFoot = (EditText) findViewById(R.id.newUserfootsize);
                EditText newUserLeg = (EditText) findViewById(R.id.newUserleglength);

                Log.d("Insert : ", "Inserting ..");
                db.addUserInfo(new UserInfo(newUserName.getText().toString(), newUserHeight.getText().toString(),
                        newUserFoot.getText().toString(), newUserLeg.getText().toString()));

                updateUserList(db.getAllUserName());

            }
        };

    public void updateUserList(List<String> usernamelist){
        list = db.getAllUserName();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
        lv.setAdapter(adapter);
        lv.setDivider(new ColorDrawable(Color.parseColor("#E34803")));
        lv.setDividerHeight(5);
    }

}
