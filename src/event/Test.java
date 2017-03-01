package event;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 2017/3/1.
 */
public class Test
{

    public static void main(String [] args )
    {
        //initiate sentenceChangedEvent(Source/Listener) instances
        SentenceChangedEventSource sentenceChangedEventSource;
        sentenceChangedEventSource = new SentenceChangedEventSource();
        SentenceChangedEventListener sentenceChangedEventListener;
        sentenceChangedEventListener = new SentenceChangedEventListener();
        // bind source and listener
        sentenceChangedEventSource.addEnventListener(sentenceChangedEventListener);
        //trigger event
        System.out.println("Now setting a new sentence :");
        sentenceChangedEventSource.setSentence("hello world");
    }
}

/*************************** event class *****************************/
class PropertyChangedEvent
{
    public PropertyChangedEvent(String sourceClassName, Object oldSentence, Object newSentence)
    {
        this.sourceClassName = sourceClassName;
        this.oldProperty = oldSentence;
        this.newProperty = newSentence;
    }

    public String getSourceClassName() {
        return sourceClassName;
    }

    public void setSourceClassName(String sourceClassName) {
        this.sourceClassName = sourceClassName;
    }

    public Object getNewProperty() {
        return newProperty;
    }

    public void setNewProperty(Object newProperty) {
        this.newProperty = newProperty;
    }

    public Object getOldProperty() {
        return oldProperty;
    }

    public void setOldProperty(Object oldProperty) {
        this.oldProperty = oldProperty;
    }

    private String sourceClassName;
    private Object oldProperty;
    private Object newProperty;
};

/*************************** event listener interface *****************************/
interface PropertyChangedEventListener
{
    public void onEvent(PropertyChangedEvent e);
};

/*************************** event listener interface *****************************/
class SentenceChangedEventListener implements PropertyChangedEventListener
{
    public SentenceChangedEventListener(){};

    public void onEvent(PropertyChangedEvent e)
    {
        System.out.println(this.getClass().getName() + " is receiving event from class " + e.getSourceClassName());
        System.out.println( "The old sentence is " +e.getOldProperty());
        System.out.println( "The new sentence is " + e.getNewProperty());
    }
};

/*************************** event source class *****************************/
class SentenceChangedEventSource
{
    public SentenceChangedEventSource()
    {
        this.listeners = new ArrayList<PropertyChangedEventListener>();
        this.sentence = "default sentence";
    }

    public void setSentence(String newSentence)
    {
        PropertyChangedEvent sentenceChangedEvent = new PropertyChangedEvent(
                                                                                    this.getClass().getName(), this.sentence, newSentence);
        // trigger event listener for sentences changing
        fireOnSentenceChangedEvent(sentenceChangedEvent);
        // update sentence
        this.sentence = newSentence;
    }

    public String getSentence()
    {
        return this.sentence;
    }

    public void addEnventListener(PropertyChangedEventListener newListener)
    {
        if(newListener != null)
        {
            this.listeners.add(newListener);
        }
    }

    public void removeEnventListener(PropertyChangedEventListener newListener)
    {
        this.listeners.remove(newListener);
    }

    //send send sentenceChangedEvent
    private void fireOnSentenceChangedEvent(PropertyChangedEvent sentenceChangedEvent)
    {
        int n = this.listeners.size();
        for(int i = 0; i < n; i++)
        {
            this.listeners.get(i).onEvent(sentenceChangedEvent);
        }
    }

    private List<PropertyChangedEventListener> listeners;
    private String sentence;
};

