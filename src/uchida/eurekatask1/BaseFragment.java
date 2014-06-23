
package uchida.eurekatask1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BaseFragment extends ListFragment {
    private int[] sImages = {};
    private String[] sDescriptions = {};

    public void setsImages(int[] sImages) {
        this.sImages = sImages;
    }

    public void setsDescriptions(String[] description) {
        this.sDescriptions = description;
    }

    private ArrayList<Dribbble> mDribbbleList;

    BaseFragment(ArrayList<Dribbble> dribbbleList) {
        this.mDribbbleList = dribbbleList;
        Log.d("test DA!", "test size=" + dribbbleList.size());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ListViewの形成
        ListView listView = getListView();
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setSelector(android.R.color.transparent);

        // // List作成
        // List<Album> list = new ArrayList<Album>();
        // for (int i = 0; i < sImages.length; i++) {
        // list.add(new Album(sImages[i], sDescriptions[i]));
        // }

        // Adapterを生成してセット
        setListAdapter(new CustomAdapter(getActivity(), mDribbbleList));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // // 選択された１行を取得
        // Dribbble dribbble = (Dribbble) l.getItemAtPosition(position);
        //
        // // イメージ取得
        // ImageView iv = new ImageView(getActivity());
        // iv.setImageBitmap(BitmapFactory.decodeResource(getResources(),
        // dribbble.getImage()));
        // iv.setScaleType(ImageView.ScaleType.FIT_XY);
        // iv.setAdjustViewBounds(true);
        //
        // // ダイアログ表示
        // Dialog dialog = new Dialog(getActivity());
        // dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // dialog.setContentView(iv);
        // dialog.show();
    }

    public static class Dribbble {
        private String title;
        private String image;
        private String likes;
        private String player;

        public Dribbble(String title, String image, String likes, String player) {
            super();
            this.title = title;
            this.image = image;
            this.likes = likes;
            this.player = player;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getLikes() {
            return likes;
        }

        public String getPlayer() {
            return player;
        }
    }

    static class ViewHolder {
        ImageView image;
        TextView title;
    }

    public class CustomAdapter extends ArrayAdapter<Dribbble> {
        LayoutInflater mInflater;

        public CustomAdapter(Context context, List<Dribbble> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.title = (TextView) convertView.findViewById(R.id.title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 写真とテキストをセットする
            final Dribbble dribbble = (Dribbble) this.getItem(position);
            if (dribbble != null) {
                holder.title.setText(dribbble.getTitle());
            }

            return convertView;
        }
    }
}
