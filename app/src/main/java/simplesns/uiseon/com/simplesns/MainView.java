package simplesns.uiseon.com.simplesns;

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

import simplesns.uiseon.com.simplesns.calendar.CalendarActivity;
import simplesns.uiseon.com.simplesns.manager.AutoLayout;
import simplesns.uiseon.com.simplesns.manager.BoardDTO;

public class MainView extends Activity {
    AutoLayout autoLayout = AutoLayout.GetInstance();
    static String id= null;
    ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
    BoardAdapter boardAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestGet();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        View view = (View) findViewById(android.R.id.content);
        autoLayout.setView(view);


        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        Log.v("xxx", id);

        Button writeBtn = (Button) findViewById(R.id.main_button);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        ListView mainListView = (ListView) findViewById(R.id.main_list_view);
        boardAdapter = new BoardAdapter(this, R.layout.list_item_main, boardList);
        mainListView.setAdapter(boardAdapter);




        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < boardList.size()) {
                    BoardDTO boardDTO = boardList.get(position);
                    //본인 ID
                    String loginId = MainView.id;
                    //작성자 ID
                    String write_user_id = boardDTO.getWrite_user_id();
                    //글 번호
                    int board_number = boardDTO.getBoard_number();
                    //내용
                    String storyText = boardDTO.getSubstance();
                    //글 확인 했는지 체크
                    String notice_check = boardDTO.getNotice_check();
                    //글 쓴 시간
                    String writeDate = boardDTO.getDatetime();


                    Intent intent = new Intent(MainView.this, ViewActivity.class);
                    //본인 ID
                    intent.putExtra("id", loginId);
                    //작성자 ID
                    intent.putExtra("userName", write_user_id);
                    //게시글 번호
                    intent.putExtra("board_number", board_number);
                    //확인 했는지 체크
                    intent.putExtra("notice_check", notice_check);
                    //작성된 날짜
                    intent.putExtra("writeDate", writeDate);
                    //내용 넣기
                    intent.putExtra("storyText", storyText);
                    startActivity(intent);
                }
            }
        });

    }





    protected void onResume() {
        super.onResume();
        if(android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestGet();
        boardAdapter.notifyDataSetChanged();





    }
    public void requestGet() {
        String requestURL = "http://27.1.165.192:8082/99JSP_myEMP/userboardselect.jsp";
        Log.i("xxx", "requestGet start");
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

        Log.i("xxx", "getXML start!");
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(is, "UTF-8");
            int eventType = parser.getEventType();
            BoardDTO dto = null;
            while( eventType != XmlPullParser.END_DOCUMENT) {
                switch(eventType) {
                    case XmlPullParser.START_TAG:
                        String startTag = parser.getName();
                        if(startTag.equals("record")){ dto = new BoardDTO(); }
                        if(dto !=null ) {
                            if(startTag.equals("board_number")){ dto.setBoard_number(Integer.parseInt(parser.nextText()));}
                            if(startTag.equals("substance")){ dto.setSubstance(parser.nextText()); }
                            if(startTag.equals("write_user_id")){ dto.setWrite_user_id(parser.nextText()); }
                            if(startTag.equals("notice_check")){ dto.setNotice_check(parser.nextText()); }
                            if(startTag.equals("date")){ dto.setDatetime(parser.nextText()); }
                        } else { Log.i("xxx", "dto = null"); }
                        break;
                    case XmlPullParser.END_TAG:
                        String endTag = parser.getName();
                        if(endTag.equals("record")){
                            boardList.add(dto);
                        }
                }//end switch
                eventType = parser.next();
            }//end while
            for( BoardDTO xx : boardList){
                Log.i("xxx",xx.getBoard_number()+" "+xx.getSubstance()+" "+xx.getWrite_user_id()+ " "+ xx.getDatetime()+" "+xx.getNotice_check()+" ");
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException ie) {
            // TODO Auto-generated catch block
            ie.printStackTrace();
        }

        boardAdapter.notifyDataSetChanged();
    }

    public void logoutClicked(View v){
        Intent intent_new = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent_new);
        finish();
    }


    public void refreshClicked (View v){
        Intent intent_refresh = new Intent(getApplicationContext(), MainView.class);
        intent_refresh.putExtra("id",id);
        startActivity(intent_refresh);
    }


    public void calendarClicked (View v){
        Intent intent_calendar = new Intent(getApplicationContext(), CalendarActivity.class);
        intent_calendar.putExtra("id",id);
        startActivity(intent_calendar);
    }

    public void checkClicked(View v){
        Intent intent_check = new Intent(getApplicationContext(), check.class);
        startActivity(intent_check);
    }
    public void noticeClicked(View v){
        Intent intent_notice = new Intent(getApplicationContext(), NoticeActivity.class);
        intent_notice.putExtra("id",id);
        startActivity(intent_notice);
    }

    class BoardAdapter extends ArrayAdapter<BoardDTO> {


        ArrayList<BoardDTO> items;


        public BoardAdapter(Context context, int textViewResourceId , ArrayList<BoardDTO> items) {
            super(context,textViewResourceId,items);
            this.items =items;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.list_item_main, null);
                autoLayout.setView(v);
            }


            BoardDTO boardDTO = items.get(position);

            if (boardDTO != null) {

//                TextView board_numberTextView = (TextView) v.findViewById(R.id.board_numberText);
//                board_numberTextView.setText(Integer.toString(boardDTO.getBoard_number()));
                //나중에 이용합시다

                TextView substanceTextView = (TextView) v.findViewById(R.id.substanceText);
                substanceTextView.setText(boardDTO.getSubstance());

                TextView write_user_idText = (TextView) v.findViewById(R.id.write_user_idText);
                write_user_idText.setText(boardDTO.getWrite_user_id());

                TextView notice_checkText = (TextView) v.findViewById(R.id.notice_check);
                if(boardDTO.getNotice_check().equals("0"))
                    notice_checkText.setText(" ");





                TextView datetimeText = (TextView) v.findViewById(R.id.datetimeText);
                datetimeText.setText(boardDTO.getDatetime());

            }
            return v;
        }
    }
}

