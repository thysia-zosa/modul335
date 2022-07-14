package org.rafisa.richtungsanzeiger.widgets;

import android.view.View;

public interface LocationViewListener {

    void showLocation(View view, int position);

    void editLocation(View view, int position);
}
