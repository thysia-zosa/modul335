package org.rafisa.test3;

import java.util.List;

import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class MyOwnItemizedOverlay extends ItemizedIconOverlay<OverlayItem> {
    protected Context mContext;
    List<OverlayItem> items;

    public MyOwnItemizedOverlay(final Context context, List<OverlayItem> aList, Drawable marker, ResourceProxy proxy) {
        super(aList,marker, new OnItemGestureListener<OverlayItem>() {
            @Override public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                return false;
            }
            @Override public boolean onItemLongPress(final int index, final OverlayItem item) {
                return false;
            }
        },proxy);
        mContext = context;
        items = aList;
    }

    public void addItem(GeoPoint geoPoint, String a, String b){
        OverlayItem olItem = new OverlayItem(a, b, geoPoint);
        items.add(olItem);
        populate();
    }

    public List<OverlayItem> getItems(){
        return items;
    }

    @Override
    protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView) {
        items.remove(item);
        populate();
        return true;
    }
}