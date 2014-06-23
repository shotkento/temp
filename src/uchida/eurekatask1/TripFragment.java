
package uchida.eurekatask1;

public class TripFragment extends BaseFragment {

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

    public TripFragment() {
        setsImages(sImages);
        setsDescriptions(sDescriptions);
    }
}
