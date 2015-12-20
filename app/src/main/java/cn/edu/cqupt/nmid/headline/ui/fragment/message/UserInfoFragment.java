package cn.edu.cqupt.nmid.headline.ui.fragment.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.LogRecord;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.repository.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.*;
import cn.edu.cqupt.nmid.headline.support.repository.headline.bean.Class;
import cn.edu.cqupt.nmid.headline.ui.activity.MessageListActivity;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by xwysun on 2015/11/17.
 */
public class UserInfoFragment extends Fragment {

    @InjectView(R.id.studentCheck)
    ImageView studentCheck;
    @InjectView(R.id.student)
    LinearLayout student;
    @InjectView(R.id.spinner_grade)
    Spinner spinnerGrade;
    @InjectView(R.id.gradebackground)
    LinearLayout gradebackground;
    @InjectView(R.id.spinner_class)
    Spinner spinnerClass;
    @InjectView(R.id.classbackground)
    LinearLayout classbackground;
    @InjectView(R.id.studentsetting)
    LinearLayout studentsetting;
    @InjectView(R.id.teacherCheck)
    ImageView teacherCheck;
    @InjectView(R.id.teacher)
    LinearLayout teacher;
    @InjectView(R.id.accoutbackground)
    LinearLayout accoutbackground;
    @InjectView(R.id.passwordbackground)
    LinearLayout passwordbackground;
    @InjectView(R.id.teachersetting)
    LinearLayout teachersetting;

    public  String MODE = "student";
    @InjectView(R.id.accouttext)
    EditText accouttext;
    @InjectView(R.id.passwordtext)
    EditText passwordtext;
    @InjectView(R.id.card_view_grade)
    CardView cardViewGrade;
    @InjectView(R.id.card_view_class)
    CardView cardViewClass;
    @InjectView(R.id.confirm)
    Button confirm;
    private String password;
    private String account;
    private boolean isLogin;
    private ArrayList<String> gradeList = new ArrayList<String>();
    private ArrayList<Class> classesList=new ArrayList<Class>();
    private ArrayList<String> class0List = new ArrayList<String>();
    private ArrayList<String> class1List = new ArrayList<String>();
    private ArrayList<String> class2List = new ArrayList<String>();
    private ArrayList<String> class3List = new ArrayList<String>();
    private ArrayList<ArrayList<String>> classLists=new ArrayList<ArrayList<String>>();
    private String confirmGrade;
    private String confirmClass;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler classhandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setSpinnier();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences=getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        getGradeClass();
       initView();
    }

    public static UserInfoFragment newInstance(Bundle bundle) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * to do initView and SET Listener
     */
    public void initView() {
        changeCardView(MODE);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODE = "student";
                changeCardView(MODE);
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MODE = "teacher";
                changeCardView(MODE);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MODE.equals("student") ) {
                    if (confirmGrade != null && confirmClass != null) {
                        editor.putString("classNo", confirmClass);
                        editor.putString("MODE",MODE);
                        editor.commit();
                        Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MessageListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "请先选择班级", Toast.LENGTH_SHORT).show();
                    }
                } else if (MODE.equals("teacher")) {
                    account = accouttext.getText().toString().trim();
                    password = passwordtext.getText().toString().trim();
                    if (!password.isEmpty() && !account.isEmpty()) {
                        checkAccount(account,password);
                    } else {
                        Toast.makeText(getActivity(), "请输入账号密码", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void setSpinnier()
    {
        ArrayAdapter<String> gradeAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                gradeList);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradeAdapter);
        spinnerGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                confirmGrade = gradeList.get(position);
                ArrayList<String> chooseClasses=classLists.get(position);
                Log.d("chooseclasses","ok"+chooseClasses.toString());
                ArrayAdapter<String> classAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,
                       chooseClasses );
                classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerClass.setAdapter(classAdapter);
                spinnerClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        confirmClass = chooseClasses.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * to get grade and class LIST
     * use Retrofit 2.0
     */
    public void getGradeClass() {
        RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
                .create(HeadlineService.class)
                .getGradesList().enqueue(new Callback<GradeList>() {
            @Override
            public void onResponse(Response<GradeList> response, Retrofit retrofit) {
                //怪得不行的JSON解析
                gradeList=(ArrayList<String>)response.body().getGrades();
                classesList=(ArrayList<Class>)response.body().getClasses();
                class0List=(ArrayList<String>)classesList.get(0).getGrade0();
                class1List=(ArrayList<String>)classesList.get(1).getGrade1();
                class2List=(ArrayList<String>)classesList.get(2).getGrade2();
                class3List=(ArrayList<String>)classesList.get(3).getGrade3();
                Log.d("class1list", "" + class0List.toString());
                classLists.add(class0List);
                Log.d("class1list", "" + class1List.toString());
                classLists.add(class1List);
                Log.d("class1list", "" + class2List.toString());
                classLists.add(class2List);
                Log.d("class1list", "" + class3List.toString());
                classLists.add(class3List);
                Log.d("class0list0", gradeList.get(0));
                classhandler.sendEmptyMessage(0);
            }

            @Override
            public void onFailure(Throwable t) {
               Toast.makeText(getActivity(),"请检查网络连接或稍后重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * to check teacher account
     * @param account
     * @param password
     * @return
     */
    public void checkAccount(String account,String password)
    {
        RetrofitUtils.getCachedAdapter(HeadlineService.END_POINT)
                .create(HeadlineService.class)
                .CheckAccount(account,password).enqueue(new Callback<IsLogin>() {
            @Override
            public void onResponse(Response<IsLogin> response, Retrofit retrofit) {
                isLogin=response.body().getLogin();
                Log.d("islogin", String.valueOf(isLogin));
                if (isLogin) {
                    editor.putString("account", account);
                    editor.putString("password", password);
                    editor.putString("MODE", MODE);
                    editor.commit();
                    Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MessageListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Log.d("toast", String.valueOf(isLogin));
                    Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                isLogin=false;
                Toast.makeText(getActivity(), "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void changeCardView(String mode)
    {
        if (mode.equals("student")) {
            studentCheck.setBackgroundResource(R.drawable.checkbox_checked);
            teacherCheck.setBackgroundResource(R.drawable.checkbox_uncheck);
            studentsetting.setEnabled(true);
            spinnerGrade.setEnabled(true);
            spinnerClass.setEnabled(true);
            teachersetting.setEnabled(false);
            accouttext.setEnabled(false);
            passwordtext.setEnabled(false);
        } else if (mode.equals("teacher")){
            studentCheck.setBackgroundResource(R.drawable.checkbox_uncheck);
            teacherCheck.setBackgroundResource(R.drawable.checkbox_checked);
            studentsetting.setEnabled(false);
            spinnerGrade.setEnabled(false);
            spinnerClass.setEnabled(false);
            teachersetting.setEnabled(true);
            accouttext.setEnabled(true);
            passwordtext.setEnabled(true);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
