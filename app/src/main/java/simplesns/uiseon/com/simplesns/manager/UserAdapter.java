package simplesns.uiseon.com.simplesns.manager;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import simplesns.uiseon.com.simplesns.R;

public class UserAdapter extends BaseAdapter {

    Context ctx;
    ArrayList<UserDTO> list;
    int layout;
    LayoutInflater inf;

    public UserAdapter(Context ctx, ArrayList<UserDTO> list, int layout) {
        super();
        this.ctx = ctx;
        this.list = list;
        this.layout = layout;
        inf = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView == null) { convertView = inf.inflate(layout, null); }



        TextView txt_userNumber = (TextView)convertView.findViewById(R.id.txt_userNumber);
        TextView txt_type = (TextView)convertView.findViewById(R.id.txt_type);
        TextView txt_id = (TextView)convertView.findViewById(R.id.txt_id);
        TextView txt_passwd = (TextView)convertView.findViewById(R.id.txt_passwd);
        TextView txt_passwdConfirm= (TextView)convertView.findViewById(R.id.txt_passwdConfirm);

        txt_userNumber.setText("userNumber : "+list.get(position).getUserNumber());
        txt_type.setText("type : "+list.get(position).getType());
        txt_id.setText("id : "+list.get(position).getId());
        txt_passwd.setText("passwd : "+list.get(position).getPasswd());
        txt_passwdConfirm.setText("passwdConfirm : "+list.get(position).getPasswdConfirm());


        return convertView;
    }

}
