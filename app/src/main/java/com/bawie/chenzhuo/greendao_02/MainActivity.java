package com.bawie.chenzhuo.greendao_02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawie.chenzhuo.greendao.gen.DaoMaster;
import com.bawie.chenzhuo.greendao.gen.DaoSession;
import com.bawie.chenzhuo.greendao.gen.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.et_salary)
    EditText etSalary;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.bt_insert)
    Button btInsert;
    @BindView(R.id.bt_select)
    Button btSelect;
    @BindView(R.id.bt_delete)
    Button btDelete;
    @BindView(R.id.bt_update)
    Button btUpdate;
    @BindView(R.id.rlv)
    RecyclerView rlv;
    private UserDao userDao;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "pwk.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();

    }

    @OnClick({R.id.bt_insert, R.id.bt_select, R.id.bt_delete, R.id.bt_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_insert:
                insert();
                break;
            case R.id.bt_select:
                select();
                break;
            case R.id.bt_delete:
                delete();
                break;
            case R.id.bt_update:
                update();
                break;
        }
    }


    private void insert() {
        name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String sex = etSex.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        if (name.isEmpty() && age.isEmpty() && sex.isEmpty() && salary.isEmpty()) {
            Toast.makeText(this, "请输入完整内容", Toast.LENGTH_SHORT).show();
        } else {


            User user = new User(null, name, age, sex, salary);
            long insert = userDao.insert(user);
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
            etName.getText().clear();
            etAge.getText().clear();
            etSex.getText().clear();
            etSalary.getText().clear();
//        select();
        }
    }

    private void select() {
        List<User> users = userDao.loadAll();
        MyAdapter adapter = new MyAdapter(users, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(linearLayoutManager);
        rlv.setAdapter(adapter);
    }

    private void delete() {
        String id = etId.getText().toString().trim();
        userDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
        Toast.makeText(this, "删除成功~", Toast.LENGTH_SHORT).show();
        etId.getText().clear();
        select();
    }

    private void update() {
        User user = userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).build().unique();
        if (user != null) {
            String s = etName.getText().toString();
            user.setName(s);

        }
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
        select();
    }

}
