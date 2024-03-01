package assignment.whales;

public class DataKey {
	private String whaleName;
	private int whaleSize;

	// default constructor
	public DataKey() {
		this(null, 0);
	}
        
	public DataKey(String name, int size) {
		whaleName = name;
		whaleSize = size;
	}

	public String getWhaleName() {
		return whaleName;
	}

	public int getWhaleSize() {
		return whaleSize;
	}

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
            if (this.getWhaleSize() == k.getWhaleSize()) {
                int compare = this.whaleName.compareTo(k.getWhaleName());
                if (compare == 0){
                     return 0;
                } 
                else if (compare < 0) {
                    return -1;
                }
            }
            else if(this.getWhaleSize() < k.getWhaleSize()){
                    return -1;
            }
            return 1;
            
	}
}
