package com.mawasource.pdfscan;

import java.util.Map;

public class Utils {

	public static void printMap(Map<Integer, Integer> map) {
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}
}
