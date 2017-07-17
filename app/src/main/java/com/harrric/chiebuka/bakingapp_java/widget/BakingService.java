package com.harrric.chiebuka.bakingapp_java.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by chiebuka on 6/27/17.
 */

public class BakingService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this);
    }
}
