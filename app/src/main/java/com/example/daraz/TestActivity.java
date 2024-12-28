package com.example.daraz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    GridView gridView ;
    ArrayList<String> itemlist =new ArrayList<String>();
    private ArrayList<String> displayedList;
    LinearLayout button;
    TextView textView;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    boolean showall=false;
    private MoveAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


   /*     tabLayout=findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewpager2);
        List<Fragment> fragments= new ArrayList<>();
        fragments.add(new SectionFragments("For you"));
        fragments.add(new SectionFragments("Free Delivery"));
        fragments.add(new SectionFragments("New Arrival"));
        fragments.add(new SectionFragments("Must Buy"));
         Viewpage adapter=new Viewpage(TestActivity.this, fragments);
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout,viewPager2,
                ((tab, position) ->{
                    switch (position){
                        case 0:
                            tab.setText("For you");
                            break;
                        case 1:
                            tab.setText("Free Delivery");
                            break;
                        case 2:
                            tab.setText("New Arrival");
                            break;
                        case 3:
                            tab.setText("Must Buy");
                            break;

                    }

                } )).attach();*/

//        TabLayout tabLayout = findViewById(R.id.tablayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Home"));
//        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
//        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
//        tabLayout.addTab(tabLayout.newTab().setText("Settings"));

//


        for (int i=0;i<=25;i++){
            itemlist.add("Hello"+i);
        }

        gridView=findViewById(R.id.maingridvoiw);
        button=findViewById(R.id.howw);
        displayedList = new ArrayList<>(itemlist.subList(0, Math.min(6, itemlist.size())));

        // Set up the adapter
        adapter = new MoveAdapter( displayedList,this);
        gridView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleItems();


            }
        });





     }

//    private void setupTabIcons(TabLayout tabLayout) {
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            if (tab != null) {
//                switch (i) {
//                    case 0:
//                        tab.setCustomView(createTabView("Home", R.drawable.car1));
//                        break;
//                    case 1:
//                        tab.setCustomView(createTabView("Profile", R.drawable.car2));
//                        break;
//                    case 2:
//                        tab.setCustomView(createTabView("Settings", R.drawable.car3));
//                        break;
//                }
//            }
//        }
//    }
//
//    private @NonNull View createTabView(String title, @DrawableRes int iconRes) {
//        View view = getLayoutInflater().inflate(R.layout.new_top, null);
//        TextView tabText = view.findViewById(R.id.tabText);
//        ImageView tabIcon = view.findViewById(R.id.tabicon);
//
//        tabText.setText(title);
//        tabIcon.setImageResource(iconRes);
//
//        return view;
//    }

    private void toggleItems() {
        if (showall){
            // Show fewer items
            displayedList.clear();
            displayedList.addAll(itemlist.subList(0, Math.min(8, itemlist.size())));
        }else {
            // Show all items
            displayedList.clear();
            displayedList.addAll(itemlist);

        }
        showall= !showall;
        adapter.notifyDataSetChanged();
    }

    private void populateinitialdata(){

        for (int i=0;i<=20;i++){
            itemlist.add("item"+i);

        }
    }
}