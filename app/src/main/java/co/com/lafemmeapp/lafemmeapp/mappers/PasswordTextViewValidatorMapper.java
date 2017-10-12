package co.com.lafemmeapp.lafemmeapp.mappers;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/20/17.
 * This class is a mapper function to check is a password entered is valid or not
 * <p>
 * We implement the {@link ITextViewValidatorMapper} interface that extends from
 * {@link Function<TextViewTextChangeEvent,  TextChangedMappedEvent >}
 * That means that the apply method will receive a {@link TextViewTextChangeEvent} object
 * and will return a TextChangedMappedEvent object
 */
public class PasswordTextViewValidatorMapper implements ITextViewValidatorMapper {

    private static final PasswordTextViewValidatorMapper
            ourInstance = new PasswordTextViewValidatorMapper();

    /**
     * This is the regex used to validate the password
     * The regex validate that the password should have
     * one upper case character and his length have to between 6 and 16 characters
     */
    private static final String PASSWORD_VALIDATOR_REGEX = "^(?=.*[a-z])(?=.*[A-Z]).{6,16}$";

    private final String PASSWORD_TEMP = "(?=.*[a-z]).{6,16}$";

    /**
     * This is the pattern object used to validate the regex
     */
    private Pattern mPattern;

    /**
     * This is the matcher object that will said is the patters is valid or not
     */
    private Matcher mMatcher;


    private PasswordTextViewValidatorMapper() {
        /**
         * We create the pattern compiling the regex
         */
        mPattern = Pattern.compile(PASSWORD_TEMP);

    }

    public static PasswordTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    /**
     * @param textViewTextChangeEvent the event with the text enter in an some TextView
     * @return TextChangedMappedEvent that contains the TextView id to identify the component
     * and the value that is a boolean that said if the password is valid or not
     * @throws Exception
     */
    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        PasswordConfirmationTextViewValidatorMapper.getInstance().setPwdToCheck(textViewTextChangeEvent.text().toString());
        /**
         * So what we do here is with the pattern we create a matcher that will evaluate the
         * text from the TextView and then we see if the text matches or not
         */
        return new TextChangedMappedEvent(textViewTextChangeEvent.view().getId(),
                mPattern.matcher(textViewTextChangeEvent.text()).matches());
    }
}
