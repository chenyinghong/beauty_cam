package com.example.beauty_cam.glcamera.utils;

import android.content.Context;

import com.example.beauty_cam.glcamera.filters.BaseFilter;
import com.example.beauty_cam.glcamera.filters.BeautyFilter;
import com.example.beauty_cam.glcamera.filters.BlackCatFilter;
import com.example.beauty_cam.glcamera.filters.BlackFilter;
import com.example.beauty_cam.glcamera.filters.HealthyFilter;
import com.example.beauty_cam.glcamera.filters.BrooklynFilter;
import com.example.beauty_cam.glcamera.filters.CalmFilter;
import com.example.beauty_cam.glcamera.filters.CoolFilter;
import com.example.beauty_cam.glcamera.filters.LatteFilter;
import com.example.beauty_cam.glcamera.filters.AmaroFilter;
import com.example.beauty_cam.glcamera.filters.AntiqueFilter;
import com.example.beauty_cam.glcamera.filters.BrannanFilter;
import com.example.beauty_cam.glcamera.filters.SweetsFilter;
import com.example.beauty_cam.glcamera.filters.WarmFilter;
import com.example.beauty_cam.glcamera.filters.RomanceFilter;
import com.example.beauty_cam.glcamera.filters.SakuraFilter;
import com.example.beauty_cam.glcamera.filters.SkinWhitenFilter;
import com.example.beauty_cam.glcamera.filters.OriginalFilter;
import com.example.beauty_cam.glcamera.filters.SunriseFilter;
import com.example.beauty_cam.glcamera.filters.SunsetFilter;
import com.example.beauty_cam.glcamera.filters.WhiteCatFilter;

public class FilterFactory {

    public enum FilterType {

        SkinWhiten,
        BlackWhite,
        BlackCat,
        WhiteCat,
        Healthy,
        Romance,
        Original,
        Sunrise,
        Sunset,
        Sakura,
        Latte,
        Warm,
        Calm,
        Brooklyn,
        Cool,
        Sweets,
        Amaro,
        Antique,
        Brannan,
        Beauty
    }

    public static BaseFilter createFilter(Context c, FilterType filterType) {

        BaseFilter baseFilter = null;

        switch (filterType) {

            case Sakura:

                baseFilter = new SakuraFilter(c);
                break;
            case Sunset:

                baseFilter = new SunsetFilter(c);
                break;

            case Healthy:

                baseFilter = new HealthyFilter(c);

                break;
            case Romance:

                baseFilter = new RomanceFilter(c);

                break;

            case Sunrise:

                baseFilter = new SunriseFilter(c);

                break;
            case BlackCat:

                baseFilter = new BlackCatFilter(c);

                break;

            case Original:

                baseFilter = new OriginalFilter(c);

                break;

            case WhiteCat:

                baseFilter = new WhiteCatFilter(c);

                break;

            case BlackWhite:

                baseFilter = new BlackFilter(c);

                break;

            case SkinWhiten:

                baseFilter = new SkinWhitenFilter(c);

                break;

            case Calm:

                baseFilter = new CalmFilter(c);
                break;

            case Warm:

                baseFilter = new WarmFilter(c);

                break;
            case Latte:

                baseFilter = new LatteFilter(c);

                break;

            case Cool:

                baseFilter = new CoolFilter(c);
                break;

            case Sweets:

                baseFilter = new SweetsFilter(c);
                break;

            case Brooklyn:

                baseFilter = new BrooklynFilter(c);

                break;
            case Amaro:

                baseFilter = new AmaroFilter(c);
                break;

            case Antique:

                baseFilter = new AntiqueFilter(c);

                break;
            case Brannan:

                baseFilter = new BrannanFilter(c);

                break;
            case Beauty:

                baseFilter = new BeautyFilter(c);

                break;


        }

        return baseFilter;
    }


}
