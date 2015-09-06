package de.ub0r.android.eucookieconsent;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Checks if an user is an EU user. This requires android.permission.READ_PHONE_STATE.
 *
 * @author flx
 */
public class EuUserChecker {

    static final String[] EU_PREFIXES = new String[]{
            "376",
            "43",
            "32",
            "359",
            "385",
            "357",
            "420",
            "45",
            "372",
            "298",
            "358",
            "33",
            "350",
            "49",
            "30",
            "36",
            "354",
            "353",
            "39",
            "371",
            "423",
            "370",
            "352",
            "356",
            "377",
            "31",
            "47",
            "48",
            "351",
            "40",
            "378",
            "421",
            "386",
            "34",
            "46",
            "41",
            "44",
            "379",
    };

    static final String[] EU_ISOS = new String[]{
            "BE",
            "BG",
            "CZ",
            "DK",
            "DE",
            "EE",
            "IE",
            "EL",
            "ES",
            "FR",
            "HR",
            "IT",
            "CY",
            "LV",
            "LT",
            "LU",
            "HU",
            "MT",
            "NL",
            "AT",
            "PL",
            "PT",
            "RO",
            "SI",
            "SK",
            "FI",
            "SE",
            "UK",
    };

    private final Context mContext;

    public EuUserChecker(final Context context) {
        mContext = context;
    }

    public boolean isNotEuUser() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) {
            // no service, we are not sure the user comes from outside EU
            return false;
        }

        String telephoneNumber = telephonyManager.getLine1Number();
        if (!TextUtils.isEmpty(telephoneNumber)) {
            if (telephoneNumber.startsWith("+")) {
                return !isEuPrefixed(telephoneNumber.substring(1));
            } else if (telephoneNumber.startsWith("00")) {
                return !isEuPrefixed(telephoneNumber.substring(2));
            }
        }

        String countryIso = telephonyManager.getNetworkCountryIso();
        if (!TextUtils.isEmpty(countryIso)) {
            return !isEuIso(countryIso);
        }

        // we just don't know.
        return false;
    }

    /**
     * Returns true, if a number prefixed with any EU number prefix
     *
     * @param cleanNumber must be stripped of + or ^00
     */
    boolean isEuPrefixed(final String cleanNumber) {
        for (String prefix : EuUserChecker.EU_PREFIXES) {
            if (cleanNumber.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true, if an ISO code is an EU ISO code
     *
     * @param iso two letter ISO code
     */
    boolean isEuIso(final String iso) {
        String upcaseIso = iso.toUpperCase();
        for (String s : EuUserChecker.EU_ISOS) {
            if (upcaseIso.equals(s)) {
                return true;
            }
        }
        return false;
    }

}
