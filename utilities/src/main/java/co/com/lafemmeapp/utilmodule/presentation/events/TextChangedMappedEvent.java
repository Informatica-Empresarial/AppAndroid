package co.com.lafemmeapp.utilmodule.presentation.events;

/**
 * Created by oscargallon on 4/20/17.
 * This Object will transport the data from a TextView text change event,
 */
public class TextChangedMappedEvent {

    /**
     * {@link android.widget.TextView} id
     */
    private final int sourceId;

    /**
     * The text on the source {@link android.widget.TextView} was valid or not
     */
    private final boolean isValid;

    public TextChangedMappedEvent(int sourceId, boolean isValid) {
        this.sourceId = sourceId;
        this.isValid = isValid;
    }



    public int getSourceId() {
        return sourceId;
    }

    public boolean isValid() {
        return isValid;
    }
}
