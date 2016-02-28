package com.ishan1608.panoramagltest;

import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.loaders.PLILoader;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.loaders.PLLoaderListener;
import com.panoramagl.transitions.PLTransitionBlend;
import com.panoramagl.utils.PLUtils;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends PLView
{
    private static final String TAG = "MAIN_ACTIVITY";
    /**init methods*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    /**
     * This event is fired when root content view is created
     * @param contentView current root content view
     * @return root content view that Activity will use
     */
    @Override
    protected View onContentViewCreated(View contentView) {
        //Load layout
        ViewGroup mainView = (ViewGroup)this.getLayoutInflater().inflate(R.layout.activity_main, null);
        //Add 360 view
        mainView.addView(contentView, 0);

        loadSpherical2LocalJson();
//        loadSpherical2InternetJson();
//        loadSpherical2ImageFromJava();

        //Return root content view
        return super.onContentViewCreated(mainView);
    }

    private void loadSpherical2LocalJson() {
        try
        {
            PLILoader loader;
            loader = new PLJSONLoader("res://raw/json_spherical2");
            loader.setListener(new PLLoaderListener() {
                @Override
                public void didBegin(PLILoader loader) {
                    Log.d(TAG, "Loader begin");
                }

                @Override
                public void didComplete(PLILoader loader) {
                    Log.d(TAG, "Loader complete");
                    setScrollingEnabled(false);
                    stopSensorialRotation();
                }

                @Override
                public void didStop(PLILoader loader) {
                    Log.d(TAG, "Loader stop");
                }

                @Override
                public void didError(PLILoader loader, String error) {
                    Log.d(TAG, "Loader error : " + error);
                }
            });
            this.load(loader, true, new PLTransitionBlend(2.0f));
        }
        catch(Throwable e)
        {
            Toast.makeText(this.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSpherical2InternetJson() {
        try
        {
            PLILoader loader;
            loader = new PLJSONLoader("http://statichost.herokuapp.com/panoramagl/json_spherical2.json");
            loader.setListener(new PLLoaderListener() {
                @Override
                public void didBegin(PLILoader loader) {
                    Log.d(TAG, "Loader begin");
                }

                @Override
                public void didComplete(PLILoader loader) {
                    Log.d(TAG, "Loader complete");
                }

                @Override
                public void didStop(PLILoader loader) {
                    Log.d(TAG, "Loader stop");
                }

                @Override
                public void didError(PLILoader loader, String error) {
                    Log.d(TAG, "Loader error : " + error);
                }
            });
            this.load(loader, true, new PLTransitionBlend(2.0f));
        }
        catch(Throwable e)
        {
            Toast.makeText(this.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSpherical2ImageFromJava(){
        PLSpherical2Panorama panorama = new PLSpherical2Panorama();
        panorama.getCamera().lookAt(30.0f, 90.0f);
        panorama.setImage(new PLImage(PLUtils.getBitmap(this, R.raw.casabella_hall_2048), false));
//        panorama.setImage(new PLImage(PLUtils.getBitmap(this, "http://statichost.herokuapp.com/panoramagl/casabella_hall_2048.jpg"), false));
        this.setPanorama(panorama);
    }
}