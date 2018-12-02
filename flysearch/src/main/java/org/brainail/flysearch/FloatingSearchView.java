package org.brainail.flysearch;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.*;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import androidx.annotation.*;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.recyclerview.widget.RecyclerView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FloatingSearchView extends ConstraintLayout {

    private static final int DEFAULT_BACKGROUND_COLOR = 0x90000000;
    private static final int DEFAULT_CONTENT_COLOR = 0xfff0f0f0;

    private static final int DEFAULT_RADIUS = 2;
    private static final int DEFAULT_ELEVATION = 2;
    private static final int DEFAULT_MAX_ELEVATION = 2;

    private static final long DEFAULT_DURATION_ENTER = 500;
    private static final long DEFAULT_DURATION_EXIT = 400;

    private static final Interpolator DECELERATE = new DecelerateInterpolator(3f);
    private static final Interpolator ACCELERATE = new AccelerateInterpolator(2f);

    @NonNull
    private final RecyclerView.AdapterDataObserver mAdapterObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
        }

        @Override
        public void onChanged() {
            updateSuggestionsVisibility();
        }
    };

    public interface OnSearchListener {
        void onSearchAction(CharSequence text);
    }

    public interface OnIconClickListener {
        void onNavigationClick();
    }

    public interface OnSearchFocusChangedListener {
        void onFocusChanged(boolean focused);
    }

    private final LogoEditText mSearchInput;
    private final ImageView mNavButtonView;
    private final RecyclerView mRecyclerView;
    private final ViewGroup mSearchContainer;
    private final CardView mSearchCard;
    private final View mDivider;
    private final ActionMenuView mActionMenu;

    private final Activity mHostActivity;

    private final List<Integer> mAlwaysShowingMenu = new ArrayList<>();

    private OnSearchFocusChangedListener mFocusListener;
    private OnIconClickListener mNavigationClickListener;
    private Drawable mBackgroundDrawable;
    private boolean mSuggestionsShown;

    @Nullable
    private ValueAnimator mCurrentBackgroundAnimator;
    @Nullable
    private ObjectAnimator mCurrentIconAnimator;

    public FloatingSearchView(Context context) {
        this(context, null);
    }

    public FloatingSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.floatingSearchViewStyle);
    }

    public FloatingSearchView(Context context, AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHostActivity = isInEditMode() ? null : findHostActivity();

        setFocusable(true);
        setFocusableInTouchMode(true);

        inflate(getContext(), R.layout.fsv_floating_search_layout, this);

        mSearchInput = findViewById(R.id.fsv_search_text);
        mNavButtonView = findViewById(R.id.fsv_search_action_navigation);
        mRecyclerView = findViewById(R.id.fsv_suggestions_list);
        mDivider = findViewById(R.id.fsv_suggestions_divider);
        mSearchContainer = findViewById(R.id.fsv_search_container);
        mActionMenu = findViewById(R.id.fsv_search_action_menu);

        mSearchCard = findViewById(R.id.fsv_search_card);
        mSearchCard.setCardBackgroundColor(DEFAULT_CONTENT_COLOR);
        mSearchCard.setRadius(ViewUtils.dpToPx(DEFAULT_RADIUS));
        mSearchCard.setCardElevation(ViewUtils.dpToPx(DEFAULT_ELEVATION));
        mSearchCard.setMaxCardElevation(ViewUtils.dpToPx(DEFAULT_MAX_ELEVATION));

        applyXmlAttributes(attrs, defStyleAttr, 0);
        setupViews();
    }

    private void applyXmlAttributes(AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        final TypedArray styledAttrs = getContext().obtainStyledAttributes(
                attrs, R.styleable.FloatingSearchView, defStyleAttr, defStyleRes);

        // Search bar width
        View suggestionsContainer = findViewById(R.id.fsv_suggestions_container);
        int searchBarWidth = styledAttrs.getDimensionPixelSize(
                R.styleable.FloatingSearchView_fsv_searchBarWidth,
                mSearchContainer.getLayoutParams().width);
        mSearchContainer.getLayoutParams().width = searchBarWidth;
        suggestionsContainer.getLayoutParams().width = searchBarWidth;

        // Divider
        mDivider.setBackground(styledAttrs.getDrawable(R.styleable.FloatingSearchView_android_divider));
        int dividerHeight = styledAttrs.getDimensionPixelSize(R.styleable.FloatingSearchView_android_dividerHeight, -1);

        final MarginLayoutParams dividerLayoutParams = (MarginLayoutParams) mDivider.getLayoutParams();
        if (mDivider.getBackground() != null && dividerHeight != -1) {
            dividerLayoutParams.height = dividerHeight;
        }

        mDivider.setLayoutParams(dividerLayoutParams);

        // Content inset
        final MarginLayoutParams searchLayoutParams = (MarginLayoutParams) mSearchInput.getLayoutParams();
        int contentInsetStart = styledAttrs.getDimensionPixelSize(
                R.styleable.FloatingSearchView_contentInsetStart,
                MarginLayoutParamsCompat.getMarginStart(searchLayoutParams));
        int contentInsetEnd = styledAttrs.getDimensionPixelSize(
                R.styleable.FloatingSearchView_contentInsetEnd,
                MarginLayoutParamsCompat.getMarginEnd(searchLayoutParams));

        MarginLayoutParamsCompat.setMarginStart(searchLayoutParams, contentInsetStart);
        MarginLayoutParamsCompat.setMarginEnd(searchLayoutParams, contentInsetEnd);

        int logoId = styledAttrs.getResourceId(R.styleable.FloatingSearchView_logo, -1);
        if (logoId != -1 && !isInEditMode()) {
            setLogo(AppCompatResources.getDrawable(mHostActivity, logoId));
        }

        int iconId = styledAttrs.getResourceId(R.styleable.FloatingSearchView_fsv_icon, -1);
        if (iconId != -1 && !isInEditMode()) {
            setIcon(AppCompatResources.getDrawable(mHostActivity, iconId));
        }

        setContentBackgroundColor(styledAttrs.getColor(
                R.styleable.FloatingSearchView_fsv_contentBackgroundColor, DEFAULT_CONTENT_COLOR));
        setRadius(styledAttrs.getDimensionPixelSize(
                R.styleable.FloatingSearchView_fsv_cornerRadius, ViewUtils.dpToPx(DEFAULT_RADIUS)));
        inflateMenu(styledAttrs.getResourceId(R.styleable.FloatingSearchView_fsv_menu, 0));
        setPopupTheme(styledAttrs.getResourceId(R.styleable.FloatingSearchView_popupTheme, 0));
        setHint(styledAttrs.getString(R.styleable.FloatingSearchView_android_hint));

        styledAttrs.recycle();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViews() {
        // mSearchContainer.setLayoutTransition(getDefaultLayoutTransition());
        // mSearchContainer.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.GONE);

        mBackgroundDrawable = getBackground();
        mBackgroundDrawable = mBackgroundDrawable != null
                ? mBackgroundDrawable.mutate()
                : new ColorDrawable(DEFAULT_BACKGROUND_COLOR);

        setBackground(mBackgroundDrawable);
        mBackgroundDrawable.setAlpha(0);

        mNavButtonView.setOnClickListener(v -> {
            if (mNavigationClickListener != null) {
                mNavigationClickListener.onNavigationClick();
            }
        });

        setOnTouchListener((v, event) -> {
            if (!isActivated()) {
                return false;
            }
            setActivated(false);
            return true;
        });

        mSearchInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus != isActivated()) {
                setActivated(hasFocus);
            }
        });

        mSearchInput.setOnKeyListener((view, keyCode, keyEvent) -> {
            if (keyCode != KeyEvent.KEYCODE_ENTER) {
                return false;
            }
            setActivated(false);
            return true;
        });
    }

    public void setRadius(float radius) {
        mSearchCard.setRadius(radius);
    }

    public void setContentBackgroundColor(@ColorInt int color) {
        mSearchCard.setCardBackgroundColor(color);
        mActionMenu.setBackgroundColor(color);
    }

    public Menu getMenu() {
        return mActionMenu.getMenu();
    }

    public void setPopupTheme(@StyleRes int resId) {
        mActionMenu.setPopupTheme(resId);
    }

    @SuppressLint("ResourceType")
    public void inflateMenu(@MenuRes int menuRes) {
        if (menuRes == 0 || isInEditMode()) {
            return; // early exit
        }

        mHostActivity.getMenuInflater().inflate(menuRes, mActionMenu.getMenu());
        XmlResourceParser parser = null;
        try {
            parser = getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);
            parseMenu(parser, attrs);
        } catch (XmlPullParserException | IOException exception) {
            throw new InflateException("Error parsing menu XML", exception); // should not happens
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }

    public void setOnSearchListener(final OnSearchListener listener) {
        mSearchInput.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode != KeyEvent.KEYCODE_ENTER) {
                return false;
            }
            listener.onSearchAction(mSearchInput.getText());
            return true;
        });
    }

    public void setOnMenuItemClickListener(ActionMenuView.OnMenuItemClickListener listener) {
        mActionMenu.setOnMenuItemClickListener(listener);
    }

    public CharSequence getText() {
        return mSearchInput.getText();
    }

    public void clearText() {
        mSearchInput.getText().clear();
    }

    public void setText(CharSequence text) {
        // usually some search is done after text changed
        // so we don't wanna notify about the same things
        if (!mSearchInput.getText().equals(text)) {
            mSearchInput.setText(text);
        }
    }

    public void setSelection(final int index) {
        mSearchInput.setSelection(index);
    }

    public void setHint(CharSequence hint) {
        mSearchInput.setHint(hint);
    }

    @Override
    public void setActivated(boolean activated) {
        if (activated == isActivated()) {
            return;
        }

        super.setActivated(activated);
        if (activated) {
            mSearchInput.requestFocus();
            ViewUtils.showSoftKeyboardDelayed(mSearchInput, 100);
        } else {
            requestFocus();
            ViewUtils.closeSoftKeyboard(mHostActivity);
        }

        if (mFocusListener != null) {
            mFocusListener.onFocusChanged(activated);
        }

        showMenu(!activated);
        fadeIn(activated);
        updateSuggestionsVisibility();
    }

    public void setOnIconClickListener(OnIconClickListener navigationClickListener) {
        mNavigationClickListener = navigationClickListener;
    }

    public void setOnSearchFocusChangedListener(OnSearchFocusChangedListener focusListener) {
        mFocusListener = focusListener;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        mSearchInput.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        mSearchInput.removeTextChangedListener(textWatcher);
    }

    public void setAdapter(RecyclerView.Adapter<? extends RecyclerView.ViewHolder> adapter) {
        RecyclerView.Adapter<? extends RecyclerView.ViewHolder> currentAdapter = getAdapter();
        if (currentAdapter != null) {
            currentAdapter.unregisterAdapterDataObserver(mAdapterObserver);
        }
        adapter.registerAdapterDataObserver(mAdapterObserver);
        mRecyclerView.setAdapter(adapter);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        mRecyclerView.setItemAnimator(itemAnimator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        mRecyclerView.addItemDecoration(decoration);
    }

    public void setLogo(Drawable drawable) {
        mSearchInput.setLogo(drawable);
    }

    public void setLogo(@DrawableRes int resId) {
        mSearchInput.setLogo(resId);
    }

    public void setIcon(@DrawableRes int resId) {
        showIcon(resId != 0);
        mNavButtonView.setImageResource(resId);
    }

    public void setIcon(Drawable drawable) {
        showIcon(drawable != null);
        mNavButtonView.setImageDrawable(drawable);
    }

    public void showSearchLogo(boolean show) {
        mSearchInput.showLogo(show);
    }

    public void showIcon(boolean show) {
        mNavButtonView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public Drawable getIcon() {
        return mNavButtonView == null ? null : mNavButtonView.getDrawable();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public RecyclerView.Adapter<? extends RecyclerView.ViewHolder> getAdapter() {
        return mRecyclerView.getAdapter();
    }

    protected LayoutTransition getDefaultLayoutTransition() {
        return new LayoutTransition();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void fadeIn(boolean enter) {
        if (null != mCurrentBackgroundAnimator) {
            mCurrentBackgroundAnimator.cancel();
        }
        if (Build.VERSION.SDK_INT >= 19)
            mCurrentBackgroundAnimator = ObjectAnimator.ofInt(
                    mBackgroundDrawable, "alpha", enter ? 255 : 0);
        else {
            mCurrentBackgroundAnimator = ValueAnimator.ofInt(enter ? 0 : 255, enter ? 255 : 0);
            mCurrentBackgroundAnimator.addUpdateListener(animation -> {
                int value = (Integer) animation.getAnimatedValue();
                mBackgroundDrawable.setAlpha(value);
            });
        }

        mCurrentBackgroundAnimator.setDuration(enter ? DEFAULT_DURATION_ENTER : DEFAULT_DURATION_EXIT);
        mCurrentBackgroundAnimator.setInterpolator(enter ? DECELERATE : ACCELERATE);
        mCurrentBackgroundAnimator.start();

        Drawable icon = unwrap(getIcon());
        if (icon != null) {
            if (null != mCurrentIconAnimator) {
                mCurrentIconAnimator.cancel();
            }
            mCurrentIconAnimator = ObjectAnimator.ofFloat(icon, "progress", enter ? 1 : 0);
            mCurrentIconAnimator.setDuration(enter ? DEFAULT_DURATION_ENTER : DEFAULT_DURATION_EXIT);
            mCurrentIconAnimator.setInterpolator(enter ? DECELERATE : ACCELERATE);
            mCurrentIconAnimator.start();
        }
    }

    private int getSuggestionsCount() {
        RecyclerView.Adapter<? extends RecyclerView.ViewHolder> currentAdapter = getAdapter();
        return currentAdapter == null ? 0 : currentAdapter.getItemCount();
    }

    private void updateSuggestionsVisibility() {
        showSuggestions(isActivated() && getSuggestionsCount() > 0);
    }

    private boolean suggestionsShown() {
        return mSuggestionsShown;
    }

    private void showSuggestions(final boolean show) {
        if (show == suggestionsShown()) {
            return;
        }

        mSuggestionsShown = show;

        if (show) {
            updateDivider();
            mRecyclerView.setVisibility(VISIBLE);
        } else {
            showDivider(false);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private void showDivider(boolean visible) {
        mDivider.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void updateDivider() {
        showDivider(isActivated() && getSuggestionsCount() > 0);
    }

    @NonNull
    private Activity findHostActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException();
    }

    private void showMenu(final boolean visible) {
        Menu menu = getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (mAlwaysShowingMenu.contains(item.getItemId())) {
                continue;
            }
            item.setVisible(visible);
        }
    }

    private void parseMenu(XmlPullParser parser, AttributeSet attrs) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals("menu")) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }

                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;

        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }

                    tagName = parser.getName();
                    if (tagName.equals("item")) {
                        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MenuItem);
                        int itemShowAsAction = a.getInt(R.styleable.MenuItem_showAsAction, -1);

                        if ((itemShowAsAction & MenuItem.SHOW_AS_ACTION_ALWAYS) != 0) {
                            int itemId = a.getResourceId(R.styleable.MenuItem_android_id, NO_ID);
                            if (itemId != NO_ID) mAlwaysShowingMenu.add(itemId);
                        }
                        a.recycle();
                    } else {
                        lookingForEndOfUnknownTag = true;
                        unknownTagName = tagName;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } else if (tagName.equals("menu")) {
                        reachedEndOfMenu = true;
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }

            eventType = parser.next();
        }
    }

    @SuppressLint("RestrictedApi")
    private static Drawable unwrap(Drawable icon) {
        if (icon instanceof androidx.appcompat.graphics.drawable.DrawableWrapper) {
            return ((androidx.appcompat.graphics.drawable.DrawableWrapper) icon).getWrappedDrawable();
        } else if (Build.VERSION.SDK_INT >= 23 && icon instanceof android.graphics.drawable.DrawableWrapper) {
            return ((android.graphics.drawable.DrawableWrapper) icon).getDrawable();
        } else {
            return DrawableCompat.unwrap(icon);
        }
    }

    private static class FlyRecyclerView extends RecyclerView {
        public FlyRecyclerView(Context context) {
            super(context);
        }

        public FlyRecyclerView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public FlyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent e) {
            ViewUtils.closeSoftKeyboard(this);
            return super.onTouchEvent(e);
        }
    }

    private static class LogoEditText extends AppCompatEditText {
        private Drawable mLogoIcon;
        private boolean mShouldShowLogo;
        private boolean mIsStateDirty;

        public LogoEditText(Context context) {
            super(context);
        }

        public LogoEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LogoEditText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        public void showLogo(boolean shown) {
            mShouldShowLogo = shown;
        }

        public void setLogo(@DrawableRes int res) {
            setLogo(res == 0
                    ? null
                    : ResourcesCompat.getDrawable(getResources(), res, getContext().getTheme()));
        }

        public void setLogo(Drawable logo) {
            mLogoIcon = logo;
            mIsStateDirty = true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (mShouldShowLogo && mLogoIcon != null) {
                if (mIsStateDirty) {
                    updateLogoBounds();
                    mIsStateDirty = false;
                }
                mLogoIcon.draw(canvas);
            } else
                super.onDraw(canvas);
        }

        private void updateLogoBounds() {
            int logoHeight = Math.min(getHeight(), mLogoIcon.getIntrinsicHeight());
            int top = (getHeight() - logoHeight) / 2;
            int logoWidth = (mLogoIcon.getIntrinsicWidth() * logoHeight) / mLogoIcon.getIntrinsicHeight();
            mLogoIcon.setBounds(0, top, logoWidth, top + logoHeight);
        }
    }
}
