package com.zzu.peifuYang.work959;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.zzu.peifuYang.work959.util.MusicProgressBarUtil;

import java.util.ArrayList;

import me.wcy.lrcview.LrcView;

public class PageActivity extends FragmentActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private playerFragment player;
    private discoverFragment discover;
    private LrcView lrcView;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        //找到ViewPager
        mPager =  findViewById(R.id.pager);
//        添加fragment
        ArrayList<Fragment> fragments = new ArrayList<>();
        player = new playerFragment();
        discover = new discoverFragment();
        fragments.add(player);
        fragments.add(discover);
        pagerAdapter = new PageAdapter(this,fragments);
        mPager.setAdapter(pagerAdapter);
//        init_view();
//        showLyric();
    }
    public void init_view(){
        lrcView = player.getView().findViewById(R.id.lyric_player);
    }
    public void showLyric(){
        lrcView.setLabel("无歌词");
        lrcView.setDraggable(true, new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                return true;
            }
        });
        String lrcText = MusicProgressBarUtil.getLrcText("秦洋-再见.lrc",this);
        lrcView.loadLrc(lrcText);
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
}
