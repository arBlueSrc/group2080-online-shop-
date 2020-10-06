package com.appnita.digikala.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.appnita.digikala.R;
import com.appnita.digikala.databinding.FragmentPlayBinding;
import com.appnita.digikala.retrofit.pojoPosts.PostsItem;
import com.appnita.digikala.ui.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class PlayFragment extends BottomSheetDialogFragment {

    FragmentPlayBinding binding;
    MediaPlayer mediaPlayer;
    Timer timer;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(getContext());
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private static final String DESCRIBABLE_KEY = "dialog_playback";
    private PostsItem postsItem;

    public static PlayFragment newInstance(PostsItem postsItem) {
        PlayFragment bottomSheetFragment = new PlayFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DESCRIBABLE_KEY, postsItem);
        bottomSheetFragment.setArguments(bundle);

        return bottomSheetFragment;
    }

    public PlayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlayBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mediaPlayer = new MediaPlayer();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
        }
        postsItem = (PostsItem) getArguments().getSerializable(
                DESCRIBABLE_KEY);
        binding.txtTitle.setText(postsItem.getTitle());
        binding.txtTitle.setText(postsItem.getContent().substring(53,postsItem.getContent().indexOf("mp3")+3));
        Picasso.with(getContext())
                .load(postsItem.getThumbnail())
                .into(binding.imgBtmsheet);
        try {
          mediaPlayer.setDataSource(postsItem.getContent().substring(53,postsItem.getContent().indexOf("mp3")+3));
//            mediaPlayer.setDataSource("https://dl.nex1music.ir/1399/07/09/Arash%20Rabiei%20-%20Aroomam%20Ba%20To%20[128].mp3?time=1601481409&filename=/1399/07/09/Arash%20Rabiei%20-%20Aroomam%20Ba%20To%20[128].mp3");

        } catch (IOException e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        binding.seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                setupViews();
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                binding.seekBar.setProgress(0);
                binding.btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause, null));
            }
        });
    }

    private void setupViews() {

        binding.seekBar.setVisibility(View.VISIBLE);
        binding.txtNowTime.setVisibility(View.VISIBLE);
        binding.txtHoleTime.setVisibility(View.VISIBLE);
        binding.btnPlay.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.GONE);

        binding.btnPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                binding.btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_play, null));
            } else {
                mediaPlayer.start();
                binding.btnPlay.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_pause, null));
            }
        });

        String time = getStringTime(mediaPlayer.getDuration());
        binding.txtHoleTime.setText(time);
        binding.txtNowTime.setText(getStringTime(0));

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                binding.seekBar.setSecondaryProgress((percent / 100) * mp.getDuration());
            }
        });

        timer = new Timer();
        timer.schedule(new PlayTimer(), 0, 1000);
    }

    public class PlayTimer extends TimerTask {

        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    binding.seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    binding.txtNowTime.setText(getStringTime(mediaPlayer.getCurrentPosition()));
                }
            });
        }
    }

    @NotNull
    private String getStringTime(int time) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        long second = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format(Locale.ENGLISH, "%02d", minutes) + ":" + String.format(Locale.ENGLISH, "%02d", second);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        super.onDestroy();
    }
}