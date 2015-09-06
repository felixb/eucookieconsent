package de.ub0r.android.eucookieconsent;

import android.test.AndroidTestCase;

/**
 * @author flx
 */
public class EuUserCheckerTest extends AndroidTestCase {

    public void testIsEuPrefixed() {
        EuUserChecker checker = new EuUserChecker(getContext());
        assertTrue(checker.isEuPrefixed("4912345"));
        assertFalse(checker.isEuPrefixed("155555"));
    }

    public void testIsEuIso() {
        EuUserChecker checker = new EuUserChecker(getContext());
        assertTrue(checker.isEuIso("DE"));
        assertTrue(checker.isEuIso("de"));
        assertFalse(checker.isEuIso("US"));
    }
}
