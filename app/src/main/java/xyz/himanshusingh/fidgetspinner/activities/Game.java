package xyz.himanshusingh.fidgetspinner.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import xyz.himanshusingh.fidgetspinner.Custom.CustomFontTextView;
import xyz.himanshusingh.fidgetspinner.R;
import xyz.himanshusingh.fidgetspinner.adapters.SpinnerGridAdapter;
import xyz.himanshusingh.fidgetspinner.utils.Utils;

public class Game extends AppCompatActivity {
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 200;
    private static final String PATH_TOS = "";
    String MAXIMUM_VALUE = "MAXIMUM_VALUE",
            LAST_VALUE = "LAST_VALUE",
            TARGET = "TARGET";
    ImageView share;
    CustomFontTextView tMax, tTarget, tCurrent;
    RelativeLayout container;
    AppCompatButton btnHowToPlay;
    int numberOfRotations = 0, target = 100, maximumValue = 0;
    Context context;
    SharedPreferences gameSettings;
    SharedPreferences.Editor gameSettingsEditor;
    private static Bitmap imageOriginal, imageScaled;
    private static Matrix matrix;
    private ImageView btnSelect, pen;
    private ImageView dialer, profile;
    private int dialerHeight, dialerWidth;

    private GestureDetector detector;
    // needed for detecting the inversed rotations
    private boolean[] quadrantTouched;

    private boolean allowRotating;

    private Vibrator mVibrator;

    private String TAG = "SPINIFY";

    private ObjectAnimator rotationAnimator;

    int DURATION_FOR_ONE_ROTATION = 300;
    boolean canAnimate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (isUserLogin()) {
            loginUser();
        }
        setContentView(R.layout.activity_game);
        context = this;
        gameSettings = getSharedPreferences("gameSettings", MODE_PRIVATE);
        gameSettingsEditor = gameSettings.edit();
        gameSettingsEditor.apply();
        share = (ImageView) findViewById(R.id.share);
        pen = (ImageView) findViewById(R.id.pen);
        container = (RelativeLayout) findViewById(R.id.container);
        dialer = (ImageView) findViewById(R.id.fidgetSpinner);
        profile = (ImageView) findViewById(R.id.profile);
        tMax = (CustomFontTextView) findViewById(R.id.maximumScore);
        tTarget = (CustomFontTextView) findViewById(R.id.targetValue);
        tCurrent = (CustomFontTextView) findViewById(R.id.currentValue);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginMech();
            }
        });
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isUserLogin()) {
                    loginMech();

                } else {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    PackageManager pm = getPackageManager();
                    Intent tempIntent = new Intent(Intent.ACTION_SEND);
                    tempIntent.setType("*/*");
                    List<ResolveInfo> resInfo = pm.queryIntentActivities(tempIntent, 0);
                    for (int i = 0; i < resInfo.size(); i++) {
                        ResolveInfo ri = resInfo.get(i);
                        if (ri.activityInfo.packageName.contains("android.gm")) {
                            myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                            myIntent.setAction(Intent.ACTION_SEND);
                            myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello2himanshusingh@gmail.com"});
                            myIntent.setType("message/rfc822");
                            myIntent.putExtra(Intent.EXTRA_TEXT, "Hey, I am " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                            myIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Suggestion about App");
                        }
                    }
                    startActivity(myIntent);
                }
            }
        });
        btnSelect = (ImageView) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSpinner();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi, my maximum score is " + String.valueOf(maximumValue) + ". Challenge me on Fidget Spinner. Download it from " + "https://play.google.com/store/apps/details?id=xyz.himanshusingh.fidgetspinner to challange me.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
        setImage(R.drawable.batman, false);
        dialer.setOnTouchListener(new MyOnTouchListener());
        dialer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (dialerHeight == 0 || dialerWidth == 0) {
                    dialerHeight = dialer.getHeight();
                    dialerWidth = dialer.getWidth();
                    Matrix resize = new Matrix();
                    resize.postScale((float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getWidth(), (float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getHeight());
                    imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(), imageOriginal.getHeight(), resize, false);
                    float translateX = dialerWidth / 2 - imageScaled.getWidth() / 2;
                    float translateY = dialerHeight / 2 - imageScaled.getHeight() / 2;
                    matrix.postTranslate(translateX, translateY);
                    dialer.setImageBitmap(imageScaled);
                    dialer.setImageMatrix(matrix);
                }
            }
        });
        updateViews();
        changeTarget();
    }

    private void loginMech() {
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setTosUrl(PATH_TOS)
                .build(), RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                loginUser();
            }
            if (resultCode == RESULT_CANCELED) {
            }
            return;
        }
    }

    private boolean isUserLogin() {
        if (auth.getCurrentUser() == null) {
            return true;
        }
        return false;
    }

    private void loginUser() {

    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void setImage(int imageId, boolean isChanging) {
        imageOriginal = BitmapFactory.decodeResource(getResources(), imageId);
        matrix = new Matrix();
        detector = new GestureDetector(this, new MyGestureDetector());
        quadrantTouched = new boolean[]{false, false, false, false, false};

        allowRotating = true;

        if (isChanging) {

            dialerHeight = dialer.getHeight();
            dialerWidth = dialer.getWidth();
            Matrix resize = new Matrix();
            resize.postScale((float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getWidth(), (float) Math.min(dialerWidth, dialerHeight) / (float) imageOriginal.getHeight());
            imageScaled = Bitmap.createBitmap(imageOriginal, 0, 0, imageOriginal.getWidth(), imageOriginal.getHeight(), resize, false);

            float translateX = dialerWidth / 2 - imageScaled.getWidth() / 2;
            float translateY = dialerHeight / 2 - imageScaled.getHeight() / 2;
            matrix.postTranslate(translateX, translateY);
            dialer.setImageBitmap(imageScaled);
            dialer.setImageMatrix(matrix);

        }
    }

    private void rotateDialer(float degrees) {
        matrix.postRotate(degrees, dialerWidth / 2, dialerHeight / 2);
        dialer.setImageMatrix(matrix);
    }

    private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (dialerWidth / 2d);
        double y = dialerHeight - yTouch - (dialerHeight / 2d);

        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
            case 3:
                return 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            default:
                return 0;
        }
    }

    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }

    }

    private class MyOnTouchListener implements View.OnTouchListener {
        private double startAngle;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (rotationAnimator != null && rotationAnimator.isRunning()) {
                rotationAnimator.cancel();
            } else {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        for (int i = 0; i < quadrantTouched.length; i++) {
                            quadrantTouched[i] = false;
                        }
                        allowRotating = false;
                        startAngle = getAngle(event.getX(), event.getY());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        double currentAngle = getAngle(event.getX(), event.getY());
                        rotateDialer((float) (startAngle - currentAngle));
                        startAngle = currentAngle;
                        break;
                    case MotionEvent.ACTION_UP:
                        allowRotating = true;
                        break;
                }
            }
            quadrantTouched[getQuadrant(event.getX() - (dialerWidth / 2), dialerHeight - event.getY() - (dialerHeight / 2))] = true;
            detector.onTouchEvent(event);
            return true;
        }
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int q1 = getQuadrant(e1.getX() - (dialerWidth / 2), dialerHeight - e1.getY() - (dialerHeight / 2));
            int q2 = getQuadrant(e2.getX() - (dialerWidth / 2), dialerHeight - e2.getY() - (dialerHeight / 2));
            float velocity = (velocityX + velocityY) * 3;
            int rotationTime = (int) (velocity / 360) * DURATION_FOR_ONE_ROTATION;

            if ((q1 == 2 && q2 == 2 && Math.abs(velocityX) < Math.abs(velocityY))
                    || (q1 == 3 && q2 == 3)
                    || (q1 == 1 && q2 == 3)
                    || (q1 == 4 && q2 == 4 && Math.abs(velocityX) > Math.abs(velocityY))
                    || ((q1 == 2 && q2 == 3) || (q1 == 3 && q2 == 2))
                    || ((q1 == 3 && q2 == 4) || (q1 == 4 && q2 == 3))
                    || (q1 == 2 && q2 == 4 && quadrantTouched[3])
                    || (q1 == 4 && q2 == 2 && quadrantTouched[3])) {
                rotateWheelToTarget(dialer, 0, -1 * velocity, Utils.getUnsignedInt(rotationTime), 0);
            } else {
                rotateWheelToTarget(dialer, 0, velocity, Utils.getUnsignedInt(rotationTime), 0);
            }

            return true;
        }
    }

    float getDistance(float startX, float startY, MotionEvent ev) {
        float distanceSum = 0;
        final int historySize = ev.getHistorySize();
        for (int h = 0; h < historySize; h++) {
            float hx = ev.getHistoricalX(0, h);
            float hy = ev.getHistoricalY(0, h);
            float dx = (hx - startX);
            float dy = (hy - startY);
            distanceSum += Math.sqrt(dx * dx + dy * dy);
            startX = hx;
            startY = hy;
        }
        float dx = (ev.getX(0) - startX);
        float dy = (ev.getY(0) - startY);
        distanceSum += Math.sqrt(dx * dx + dy * dy);
        return distanceSum;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Exiting...")
                .setMessage("Are you sure you want to exit right now?")
                .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                    }
                })
                .show();
    }

    public void rotateWheelToTarget(final View view, final float startDegree, final float endDegree, final int duration, final int numberOfRepeats) {
        if (rotationAnimator != null) {
            rotationAnimator.cancel();
        }
        rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startDegree, endDegree);
        rotationAnimator.setDuration(duration);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float currentRotation = view.getRotation();
                int totalRotations = (int) Math.floor(Utils.getUnsignedInt((int) endDegree) / 360);
                numberOfRotations = (int) Math.floor(Utils.getUnsignedInt((int) currentRotation) / 360);
                tCurrent.setText(String.valueOf(numberOfRotations));
            }

        });
        rotationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tCurrent.setText("0");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (numberOfRotations > gameSettings.getInt(MAXIMUM_VALUE, 0)) {
                    gameSettingsEditor.putInt(MAXIMUM_VALUE, numberOfRotations);
                    gameSettingsEditor.apply();
                }
                if (numberOfRotations > gameSettings.getInt(LAST_VALUE, 0)) {
                    target = numberOfRotations + 20;
                    gameSettingsEditor.putInt(LAST_VALUE, numberOfRotations);
                    gameSettingsEditor.putInt(TARGET, target);
                    gameSettingsEditor.apply();
                }
                updateViews();
                changeTarget();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        rotationAnimator.start();
    }

    private void updateViews() {
        maximumValue = gameSettings.getInt(MAXIMUM_VALUE, 0);
        tMax.setText("Max: " + String.valueOf(maximumValue));
    }

    private void changeTarget() {
        target = gameSettings.getInt(TARGET, 100);
        tTarget.setText(String.valueOf(target));
    }

    private synchronized void vibrate(long duration) {
        if (mVibrator == null) {
            mVibrator = (Vibrator)
                    getSystemService(Context.VIBRATOR_SERVICE);
        }
        mVibrator.vibrate(duration);
    }

    @Override
    protected void onPause() {
        if (rotationAnimator != null && rotationAnimator.isRunning()) {
//            soundPool.pause(soundId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                rotationAnimator.pause();
            } else {
                rotationAnimator.start();
            }
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (rotationAnimator != null && rotationAnimator.isPaused()) {
//                soundPool.resume(soundId);
                rotationAnimator.resume();
            }
        } else {
            if (rotationAnimator != null) {
                rotationAnimator.start();
            }
        }
        super.onResume();
    }

    public void selectSpinner() {
        if (rotationAnimator != null) {
            rotationAnimator.cancel();
        }
        final ArrayList<Integer> spinnerList = new ArrayList<>();
        spinnerList.add(R.drawable.spinner);
        spinnerList.add(R.drawable.spinner_blue);
        spinnerList.add(R.drawable.spinner_blue_1);
        spinnerList.add(R.drawable.spinner_blue_2);
        spinnerList.add(R.drawable.spinner_red_1);
        spinnerList.add(R.drawable.spinner_red_2);
        spinnerList.add(R.drawable.spinner_red_4);
        spinnerList.add(R.drawable.spinner_gold);
        spinnerList.add(R.drawable.spinner_green);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_spinner_dialog);
        dialog.setCancelable(true);
        dialog.show();
        final GridView gridView = (GridView) dialog.findViewById(R.id.spinnerGrid);
        gridView.setAdapter(new SpinnerGridAdapter(context, spinnerList));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setImage(spinnerList.get(position), true);
                btnSelect.setImageResource(spinnerList.get(position));
                dialog.dismiss();
            }
        });
    }
}
