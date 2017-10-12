package co.com.lafemmeapp.lafemmeapp.mappers;

import android.util.Patterns;

import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import co.com.lafemmeapp.utilmodule.presentation.ITextViewValidatorMapper;
import co.com.lafemmeapp.utilmodule.presentation.events.TextChangedMappedEvent;
import io.reactivex.functions.Function;

/**
 * Created by oscargallon on 4/20/17.
 * This class is a mapper function to check is a email entered is valid or not
 *
 * We implement the {@link ITextViewValidatorMapper} interface that extends from
 * {@link Function<TextViewTextChangeEvent,  TextChangedMappedEvent >}
 * That means that the apply method will receive a {@link TextViewTextChangeEvent} object
 * and will return a TextChangedMappedEvent object
 */
public class EmailTextViewValidatorMapper implements ITextViewValidatorMapper {

    private static final EmailTextViewValidatorMapper ourInstance = new EmailTextViewValidatorMapper();

    public static EmailTextViewValidatorMapper getInstance() {
        return ourInstance;
    }

    private EmailTextViewValidatorMapper() {
    }


    /**
     * @param textViewTextChangeEvent the event with the text enter in an some TextView
     * @return TextChangedMappedEvent that contains the TextView id to identify the component
     * and the value that is a boolean that said if the email is valid or not
     * @throws Exception
     */
    @Override
    public TextChangedMappedEvent apply(TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
        return new TextChangedMappedEvent(textViewTextChangeEvent.view().getId(),
                Patterns.EMAIL_ADDRESS.matcher(textViewTextChangeEvent.text()).matches());
    }
}
