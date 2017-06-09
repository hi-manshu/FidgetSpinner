package xyz.himanshusingh.fidgetspinner.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Helper class for setting typeface to the text views.
 * @author @himanshu
 */
@SuppressWarnings("unused") // public APIs
public final class TypefaceHelper {
	public static final String TAG = TypefaceHelper.class.getSimpleName();
	private static TypefaceHelper sHelper;
	private final Application mApplication;
	private final TypefaceCache mCache;

	private TypefaceHelper(Application application) {
		mApplication = application;
		mCache = TypefaceCache.getInstance(application);
	}
	public static synchronized void initialize(Application application) {
		if (sHelper != null) {
			Log.v(TAG, "already initialized");
		}
		sHelper = new TypefaceHelper(application);
	}
	public static synchronized void destroy() {
		if (sHelper == null) {
			Log.v(TAG, "not initialized yet");
			return;
		}
		sHelper = null;
	}
	public static synchronized TypefaceHelper getInstance() {
		if (sHelper == null) {
			throw new IllegalArgumentException("Instance is not initialized yet. Call initialize() first.");
		}
		return sHelper;
	}
	public Typeface getTypeface(String typefaceName){
		return mCache.get(typefaceName);
	}
	public <V extends TextView> void setTypeface(V view, String typefaceName) {
		Typeface typeface = mCache.get(typefaceName);
		view.setTypeface(typeface);
	}
	public <V extends TextView> void setTypeface(V view, @StringRes int strResId) {
		setTypeface(view, mApplication.getString(strResId));
	}
	public <V extends TextView> void setTypeface(V view, String typefaceName, int style) {
		Typeface typeface = mCache.get(typefaceName);
		view.setTypeface(typeface, style);
	}
public <V extends TextView> void setTypeface(V view, @StringRes int strResId, int style) {
		setTypeface(view, mApplication.getString(strResId), style);
	}
	public <V extends ViewGroup> void setTypeface(V viewGroup, String typefaceName) {
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = viewGroup.getChildAt(i);
			if (child instanceof ViewGroup) {
				setTypeface((ViewGroup) child, typefaceName);
				continue;
			}
			if (!(child instanceof TextView)) {
				continue;
			}
			setTypeface((TextView) child, typefaceName);
		}
	}

	public <V extends ViewGroup> void setTypeface(V viewGroup, @StringRes int strResId) {
		setTypeface(viewGroup, mApplication.getString(strResId));
	}

	public <V extends ViewGroup> void setTypeface(V viewGroup, String typefaceName, int style) {
		int count = viewGroup.getChildCount();
		for (int i = 0; i < count; i++) {
			View child = viewGroup.getChildAt(i);
			if (child instanceof ViewGroup) {
				setTypeface((ViewGroup) child, typefaceName, style);
				continue;
			}
			if (!(child instanceof TextView)) {
				continue;
			}
			setTypeface((TextView) child, typefaceName, style);
		}
	}

	public <V extends ViewGroup> void setTypeface(V viewGroup, @StringRes int strResId, int style) {
		setTypeface(viewGroup, mApplication.getString(strResId), style);
	}
    public void setTypeface(Paint paint, String typefaceName) {
        Typeface typeface = mCache.get(typefaceName);
        paint.setTypeface(typeface);
    }
	public void setTypeface(Paint paint, @StringRes int strResId) {
		setTypeface(paint, mApplication.getString(strResId));
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, String typefaceName) {
		return setTypeface(context, layoutRes, null, typefaceName);
	}

	public View setTypeface(Context context, @LayoutRes int layoutRes, @StringRes int strResId) {
		return setTypeface(context, layoutRes, mApplication.getString(strResId));
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, ViewGroup parent, String typefaceName) {
		ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(layoutRes, parent);
		setTypeface(view, typefaceName);
		return view;
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, ViewGroup parent, @StringRes int strResId) {
		return setTypeface(context, layoutRes, parent, mApplication.getString(strResId));
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, String typefaceName, int style) {
		return setTypeface(context, layoutRes, null, typefaceName, 0);
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, @StringRes int strResId, int style) {
		return setTypeface(context, layoutRes, mApplication.getString(strResId), 0);
	}

	public View setTypeface(Context context, @LayoutRes int layoutRes, ViewGroup parent, String typefaceName, int style) {
		ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(layoutRes, parent);
		setTypeface(view, typefaceName, style);
		return view;
	}
	public View setTypeface(Context context, @LayoutRes int layoutRes, ViewGroup parent, @StringRes int strResId, int style) {
		return setTypeface(context, layoutRes, parent, mApplication.getString(strResId), style);
	}
	public void setTypeface(Activity activity, String typefaceName) {
		setTypeface(activity, typefaceName, 0);
	}
	public void setTypeface(Activity activity, @StringRes int strResId) {
		setTypeface(activity, mApplication.getString(strResId));
	}
	public void setTypeface(Activity activity, String typefaceName, int style) {
		setTypeface((ViewGroup) activity.getWindow().getDecorView(), typefaceName, style);
	}
	public void setTypeface(Activity activity, @StringRes int strResId, int style) {
		setTypeface(activity, mApplication.getString(strResId), style);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public <F extends android.app.Fragment> void setTypeface(F fragment, String typefaceName) {
		setTypeface(fragment, typefaceName, 0);
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public <F extends android.app.Fragment> void setTypeface(F fragment, @StringRes int strResId) {
		setTypeface(fragment, mApplication.getString(strResId));
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public <F extends android.app.Fragment> void setTypeface(F fragment, String typefaceName, int style) {
		View root = fragment.getView();
		if (root instanceof TextView) {
			setTypeface((TextView) root, typefaceName, style);
		} else if (root instanceof ViewGroup) {
			setTypeface((ViewGroup) root, typefaceName, style);
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public <F extends android.app.Fragment> void setTypeface(F fragment, @StringRes int strResId, int style) {
		setTypeface(fragment, mApplication.getString(strResId), style);
	}
	public <F extends android.support.v4.app.Fragment> void supportSetTypeface(F fragment, String typefaceName) {
		supportSetTypeface(fragment, typefaceName, 0);
	}
	public <F extends android.support.v4.app.Fragment> void supportSetTypeface(F fragment, @StringRes int strResId) {
		supportSetTypeface(fragment, mApplication.getString(strResId));
	}
	public <F extends android.support.v4.app.Fragment> void supportSetTypeface(F fragment, String typefaceName, int style) {
		View root = fragment.getView();
		if (root instanceof TextView) {
			setTypeface((TextView) root, typefaceName, style);
		} else if (root instanceof ViewGroup) {
			setTypeface((ViewGroup) root, typefaceName, style);
		}
	}
	public <F extends android.support.v4.app.Fragment> void supportSetTypeface(F fragment, @StringRes int strResId, int style) {
		supportSetTypeface(fragment, mApplication.getString(strResId), style);
	}
	public <D extends Dialog> void setTypeface(D dialog, String typefaceName) {
		setTypeface(dialog, typefaceName, 0);
	}
	public <D extends Dialog> void setTypeface(D dialog, @StringRes int strResId) {
		setTypeface(dialog, mApplication.getString(strResId));
	}
	public <D extends Dialog> void setTypeface(D dialog, String typefaceName, int style) {
		DialogUtils.setTypeface(this, dialog, typefaceName, style);
	}
	public <D extends Dialog> void setTypeface(D dialog, @StringRes int strResId, int style) {
		setTypeface(dialog, mApplication.getString(strResId), style);
	}
	public Toast setTypeface(Toast toast, String typefaceName) {
		return setTypeface(toast, typefaceName, 0);
	}
	public Toast setTypeface(Toast toast, @StringRes int strResId) {
		return setTypeface(toast, mApplication.getString(strResId));
	}
	public Toast setTypeface(Toast toast, String typefaceName, int style) {
		setTypeface((ViewGroup) toast.getView(), typefaceName, style);
		return toast;
	}
	public Toast setTypeface(Toast toast, @StringRes int strResId, int style) {
		return setTypeface(toast, mApplication.getString(strResId), style);
	}
}
