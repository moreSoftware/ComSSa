package simplesns.uiseon.com.simplesns.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


import simplesns.uiseon.com.simplesns.R;
import simplesns.uiseon.com.simplesns.manager.AutoLayout;


public class CalendarActivity extends Activity implements View.OnClickListener,

        AdapterView.OnItemClickListener {


    ArrayList<String> mItems;

    ArrayAdapter<String> adapter;

    TextView textYear;
    AutoLayout autoLayout = AutoLayout.GetInstance();
    static String id= null;
    TextView textMon;

    int year, mon;
    ArrayList<CalendarDTO> calendarList;
    CalendarActivity.CalendarAdapter calendarAdapter;
    ListView mainListView;

    /** Called when the activity is first created. */

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();
        id =intent.getExtras().getString("id");

        textYear = (TextView) this.findViewById(R.id.edit1);
        textMon = (TextView) this.findViewById(R.id.edit2);
        mItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, mItems);
        GridView gird = (GridView) this.findViewById(R.id.grid1);
        gird.setAdapter(adapter);
        gird.setOnItemClickListener(this);

        View view = (View) findViewById(android.R.id.content);
        autoLayout.setView(view);


        Date date = new Date();// 오늘에 날짜를 세팅 해준다.
        year = date.getYear() + 1900;
        mon = date.getMonth() + 1;
        textYear.setText(year + "");
        textMon.setText(mon + "");

        fillDate(year, mon);

        Log.v("qwer",year+""+mon);
        requestGet(year,mon);


        Button btnmove = (Button) this.findViewById(R.id.bt1);
        btnmove.setOnClickListener(this);

        mainListView = (ListView) findViewById(R.id.calendar_list_view);
        calendarAdapter = new CalendarActivity.CalendarAdapter(this, R.layout.list_item_calendar, calendarList);
        mainListView.setAdapter(calendarAdapter);
    }


    @Override

    public void onClick(View arg0) {

// TODO Auto-generated method stub

        if (arg0.getId() == R.id.bt1) {

            year = Integer.parseInt(textYear.getText().toString());

            mon = Integer.parseInt(textMon.getText().toString());

            fillDate(year, mon);

        }




    }


    public void beforeMClicked(View v){
        mon -= 1;
        if(mon==0) {
            mon=12; year -=1;
        }
        fillDate(year, mon);
        mainListView.setAdapter(null);
        calendarList = new ArrayList<CalendarDTO>();
        requestGet(year,mon);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter = new CalendarActivity.CalendarAdapter(this, R.layout.list_item_calendar, calendarList);
        mainListView.setAdapter(calendarAdapter);


    }

    public void nextMClicked(View v){
        mon+= 1;
        if(mon==13) {
            mon=1;   year +=1;
        }
        fillDate(year, mon);
        mainListView.setAdapter(null);
        calendarList = new ArrayList<CalendarDTO>();
        requestGet(year,mon);
        calendarAdapter.notifyDataSetChanged();
        calendarAdapter = new CalendarActivity.CalendarAdapter(this, R.layout.list_item_calendar, calendarList);
        mainListView.setAdapter(calendarAdapter);
    }




    private void fillDate(int year, int mon) {

        String Y = Integer.toString(year);
        String M = Integer.toString(mon);

        mItems.clear();
        mItems.add(" ");
        mItems.add(Y.substring(0,2));
        mItems.add(Y.substring(2,4));
        mItems.add("년");
        mItems.add(M);
        mItems.add("월");
        mItems.add(" ");

        mItems.add("일");

        mItems.add("월");

        mItems.add("화");

        mItems.add("수");

        mItems.add("목");

        mItems.add("금");

        mItems.add("토");


        Date current = new Date(year - 1900, mon - 1, 1);
        int day = current.getDay(); // 요일도 int로 저장.

        for (int i = 0; i < day; i++) {

            mItems.add("");

        }


        current.setDate(32);// 32일까지 입력하면 1일로 바꿔준다.

        int last = 32 - current.getDate();




        for (int i = 1; i <= last; i++) {

            mItems.add(i + "");

        }

        adapter.notifyDataSetChanged();




    }




    @Override

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

// TODO Auto-generated method stub

        if (mItems.get(arg2).equals("")) {
            ;
        } else {

            Intent intent = new Intent(this, Detail.class);//해당 일을 눌렸을때

            intent.putExtra("Param1", textYear.getText().toString() + "/"

                    + textMon.getText().toString() + "/" + mItems.get(arg2));
            intent.putExtra("id",id);

            startActivity(intent);

        }

    }

    public void requestGet(int year, int mon) {
        String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/usercalendarselect.jsp"+"?year="+year+"&mon="+mon;
        Log.i("xxx", "requestGet start");
        Log.i("xxx",year+" sss"+mon);
        try {
            //1.
            HttpClient client = new DefaultHttpClient();

            HttpGet get = new HttpGet(requestURL);
            //2. 요청
            HttpResponse response = client.execute(get);
            //3. 결과 받기
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            getXML(is);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }//end requestGet()
    public void getXML(InputStream is) {
        calendarList = new ArrayList<CalendarDTO>();

        Log.i("xxx", "getXML start!");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            CalendarDTO dto = null;
            while( eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = parser.getName();
                        if(startTag.equals("record")){ dto = new CalendarDTO(); }
                        if(dto !=null ) {


                            if(startTag.equals("schNumber")){ dto.setSchNumber(Integer.parseInt(parser.nextText()));}
                            if(startTag.equals("editTitle")){ dto.setEditTitle(parser.nextText()); }
                            if(startTag.equals("editDate")){ dto.setEditDate(parser.nextText()); }
                            if(startTag.equals("editClock")){ dto.setEditClock(parser.nextText()); }
                            if(startTag.equals("editMemo")){ dto.setEditMemo(parser.nextText()); }
                            if(startTag.equals("editUser")){ dto.setEditUser(parser.nextText()); }
                        } else { Log.i("xxx", "dto = null"); }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if(endTag.equals("record")){
                            calendarList.add(dto);
                        }
                }//end switch
                eventType = parser.next();
            }//end while
            for( CalendarDTO xx : calendarList){
                Log.i("xxx",xx.getEditDate()+" "+xx.getEditMemo()+" ");
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        calendarAdapter.notifyDataSetChanged();
    }

     class CalendarDTO {

        private int schNumber;
        private String editTitle;
        private String editDate;
        private String editClock;
        private String editMemo;
        private String editUser;


        CalendarDTO(int schNumber, String editTitle, String editDate, String editClock, String editMemo,String editUser) {
            super();
            this.schNumber = schNumber;
            this.editTitle = editTitle;
            this.editDate = editDate;
            this.editClock= editClock;
            this.editMemo=editMemo;
            this.editUser = editUser;
        }

         CalendarDTO() {
            super();
        }




        public int getSchNumber() {
            return schNumber;
        }

        public String getEditTitle() {
            return editTitle;
        }

        public String getEditDate() {
            return editDate;
        }

        public String getEditClock() {
            return editClock;
        }

        public String getEditUser() {
            return editUser;
        }

        public String getEditMemo(){
            return editMemo;
        }
         public void setSchNumber(int schNumber) {
             this.schNumber = schNumber;
         }

         public void setEditTitle(String editTitle) {
             this.editTitle = editTitle;
         }

         public void setEditDate(String editDate)  {this.editDate = editDate;}

         public void setEditClock(String editClock) {
             this.editClock = editClock;
         }

         public void setEditMemo(String editMemo) {
             this.editMemo = editMemo;
         }
         public void setEditUser(String editUser) {this.editUser = editUser;}
    }
    class CalendarAdapter extends ArrayAdapter<CalendarDTO> {


        ArrayList<CalendarDTO> items;


        public CalendarAdapter(Context context, int textViewResourceId , ArrayList<CalendarDTO> items) {
            super(context,textViewResourceId,items);
            this.items =items;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item_calendar, null);
                autoLayout.setView(v);
            }


            CalendarDTO calendarDTO = items.get(position);

            if (calendarDTO != null) {

//                TextView schNumberTextView = (TextView) v.findViewById(R.id.board_numberText);
//                board_numberTextView.setText(Integer.toString(calendarDTO.getSchNumber()));


                TextView editDateTextView = (TextView) v.findViewById(R.id.editDate);
                editDateTextView.setText(calendarDTO.getEditDate()+" "+calendarDTO.getEditClock());


                TextView editMemo = (TextView) v.findViewById(R.id.editMemo);
                editMemo.setText(calendarDTO.getEditMemo());

                TextView editUser = (TextView) v.findViewById(R.id.editUser);
                editUser.setText(calendarDTO.getEditUser());




            }
            return v;
        }
    }
    protected void onResume() {
        super.onResume();
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestGet(year,mon);
       calendarAdapter.notifyDataSetChanged();


    }

}