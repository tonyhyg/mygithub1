package com.example.tabhost_example;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<PhoneItem> {

	public int compare(PhoneItem o1, PhoneItem o2) {
		if (o1.nameLetter.equals("@")
				|| o2.nameLetter.equals("#")) {
			return -1;
		} else if (o1.nameLetter.equals("#")
				|| o2.nameLetter.equals("@")) {
			return 1;
		} else {
			return o1.nameLetter.compareTo(o2.nameLetter);
		}
	}

}
