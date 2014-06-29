
package uchida.eurekatask1;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MyFragment extends ListFragment {

    final String TAG = "MyFragment";
    private List<Dribbble> mDribbbleList;
    private RequestQueue mQueue;
    private String mUrl;
    private String mCategory;
    private ImageLoader mImageLoader;
    private SimpleDatabaseHelper mHelper;
    private String[] mCols = {
            Constants.TITLE, Constants.IMAGE_URL, Constants.LIKES_COUNT, Constants.NAME
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mHelper = new SimpleDatabaseHelper(getActivity());
        mDribbbleList = new ArrayList<Dribbble>();

        // ListViewの形成
        ListView listView = getListView();
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
        listView.setSelector(android.R.color.transparent);

        // URLの取得
        mUrl = getArguments().getString(getString(R.string.KEY_URL));
        // カテゴリの取得
        mCategory = getArguments().getString(getString(R.string.KEY_CATEGORY));

        // リクエストの発行
        mQueue = Volley.newRequestQueue(getActivity());
        mQueue.add(getJsonRequest());
    }

    private JsonObjectRequest getJsonRequest() {
        return new JsonObjectRequest(Method.GET, mUrl, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            JSONArray shots = response.getJSONArray("shots");

                            for (int i = 0; i < shots.length(); i++) {
                                JSONObject shot = shots.getJSONObject(i);
                                JSONObject player = shot.getJSONObject("player");
                                ContentValues cv = new ContentValues();
                                cv.put("category", mCategory);
                                cv.put(Constants.TITLE, shot.getString(Constants.TITLE));
                                cv.put(Constants.IMAGE_URL, shot.getString(Constants.IMAGE_URL));
                                cv.put(Constants.LIKES_COUNT, shot.getString(Constants.LIKES_COUNT));
                                cv.put(Constants.NAME, player.getString(Constants.NAME));
                                db.insert(Constants.TBL_NAME, null, cv);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SQLiteDatabase db = mHelper.getReadableDatabase();
                        Cursor cs = db.query(Constants.DB_NAME, mCols, "category = ?",
                                new String[] {
                                    mCategory
                                }, null, null, null, null);
                        while (cs.moveToNext()) {
                            mDribbbleList.add(new Dribbble(cs.getString(0),
                                    cs.getString(1), cs.getString(2),
                                    cs.getString(3)));
                        }

                        // Adapterを生成してセット
                        setListAdapter(new CustomAdapter(getActivity(), mDribbbleList));
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // 選択された１行を取得
        Dribbble dribbble = (Dribbble) l.getItemAtPosition(position);

        // 選択画像表示Activityに遷移
        Intent intent = new Intent(getActivity(), uchida.eurekatask1.ViewerActivity.class);
        intent.putExtra(getString(R.string.KEY_URL), dribbble.getImage());
        startActivity(intent);
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

    public class BitmapCache implements ImageCache {
        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }
    }

    public class CustomAdapter extends ArrayAdapter<Dribbble> {
        LayoutInflater mInflater;

        public CustomAdapter(Context context, List<Dribbble> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mImageLoader = new ImageLoader(mQueue, new BitmapCache());
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

            Dribbble dribbble = (Dribbble) this.getItem(position);
            if (dribbble != null) {
                // テキストをセット
                holder.title.setText(dribbble.getTitle());
                holder.name.setText(dribbble.getPlayer());
                holder.likes.setText("Like: " + dribbble.getLikes());

                // 画像を取得
                ImageContainer imageContainer = (ImageContainer)
                        holder.image.getTag();
                if (imageContainer != null) {
                    imageContainer.cancelRequest();
                }

                ImageListener listener =
                        ImageLoader.getImageListener(holder.image,
                                android.R.drawable.spinner_background,
                                android.R.drawable.ic_dialog_alert);

                // 画像をセット
                holder.image.setTag(mImageLoader.get(dribbble.getImage(), listener));
            }

            return convertView;
        }
    }
}
