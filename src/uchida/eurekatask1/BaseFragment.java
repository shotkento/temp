
package uchida.eurekatask1;

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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

public class BaseFragment extends ListFragment {

    private List<Dribbble> mDribbbleList;

    BaseFragment(List<Dribbble> dribbbleList) {
        this.mDribbbleList = dribbbleList;
        Log.d("test DA!", "test size=" + dribbbleList.size());
    }

    public BaseFragment() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ListViewの形成
        ListView listView = getListView();
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setSelector(android.R.color.transparent);

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
        TextView name;
        TextView likes;
    }

    public class CustomAdapter extends ArrayAdapter<Dribbble> {
        LayoutInflater mInflater;
        private ImageLoader mImageLoader;

        public CustomAdapter(Context context, List<Dribbble> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mImageLoader = new ImageLoader(null, null);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.likes = (TextView) convertView.findViewById(R.id.likes);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // 写真とテキストをセットする
            final Dribbble dribbble = (Dribbble) this.getItem(position);
            if (dribbble != null) {
                holder.title.setText(dribbble.getTitle());
                holder.name.setText(dribbble.getPlayer());
                holder.likes.setText("Like: " + dribbble.getLikes());

                ImageContainer imageContainer = (ImageContainer) holder.image.getTag();
                if (imageContainer != null) {
                    imageContainer.cancelRequest();
                }

                Log.d("test", "test image =" + dribbble.getImage());
                // ImageListener listener =
                // ImageLoader.getImageListener(holder.image,
                // android.R.drawable.spinner_background /* 表示待ち時の画像 */,
                // android.R.drawable.ic_dialog_alert /* エラー時の画像 */);
                //
                // Log.d("test", "test koko");
                // holder.image.setTag(mImageLoader.get(dribbble.getImage(),
                // listener));

                Log.d("test", "test saigo");
            }

            return convertView;
        }
    }
}
