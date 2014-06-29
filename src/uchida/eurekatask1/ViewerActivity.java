
package uchida.eurekatask1;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class ViewerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = this.getIntent().getStringExtra(getString(R.string.KEY_URL));

        NetworkImageView view = (NetworkImageView) findViewById(R.id.network_image_view);
        view.setImageUrl(url, new ImageLoader(queue, new ImageCache() {

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }
        }));
    }
}
