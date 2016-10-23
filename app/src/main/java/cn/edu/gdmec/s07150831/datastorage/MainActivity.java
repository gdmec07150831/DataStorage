package cn.edu.gdmec.s07150831.datastorage;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        //实例化控件
        et1 = (EditText) findViewById(R.id.edittext1);
        et2 = (EditText) findViewById(R.id.edittext2);
        tv1 = (TextView) findViewById(R.id.textView);
    }
    //SharedPreferences写入读取
    public void spWrite(View v){
        //getSharedPreferences是Activity类的方法，用于获取SharedPreferences对象，
        //第一个参数是文件名，第二参数是操作模式，即文件的操作权限
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor editor = user.edit();  //调用此方法后处于编辑状态
        editor.putString("account",et1.getText().toString());   //写入配置信息
        editor.putString("pass",et2.getText().toString());
        editor.commit();    //提交配置文件
        Toast.makeText(MainActivity.this,"SharedPreferences写入成功",Toast.LENGTH_SHORT).show();
    }
    public void spRead(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_PRIVATE);
        String acount = user.getString("account","老子没有存这个键值，要命就一条");    //读取配置文件信息
        String pass = user.getString("pass","键值都没有哪来的密码");
        tv1.setText("账号："+acount+"\n"+"密码："+pass);
        Toast.makeText(MainActivity.this,"SharedPreferences读取成功",Toast.LENGTH_LONG);
    }
    public void ROMWrite(View v){
        String account = et1.getText().toString();
        String pass = et2.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("uset.txt",MODE_APPEND);  //定义一个文字字节输出流，名字为uset.txt
            OutputStreamWriter osw = new OutputStreamWriter(fos);   //将文件字节输出流转换为文件字符输出流
            BufferedWriter bw = new BufferedWriter(osw);    //再将文件字符输出流转成缓存字符输出流
            bw.write(account+":"+pass); //将信息写入文件
            bw.flush();
            fos.close();    //关闭输出流
            Toast.makeText(MainActivity.this,"ROM写入成功，哈哈",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ROMRead(View v){
        String acount = et1.getText().toString();
        String pass = et2.getText().toString();
        try {
            FileInputStream fis = openFileInput("uset.txt");
            InputStreamReader isr = new InputStreamReader(fis); //将字节流转换成字符流
            BufferedReader br = new BufferedReader(isr);    //转换成带缓存的br
            StringBuffer sb = new StringBuffer();
            String s;
            while ((s = br.readLine())!=null){
                sb.append(s+"\n");
            }
            fis.close();
            tv1.setText(sb);
            Toast.makeText(MainActivity.this,"ROM读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDWrite(View v){
        String str = et1.getText().toString()+":"+et2.getText().toString();
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();    //获取SD卡根目录
        String filename = sdCardRoot+"/test.txt";
        File file = new File(filename); //建立一个文件对象
        try {
            FileOutputStream fos =new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(MainActivity.this,"SD卡写入成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SDRead(View v){
        String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/test.txt";
        File file = new File(filename);
        int length = (int)file.length();
        byte[] b = new byte[length];    //使用字节数组存储读取出来的数据
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            tv1.setText(new String(b));
            Toast.makeText(MainActivity.this,"SD卡读取成功",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
