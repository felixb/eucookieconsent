EU Cookie Consent View
======================

This library provides a simple view to get the EU Cookie Consent required by EU and Google.

Key features
------------

 * Removes itself as soon the user acknowledged it once
 * Customizable message and button labels
 * Customizable url to your privacy policy

Adding the view to your activity
--------------------------------

Add the repository and the library to your build.gradle

    repositories {
        maven {
            url 'https://raw.githubusercontent.com/felixb/mvn-repo/master/'
        }
        mavenCentral()
    }

    dependencies {
        compile 'de.ub0r.android:eucookieconsent:+'
    }

Then, add the following xml snippet to your layout xml:

    <de.ub0r.android.eucookieconsent.EuCookieConsentView
            android:id="@+id/eucookieconsent"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

Customizing the view
--------------------

The view supports the following settings in code and xml:

 * `hideOutsideEu` - show/hide view for users outside of EU. This requires android.permission.READ_PHONE_STATE.
 * `textMessage` - the main message
 * `textGotIt` - label of the acknowledge button
 * `textLearnMore` - label of the link to your privacy policy
 * `urlLeanMore` - url to your privacy policy

Set a `AcknowledgeListener` to implement some fancy transition animation or do other stuff when the user acknowledges the view.

Contribution
------------

Translations of the default strings are very welcome.
Please fork and submit a pull request for any changes.

License
-------

This library is licensed under the MIT License.
I don't take any responsibility for this code nor it's compliance with EU or local law.
