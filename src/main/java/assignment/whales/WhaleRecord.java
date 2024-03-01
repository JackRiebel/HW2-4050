package assignment.whales;

/**
 * This class represents a whale record in the database. Each record consists of two
 * parts: a DataKey and the data associated with the DataKey.
 */
public class WhaleRecord {

    private DataKey key;
    private String about;
    private String sound;
    private String image;

    // default constructor
    public WhaleRecord() {
        this(null, null, null, null);
    }

    public WhaleRecord(DataKey k, String a, String s, String i) {
        key = k;
        about = a;
        sound = s;
        image = i;
    }

    public DataKey getDataKey() {
        return key;
    }

    public String getAbout() {
        return about;
    }
    
 
    public String getSound() {
        return sound;
    }
    
    public String getImage() {
        return image;
    }
    
 


}
