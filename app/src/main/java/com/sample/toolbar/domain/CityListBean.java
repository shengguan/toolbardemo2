package com.sample.toolbar.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class CityListBean implements Parcelable {


    /**
     * code :
     * opflag : 1
     */

    private ResultBean opret;
    /**
     * cityareacode : 021
     * cityid : 1004
     * cityname : 上海
     * firstchar : S
     * ishot : 1
     * ispromo : 1
     * lat : 31.230707
     * lng : 121.472916
     * region : 上海
     * topcity : 1
     */

    private List<CityBean> cities;

    public ResultBean getOpret() {
        return opret;
    }

    public void setOpret(ResultBean opret) {
        this.opret = opret;
    }

    public List<CityBean> getCities() {
        return cities;
    }

    public void setCities(List<CityBean> cities) {
        this.cities = cities;
    }

    public static class ResultBean implements Parcelable {
        private String code;
        private String opflag;

        protected ResultBean(Parcel in) {
            code = in.readString();
            opflag = in.readString();
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel in) {
                return new ResultBean(in);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOpflag() {
            return opflag;
        }

        public void setOpflag(String opflag) {
            this.opflag = opflag;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(code);
            dest.writeString(opflag);
        }
    }

    public static class CityBean {
        private String cityareacode;
        private int cityid;
        private String cityname;
        private String firstchar;
        private int ishot;
        private int ispromo;
        private String lat;
        private String lng;
        private String region;
        private int topcity;

        public String getCityareacode() {
            return cityareacode;
        }

        public void setCityareacode(String cityareacode) {
            this.cityareacode = cityareacode;
        }

        public int getCityid() {
            return cityid;
        }

        public void setCityid(int cityid) {
            this.cityid = cityid;
        }

        public String getCityname() {
            return cityname;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getFirstchar() {
            return firstchar;
        }

        public void setFirstchar(String firstchar) {
            this.firstchar = firstchar;
        }

        public int getIshot() {
            return ishot;
        }

        public void setIshot(int ishot) {
            this.ishot = ishot;
        }

        public int getIspromo() {
            return ispromo;
        }

        public void setIspromo(int ispromo) {
            this.ispromo = ispromo;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getTopcity() {
            return topcity;
        }

        public void setTopcity(int topcity) {
            this.topcity = topcity;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.cities);
        dest.writeParcelable(this.opret, flags);
    }

    public CityListBean() {
    }

    protected CityListBean(Parcel in) {
        this.cities = new ArrayList<CityBean>();
        in.readList(this.cities, CityBean.class.getClassLoader());
        this.opret = in.readParcelable(ResultBean.class.getClassLoader());
    }

    public static final Creator<CityListBean> CREATOR = new Creator<CityListBean>() {
        @Override
        public CityListBean createFromParcel(Parcel source) {
            return new CityListBean(source);
        }

        @Override
        public CityListBean[] newArray(int size) {
            return new CityListBean[size];
        }
    };
}
