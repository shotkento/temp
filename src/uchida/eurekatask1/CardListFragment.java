
package uchida.eurekatask1;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ListFragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CardListFragment extends ListFragment {
    final static int[] sImages = {
            R.drawable.souya, R.drawable.hateruma, R.drawable.garage, R.drawable.goraikou,
            R.drawable.gunkanjima, R.drawable.kesennuma, R.drawable.kokudou, R.drawable.mayakan,
            R.drawable.miyako, R.drawable.nagisa, R.drawable.nebuta, R.drawable.oni,
            R.drawable.pontiacs, R.drawable.shirakawagou
    };

    final static String[] sDescriptions = {
            "宗谷岬", "波照間島", "奄美島のガレージ", "富士山",
            "軍艦島", "気仙沼", "日本国道最高地点", "摩耶観光ホテル",
            "宮古島", "なぎさドライブウェイ", "ねぶた", "やさしい鬼",
            "PONTIACS", "白川郷"
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // ListViewの形成
        ListView listView = getListView();
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setSelector(android.R.color.transparent);

        // List作成
        List<Album> list = new ArrayList<Album>();
        for (int i = 0; i < sImages.length; i++) {
            list.add(new Album(sImages[i], sDescriptions[i]));
        }

        // Adapterを生成してセット
        setListAdapter(new CustomAdapter(getActivity(), list));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // 選択された１行を取得
        Album album = (Album) l.getItemAtPosition(position);

        // イメージ取得
        ImageView iv = new ImageView(getActivity());
        iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), album.getImage()));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setAdjustViewBounds(true);

        // ダイアログ表示
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(iv);
        dialog.show();
    }

    public static class Album {
        private int image;
        private String description;

        public Album(int image, String description) {
            super();
            this.image = image;
            this.description = description;
        }

        public int getImage() {
            return image;
        }

        public String getDescription() {
            return description;
        }

        public void setImage(int image) {
            this.image = image;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class CustomAdapter extends ArrayAdapter<Album> {
        LayoutInflater mInflater;

        public CustomAdapter(Context context, List<Album> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item, null);
            }

            // 写真とテキストをセットする
            final Album album = (Album) this.getItem(position);
            if (album != null) {
                ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
                imageView.setImageResource(album.getImage());

                TextView textView = (TextView) convertView.findViewById(R.id.description);
                textView.setText(album.getDescription());
            }

            return convertView;
        }

    }
}
