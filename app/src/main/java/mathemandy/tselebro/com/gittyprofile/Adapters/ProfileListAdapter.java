package mathemandy.tselebro.com.gittyprofile.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mathemandy.tselebro.com.gittyprofile.Model.ProfileItem;
import mathemandy.tselebro.com.gittyprofile.R;

/**
 * Created by mathemandy on 09 Mar 2017.
 */
public class ProfileListAdapter extends BaseAdapter {

    private List<ProfileItem> profile = new ArrayList<>();
    private boolean mNotifyOnChange = true;

    private Context mContext;

    public ProfileListAdapter(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public void swapData(List<ProfileItem> gitProfile){
        this.profile = gitProfile;
        notifyDataSetChanged();
    }

    public void cleanData() {
        this.profile.clear();
        if (mNotifyOnChange) notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return profile.size();
    }

    @Override
    public Object getItem(int i) {
        return profile.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        MyViewHolder Holder;

        if (view == null ){
            view = inflater.inflate(R.layout.profile_item, viewGroup, false);
            Holder = new MyViewHolder(view);
            view.setTag(Holder);
        }else {
            Holder = (MyViewHolder) view.getTag();
        }
        String imgUrl = profile.get(i).getAvatarPath();
//        Set Image Using Picasso
        Picasso.with(mContext).load(imgUrl).into(Holder.profile_photo);

//        Bind the username to the TextView
        Holder.username.setText(profile.get(i).getUsername());
        return view;
    }

    public static class MyViewHolder {
        public TextView username;
        public ImageView profile_photo;

        public MyViewHolder(View itemView) {
            username = (TextView) itemView.findViewById(R.id.user_name);
            profile_photo = (ImageView) itemView.findViewById(R.id.profile_photo);
        }
    }
}
