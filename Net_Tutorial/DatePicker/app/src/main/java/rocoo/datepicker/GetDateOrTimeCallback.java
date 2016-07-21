package rocoo.datepicker;

/**
 * Created by geosat-rd01 on 2016/7/21.
 */
public interface GetDateOrTimeCallback {
     void onClickFromTime(int hour, int minute);
     void onClickFromDate(int year, int month, int day);
}
