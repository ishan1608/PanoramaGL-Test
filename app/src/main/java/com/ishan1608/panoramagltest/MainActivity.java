package com.ishan1608.panoramagltest;

import com.panoramagl.PLView;
import com.panoramagl.loaders.PLILoader;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.transitions.PLTransitionBlend;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends PLView
{
    /**init methods*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*//Load panorama
        PLSpherical2Panorama panorama = new PLSpherical2Panorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.raw.casabella_hall_2048), false));
        this.setPanorama(panorama);*/
    }

    /**
     * This event is fired when root content view is created
     * @param contentView current root content view
     * @return root content view that Activity will use
     */
    @Override
    protected View onContentViewCreated(View contentView)
    {
        //Load layout
        ViewGroup mainView = (ViewGroup)this.getLayoutInflater().inflate(R.layout.activity_main, null);
        //Add 360 view
        mainView.addView(contentView, 0);

        loadSpherical2FromJson();

        //Return root content view
        return super.onContentViewCreated(mainView);
    }

    private void loadSpherical2FromJson() {
        try
        {
            PLILoader loader;
            loader = new PLJSONLoader("res://raw/json_spherical2");
            this.load(loader, true, new PLTransitionBlend(2.0f));
        }
        catch(Throwable e)
        {
            Toast.makeText(this.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}