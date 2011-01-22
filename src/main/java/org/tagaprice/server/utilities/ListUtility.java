package org.tagaprice.server.utilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtility {
	public static <T> void removeDuplicate(List<T> arlList)
	{
		Set<T> h = new HashSet<T>(arlList);
		arlList.clear();
		arlList.addAll(h);
	}
}
