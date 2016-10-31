package com.example.jesper.lab2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jesper on 10/29/16.
 */

public class ExpandableListHelper {
	protected List<String> groups;
	protected List<List<String>> children;
	protected Set<String> allPaths;
	protected Set<String> childNodes;

	// arrays blir till lists (lättare att hantera)
	public ExpandableListHelper(String[] groups, String[][] children) {
		this.groups = Arrays.asList(groups);
		this.children = new ArrayList<>();

		for(String[] group : children)
			this.children.add(Arrays.asList(group));

		allPaths = new HashSet<>();
		childNodes = new HashSet<>();
		generatePaths();
	}

	private void generatePaths() {

		for(int i = 0; i < groups.size(); i++) {
			// Lägg till bara föräldern
			allPaths.add("/" + groups.get(i));

			// Lägg till förälder + barn
			for (String child : children.get(i)) {
				String childPath = "/" + groups.get(i) + "/" + child;
				allPaths.add(childPath);
				childNodes.add(childPath);
			}
		}
	}


	public String getPath(int groupIndex, int childIndex) {
		return "/" + groups.get(groupIndex) + "/" + children.get(groupIndex).get(childIndex);
	}

	public boolean isValidPath(String path) {

		for(String fullPath : allPaths) {
			if(fullPath.startsWith(path))
				return true;
		}

		return false;
	}

	public boolean isChildNode(String path) {

		for(String childPath: childNodes) {
			if(childPath.equals(path))
				return true;
		}

		return false;
	}

	public String getChildFromPath(String path) {
		return path.split("/")[2];
	}

	public int getGroupIndex(String path) {
		String group = path.split("/")[1];

		return groups.indexOf(group);
	}

	// Här antar vi att sökvägen är korrekt skriven (alltså om isChildNode retunerar true)
	// retunerar group-index och child-index i en int-array
	public int[] getIndexes(String path) {

		path = path.substring(1);
		String[] nodes = path.split("/");
		String group = nodes[0];
		String child = nodes[1];

		for(int i = 0; i < groups.size(); i++) {
			// Hittade en grupp med rätt namn
			if(groups.get(i).equals(group)) {

				for(int j = 0; j < children.get(i).size(); j++) {
					if(children.get(i).get(j).equals(child))
						return new int[]{i, j};
				}

			}
		}

		// Borde aldrig hända om man kallat på isChildNode först
		return new int[]{-1, -1};
	}

}
