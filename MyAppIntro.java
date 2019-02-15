package com.asimbongeni.asie.grmtranslate;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;

public class MyAppIntro extends AppIntro {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_my_app_intro);
        addSlide(AppIntro2Fragment.newInstance("How to translate", "This is your app guide",R.drawable.homeview, Color.parseColor("#673ab7")));
        addSlide(AppIntro2Fragment.newInstance("1. Click", "Click the translate button",R.drawable.homeviewtranslate, Color.parseColor("#FF5252")));
        addSlide(AppIntro2Fragment.newInstance("2. Type chapter", "Type the chapter you want to translate",R.drawable.typechapter, Color.parseColor("#D1C4E9")));
        addSlide(AppIntro2Fragment.newInstance("3. Available Sentences", "This is a list of the untranslated sentences.",R.drawable.listallsentences, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("4. List properties", "This shows a different number of sentences in a chapter with the chapter number selected.",R.drawable.listallsentencesedited, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("5. Translation", "This is where you do your translation work. " +
                "Remember to translate all that you see, what is in English return as it is",R.drawable.translation_howto, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("6. Whatsapp Integration", "You can ask for help from you whatsapp contacts. " +
                "Click on the icon and select a contact. Note you need whatsapp to be installed in your phone",R.drawable.whatsapp_focus, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("7. Translation Aid", "You can also view the sentence before the one you are working on.",R.drawable.click_for_previous, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("8. Translation Aid", "As well as viewing the sentence after the one you are working on.",R.drawable.click_for_next, Color.parseColor("#757575")));
        addSlide(AppIntro2Fragment.newInstance("9. Saving Your translation", "Click on save to save your translated work.",R.drawable.click_to_save, Color.parseColor("#757575")));
        showSkipButton(true);
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        //Toast.makeText(getApplicationContext(), "Slide Changed", Toast.LENGTH_SHORT).show();
    }
}
