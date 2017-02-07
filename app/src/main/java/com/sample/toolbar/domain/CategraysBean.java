package com.sample.toolbar.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/21.
 */
public class CategraysBean implements Parcelable {

    /**
     * code :
     * opflag : 1
     */

    private OpretBean opret;
    /**
     * categoryfavicon : http://lbs.talkie.cn:8888/lbsback/uploaddata/industry/2015-02-04/20150204113301928.png
     * categoryid : 10
     * categoryname : 美食
     * dsfshopcount : 17
     * hyfshopcount : 14
     * parentid : 0
     */

    private List<CategorynavsBean> categorynavs;
    /**
     * dsfshopcount : 18
     * hyfshopcount : 20
     * regionid : 2137
     * regionname : 国贸
     * regionorderid : 1
     */

    private List<RegionnavsBean> regionnavs;

    public OpretBean getOpret() {
        return opret;
    }

    public void setOpret(OpretBean opret) {
        this.opret = opret;
    }

    public List<CategorynavsBean> getCategorynavs() {
        return categorynavs;
    }

    public void setCategorynavs(List<CategorynavsBean> categorynavs) {
        this.categorynavs = categorynavs;
    }

    public List<RegionnavsBean> getRegionnavs() {
        return regionnavs;
    }

    public void setRegionnavs(List<RegionnavsBean> regionnavs) {
        this.regionnavs = regionnavs;
    }

    public static class OpretBean implements Parcelable {
        private String code;
        private String opflag;

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
            dest.writeString(this.code);
            dest.writeString(this.opflag);
        }

        public OpretBean() {
        }

        protected OpretBean(Parcel in) {
            this.code = in.readString();
            this.opflag = in.readString();
        }

        public static final Creator<OpretBean> CREATOR = new Creator<OpretBean>() {
            @Override
            public OpretBean createFromParcel(Parcel source) {
                return new OpretBean(source);
            }

            @Override
            public OpretBean[] newArray(int size) {
                return new OpretBean[size];
            }
        };
    }

    public static class CategorynavsBean implements Parcelable {
        private String categoryfavicon;
        private int categoryid;
        private String categoryname;
        private int dsfshopcount;
        private int hyfshopcount;
        private int parentid;

        public String getCategoryfavicon() {
            return categoryfavicon;
        }

        public void setCategoryfavicon(String categoryfavicon) {
            this.categoryfavicon = categoryfavicon;
        }

        public int getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(int categoryid) {
            this.categoryid = categoryid;
        }

        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }

        public int getDsfshopcount() {
            return dsfshopcount;
        }

        public void setDsfshopcount(int dsfshopcount) {
            this.dsfshopcount = dsfshopcount;
        }

        public int getHyfshopcount() {
            return hyfshopcount;
        }

        public void setHyfshopcount(int hyfshopcount) {
            this.hyfshopcount = hyfshopcount;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.categoryfavicon);
            dest.writeInt(this.categoryid);
            dest.writeString(this.categoryname);
            dest.writeInt(this.dsfshopcount);
            dest.writeInt(this.hyfshopcount);
            dest.writeInt(this.parentid);
        }

        public CategorynavsBean() {
        }

        protected CategorynavsBean(Parcel in) {
            this.categoryfavicon = in.readString();
            this.categoryid = in.readInt();
            this.categoryname = in.readString();
            this.dsfshopcount = in.readInt();
            this.hyfshopcount = in.readInt();
            this.parentid = in.readInt();
        }

        public static final Creator<CategorynavsBean> CREATOR = new Creator<CategorynavsBean>() {
            @Override
            public CategorynavsBean createFromParcel(Parcel source) {
                return new CategorynavsBean(source);
            }

            @Override
            public CategorynavsBean[] newArray(int size) {
                return new CategorynavsBean[size];
            }
        };
    }

    public static class RegionnavsBean implements Parcelable {
        private int dsfshopcount;
        private int hyfshopcount;
        private int regionid;
        private String regionname;
        private int regionorderid;

        public int getDsfshopcount() {
            return dsfshopcount;
        }

        public void setDsfshopcount(int dsfshopcount) {
            this.dsfshopcount = dsfshopcount;
        }

        public int getHyfshopcount() {
            return hyfshopcount;
        }

        public void setHyfshopcount(int hyfshopcount) {
            this.hyfshopcount = hyfshopcount;
        }

        public int getRegionid() {
            return regionid;
        }

        public void setRegionid(int regionid) {
            this.regionid = regionid;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public int getRegionorderid() {
            return regionorderid;
        }

        public void setRegionorderid(int regionorderid) {
            this.regionorderid = regionorderid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.dsfshopcount);
            dest.writeInt(this.hyfshopcount);
            dest.writeInt(this.regionid);
            dest.writeString(this.regionname);
            dest.writeInt(this.regionorderid);
        }

        public RegionnavsBean() {
        }

        protected RegionnavsBean(Parcel in) {
            this.dsfshopcount = in.readInt();
            this.hyfshopcount = in.readInt();
            this.regionid = in.readInt();
            this.regionname = in.readString();
            this.regionorderid = in.readInt();
        }

        public static final Creator<RegionnavsBean> CREATOR = new Creator<RegionnavsBean>() {
            @Override
            public RegionnavsBean createFromParcel(Parcel source) {
                return new RegionnavsBean(source);
            }

            @Override
            public RegionnavsBean[] newArray(int size) {
                return new RegionnavsBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.opret, flags);
        dest.writeList(this.categorynavs);
        dest.writeList(this.regionnavs);
    }

    public CategraysBean() {
    }

    protected CategraysBean(Parcel in) {
        this.opret = in.readParcelable(OpretBean.class.getClassLoader());
        this.categorynavs = new ArrayList<CategorynavsBean>();
        in.readList(this.categorynavs, CategorynavsBean.class.getClassLoader());
        this.regionnavs = new ArrayList<RegionnavsBean>();
        in.readList(this.regionnavs, RegionnavsBean.class.getClassLoader());
    }

    public static final Creator<CategraysBean> CREATOR = new Creator<CategraysBean>() {
        @Override
        public CategraysBean createFromParcel(Parcel source) {
            return new CategraysBean(source);
        }

        @Override
        public CategraysBean[] newArray(int size) {
            return new CategraysBean[size];
        }
    };
}
