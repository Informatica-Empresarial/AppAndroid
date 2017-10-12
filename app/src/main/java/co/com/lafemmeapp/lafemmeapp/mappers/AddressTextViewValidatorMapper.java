package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.regex.Pattern;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;

/**
 * Created by Stephys on 7/05/17.
 */

public class AddressTextViewValidatorMapper implements ITextViewValidatorMapper {

    private final String ADDRESS_REGEX = "^[\\w\\#\\-\\.\\,\\¨\\s\\dáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙäëïöüÄËÏÖÜ]*$";

    private Pattern mPattern;

    private static final AddressTextViewValidatorMapper ourInstance = new AddressTextViewValidatorMapper();

    public static AddressTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    private AddressTextViewValidatorMapper() {
        mPattern = Pattern.compile(ADDRESS_REGEX);
    }

    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view()
                .getId(), ( mPattern.matcher(textViewTextChangeEvent.text()).matches() && textViewTextChangeEvent.text().toString().length() > 10));
    }
}
