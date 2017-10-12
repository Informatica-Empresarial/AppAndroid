package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.regex.Pattern;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by Stephys on 6/05/17.
 */

public class PhoneNumberValidatorMapper  implements ITextViewValidatorMapper {
    private static final PhoneNumberValidatorMapper ourInstance = new PhoneNumberValidatorMapper();

    public final String PHONE_NUMBER_REGEX = "^(\\d{7}|\\d{10})$";

    private Pattern mPattern;

    public static PhoneNumberValidatorMapper getInstance() {
        return ourInstance;
    }

    private PhoneNumberValidatorMapper() {
        mPattern = Pattern.compile(PHONE_NUMBER_REGEX);
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view()
                .getId(), mPattern.matcher(textViewTextChangeEvent.text()).matches());
    }
}
