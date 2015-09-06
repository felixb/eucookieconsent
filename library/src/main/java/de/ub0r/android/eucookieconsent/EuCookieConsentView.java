package de.ub0r.android.eucookieconsent;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * A view showing some text and buttons to get the "EU Cookie Consent". The view disables itself
 * forever after the user acknowledged it once.
 *
 * @author Felix Bechstein <f@ub0r.de>
 */
public class EuCookieConsentView extends FrameLayout {

    private static final String PREFS_FILENAME = "eu_cookie_consent";

    private static final String PREFS_ACKNOWLEDGED = "acknowledged";

    private TextView mMessageView;

    private CharSequence mMessageText;

    private TextView mGotItView;

    private CharSequence mLearnMoreText;

    private Uri mLearnMoreUri;

    private boolean mHideOutsideEu;

    public EuCookieConsentView(final Context context) {
        super(context);
        init(null, 0);
    }

    public EuCookieConsentView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EuCookieConsentView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private CharSequence getStyledString(final TypedArray a, final int styleableId,
            final int defaultId) {
        if (a.hasValue(styleableId)) {
            return a.getString(styleableId);
        } else {
            return getContext().getString(defaultId);
        }
    }

    private CharSequence getStyledString(final TypedArray a, final int styleableId,
            final CharSequence defString) {
        if (a.hasValue(styleableId)) {
            return a.getString(styleableId);
        } else {
            return defString;
        }
    }

    private void init(final AttributeSet attrs, final int defStyle) {
        if (isAcknowledged()) {
            // hide view and skip everything if user clicked "got it" once.
            setVisibility(GONE);
            return;
        }

        // get styled attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.EuCookieConsentView, defStyle, 0);
        setHideOutsideEu(a.getBoolean(R.styleable.EuCookieConsentView_hideOutsideEu, true));

        if (isHideOutsideEu() && new EuUserChecker(getContext()).isNotEuUser()) {
            // hide view and skip everything if user comes from outside EU
            setVisibility(GONE);
            a.recycle();
            return;
        }

        // inflate the view
        final LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.eu_cookie_consent_view, this);
        mMessageView = (TextView) findViewById(R.id.message);
        mMessageView.setMovementMethod(LinkMovementMethod.getInstance());
        mGotItView = (TextView) findViewById(R.id.got_it);
        mGotItView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                setAcknowledged();
                setVisibility(GONE);
            }
        });

        setMessageText(getStyledString(a, R.styleable.EuCookieConsentView_textMessage,
                R.string.message));
        setGotItText(
                getStyledString(a, R.styleable.EuCookieConsentView_textGotIt, R.string.got_it));
        setLearnMoreText(getStyledString(a, R.styleable.EuCookieConsentView_textLearnMore,
                R.string.learn_more));
        setLearnMoreUri(getStyledString(a, R.styleable.EuCookieConsentView_urlLeanMore, null));
        a.recycle();
    }

    public boolean isAcknowledged() {
        return getContext().getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
                .getBoolean(PREFS_ACKNOWLEDGED, false);
    }

    @SuppressWarnings("unused")
    public void setAcknowledged() {
        getContext().getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).edit()
                .putBoolean(PREFS_ACKNOWLEDGED, true).apply();
    }

    @SuppressWarnings("unused")
    public void resetAcknowledged() {
        getContext().getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE).edit()
                .remove(PREFS_ACKNOWLEDGED).apply();
    }

    public boolean isHideOutsideEu() {
        return mHideOutsideEu;
    }

    public void setHideOutsideEu(final boolean hideOutsideEu) {
        mHideOutsideEu = hideOutsideEu;
    }

    @SuppressWarnings("unused")
    public CharSequence getMessageText() {
        return mMessageText;
    }

    public void setMessageText(final CharSequence text) {
        mMessageText = text;
        updateMessageText();
    }

    @SuppressWarnings("unused")
    public void setTextMessage(final int resId) {
        setMessageText(getContext().getString(resId));
    }

    @SuppressWarnings("unused")
    public CharSequence getGotItText() {
        return mGotItView.getText();
    }

    public void setGotItText(final CharSequence text) {
        mGotItView.setText(text);
    }

    @SuppressWarnings("unused")
    public void setTextGotIt(final int resId) {
        setGotItText(getContext().getString(resId));
    }

    @SuppressWarnings("unused")
    public CharSequence getLearnMoreText() {
        return mLearnMoreText;
    }

    public void setLearnMoreText(final CharSequence text) {
        mLearnMoreText = text;
    }

    @SuppressWarnings("unused")
    public void setTextLearnMore(final int resId) {
        setLearnMoreText(getContext().getString(resId));
    }

    @SuppressWarnings("unused")
    public Uri getLearnMoreUri() {
        return mLearnMoreUri;
    }

    public void setLearnMoreUri(final CharSequence uri) {
        if (uri == null) {
            mLearnMoreUri = null;
        } else {
            mLearnMoreUri = Uri.parse(uri.toString());
        }
        updateMessageText();
    }

    @SuppressWarnings("unused")
    public void setLearnMoreUri(final int resId) {
        setLearnMoreUri(getContext().getString(resId));
    }

    private void updateMessageText() {
        if (mLearnMoreUri == null || TextUtils.isEmpty(mLearnMoreText)) {
            mMessageView.setText(mMessageText);
        } else {
            mMessageView.setText(Html.fromHtml(mMessageText
                    + " <a href=\"" + mLearnMoreUri.toString() + "\">"
                    + mLearnMoreText + "</a>"));
        }
    }
}
