package vn.softlink.core.model.anotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/22
 * @Description: Wireless, Data network and telephone signal strength level
 * - Excellent  :   -50 ..  ~~  dBm
 * - Good       :   -50 .. -60  dBm
 * - Fair       :   -60 .. -70  dBm
 * - Weak       :    ~~ .. -70  dBm
 * All Right Reserved.
 * *******************************************************************
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Signal.WEAK,
        Signal.FAIR,
        Signal.GOOD,
        Signal.EXCELLENT
})
public @interface Signal {
    int WEAK = 0;
    int FAIR = 1;
    int GOOD = 2;
    int EXCELLENT = 3;
}