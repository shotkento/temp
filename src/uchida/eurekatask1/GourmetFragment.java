
package uchida.eurekatask1;

import java.util.ArrayList;

public class GourmetFragment extends BaseFragment {

    GourmetFragment(ArrayList<Dribbble> dribbbleList) {
        super(dribbbleList);
        // TODO 自動生成されたコンストラクター・スタブ
    }

    final static int[] sImages = {
            R.drawable.g_akashiyaki, R.drawable.g_hiroshima, R.drawable.g_inaniwa,
            R.drawable.g_katsuo, R.drawable.g_okinawa, R.drawable.g_pizza, R.drawable.g_reimen,
            R.drawable.g_sanuki, R.drawable.g_shiroebi, R.drawable.g_todo, R.drawable.g_tonkotsu,
            R.drawable.g_uni, R.drawable.g_usoba
    };

    final static String[] sDescriptions = {
            "明石焼", "広島風お好み焼き", "稲庭うどん",
            "鰹のたたき", "沖縄そば", "ピザ", "盛岡冷麺",
            "讃岐うどん", "白エビのかき揚げ丼", "トド肉", "博多ラーメン",
            "うに丼", "うそば"
    };

}
