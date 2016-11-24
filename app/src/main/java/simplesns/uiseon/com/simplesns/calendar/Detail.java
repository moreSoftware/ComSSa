package simplesns.uiseon.com.simplesns.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import simplesns.uiseon.com.simplesns.R;

public class Detail extends Activity implements View.OnClickListener { // 일정목록 추가하기

    MyDBHelper mDBHelper;

    int mId;

    String today;

    String id = null;

    String msgString= null;

    EditText editDate, editTitle, editTime, editMemo;

    String a,b,c,d;


    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();

        id= intent.getStringExtra("id");

        today = intent.getStringExtra("Param1");

        editTitle = (EditText) findViewById(R.id.edittitle);

        editDate = (EditText) findViewById(R.id.editdate);

        editTime = (EditText) findViewById(R.id.edittime);

        editMemo = (EditText) findViewById(R.id.editmemo);

        editDate.setText(today);



        Button btn1 = (Button) findViewById(R.id.btnsave);

        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btndel);

        btn2.setOnClickListener(this);

        Button btn3 = (Button) findViewById(R.id.btncancel);

        btn3.setOnClickListener(this);


    }


    public void comeon(String a, String b, String c, String d, String e){

        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("editTitle", a));
        list.add(new BasicNameValuePair("editDate", b));
        list.add(new BasicNameValuePair("editClock", c));
        list.add(new BasicNameValuePair("editMemo", d));
        list.add(new BasicNameValuePair("editUser", e));

        Log.i("editTitle", a);
        Log.i("editDate", b);
        Log.i("editClock",c);

        String url = "http://27.1.165.192:8082/99JSP_myEMP/usercalendaradd.jsp" + "?" + URLEncodedUtils.format(list, "UTF-8");
//                              String url = "http://125.179.204.2:8080/AndroidServer/login.jsp?id=mygirl2&pwd=1111";

        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = null;
        String s ="";
        StringBuffer sb = null;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream is;
            BufferedReader br = null;
            if(entity != null) {
                is = entity.getContent();
                br =  new BufferedReader(new InputStreamReader(is));
                s = "";
                sb = new StringBuffer();
            }
            while((s = br.readLine()) != null) {
                sb.append(s);
            }
            Log.i("sb : ", sb.toString());
            msgString = sb.toString().trim().toString();
            Log.i("msgString : ", msgString);

        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    @Override

    public void onClick(View v) {

        a = editTitle.getText().toString();
        b = editDate.getText().toString();
        c = editTime.getText().toString();
        d = editMemo.getText().toString();
        Log.v("xxx",a);
        Log.v("xxxx",b);
        Log.v("xxxxx",c);


// TODO Auto-generated method stub





        switch (v.getId()) {

            case R.id.btnsave:
                comeon(a,b,c,d,id);
                Intent intent_save = new Intent(getApplicationContext(), CalendarActivity.class);
                intent_save.putExtra("id",id);
                startActivity(intent_save);
                finish(); //이따수정하기
                break;
//                if (mId != -1) {
//
//                    db.execSQL("UPDATE today SET title='"
//
//                            + editTitle.getText().toString() + "',date='"
//
//                            + editDate.getText().toString() + "', time='"
//
//                            + editTime.getText().toString() + "', memo='"
//
//                            + editMemo.getText().toString() + "' WHERE _id='" + mId
//
//                            + "';");
//
//                } else {
//
//                    db.execSQL("INSERT INTO today VALUES(null, '"
//
//                            + editTitle.getText().toString() + "', '"
//
//                            + editDate.getText().toString() + "', '"
//
//                            + editTime.getText().toString() + "', '"
//
//                            + editMemo.getText().toString() + "');");
//
//                }
//
//                mDBHelper.close();
//
//                setResult(RESULT_OK);
//
//                break;


            case R.id.btndel:

//                if (mId != -1) {
//
//                    db.execSQL("DELETE FROM today WHERE _id='" + mId + "';");
//
//                    mDBHelper.close();
//
//                }
//
//                setResult(RESULT_OK);
//
//                break;
                break;

            case R.id.btncancel:

               Intent intent_cancel = new Intent(getApplicationContext(), CalendarActivity.class);
                intent_cancel.putExtra("id",id);
                startActivity(intent_cancel);
                finish(); //이따수정하기
                break;

        }



    }

}