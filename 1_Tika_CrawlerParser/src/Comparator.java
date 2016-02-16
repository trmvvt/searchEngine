package util;

public interface Comparator {

	  /**
	   * Returns true if the comparator breaks string values up into
	   * tokens when comparing. Necessary because this impacts indexing of
	   * values.
	   */
	  public boolean isTokenized();
	  
	  public double compare(String v1, String v2);
	  
	}