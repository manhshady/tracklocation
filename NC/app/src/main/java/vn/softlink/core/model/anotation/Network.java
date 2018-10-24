package vn.softlink.core.model.anotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/22
 * @Description:
 * All Right Reserved.
 * *******************************************************************
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        Network.G_MOBILE,
        Network.MOBIFONE,
        Network.VIETNAM_MOBILE,
        Network.VINAPHONE,
        Network.VIETTEL,
})
public @interface Network {
    int G_MOBILE = 0;
    int MOBIFONE = 1;
    int VIETNAM_MOBILE = 2;
    int VINAPHONE = 3;
    int VIETTEL = 4;
}