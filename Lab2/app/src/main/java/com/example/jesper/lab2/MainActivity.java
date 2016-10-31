package com.example.jesper.lab2;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

	/**
	 * Skapa en adapter genom att ärva från BaseExpandableListView
	 * http://www.journaldev.com/9942/android-expandablelistview-example-tutorial
	 *
	 * Använda den färdiga adaptern SimpleExpandableListView
	 * https://newfivefour.com/android-SimpleExpandableListAdapter-example.html
	 *
	 * https://commonsware.com/Android/previews/widget-catalog-expandablelistview
	 */
	private ExpandableListView listView;
	private EditText textField;
	private View currentMarked;
	private int currentGroup, currentChild;

	private final String[] GROUPS = {"apa",	"banan", "apa"};

	private final String[][] CHILDREN = {
			{"harambe", "nicke", "king", "kong"},
			{"gul", "integul", "omogen"},
			{"harambe", "bing", "kong"}
	};

	private final String ROOT_NAME = "ROOT", CHILD_NAME = "CHILD";
	SimpleExpandableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ExpandableListView) findViewById(R.id.list);
		textField = (EditText) findViewById(R.id.testField);

		// Klass för att kolla om sökvägar finns, hitta indexes m.m.
		final ExpandableListHelper helper = new ExpandableListHelper(GROUPS, CHILDREN);

		 adapter = new SimpleExpandableListAdapter(
				this,

				// Groups
				getGroups(GROUPS),
				R.layout.list_group,
				new String[] {ROOT_NAME},
				new int[] {R.id.listGroup},

				// Children
				getChildren(CHILDREN),
				R.layout.list_item,
				new String[] {CHILD_NAME},
				new int[] {R.id.listItem}
		);

		listView.setAdapter(adapter);

		final TextWatcher textWatcher = new TextWatcher() {
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

			@Override
			public void afterTextChanged(Editable editable) {
				String path = editable.toString();

				if(helper.isValidPath(path)) {
					textField.setBackgroundColor(Color.TRANSPARENT);

					if(helper.isChildNode(path)) {
						int[] indexes = helper.getIndexes(path);
						currentGroup = indexes[0];
						currentChild = indexes[1];

						listView.expandGroup(indexes[0]);
					}
				} else { // Inte valid path
					textField.setBackgroundColor(Color.RED);

					if(currentMarked != null) // Tar bort markeringar
						currentMarked.setBackgroundColor(Color.TRANSPARENT);
				}
			}
		};

		listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int i) {

				Toast.makeText(
						getApplicationContext(),
						"Group expanded",
						Toast.LENGTH_SHORT).show();
				fixCurrentMarking();
			}
		});

		listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int i) {
				fixCurrentMarking();
			}
		});

		textField.addTextChangedListener(textWatcher);

		listView.setOnChildClickListener(
				new ExpandableListView.OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent, View child, int groupPos, int childPos, long id) {
						// Ta bort textWatcher temporärt (så vi inte kollar saker två gånger)
						textField.removeTextChangedListener(textWatcher);
						textField.setText(helper.getPath(groupPos, childPos));
						textField.addTextChangedListener(textWatcher);

						//currentC
						setMarked(child);
						return false;
					}
				}
		);

	}

	private void setMarked(View child) {
		if(currentMarked != null) {
			currentMarked.setBackgroundColor(Color.TRANSPARENT);
		}

		child.setBackgroundColor(Color.GREEN);
		currentMarked = child;

		// Återställ bakgrunden på textfältet
		textField.setBackgroundColor(Color.TRANSPARENT);
	}


	private void fixCurrentMarking() {

		int groupNr = -1;
		int childNr = 0;

		for (int i = 0; i < listView.getChildCount(); i++) {
			LinearLayout child = (LinearLayout) listView.getChildAt(i);
			TextView text = (TextView) child.getChildAt(0);

			Toast.makeText(
					this,
					text.getText(),
					Toast.LENGTH_SHORT).show();

			if(child.getId() == R.id.listGroup)
				groupNr++;
			else if(groupNr == currentGroup && childNr == currentChild)  // Hittade rätt barn
				child.setBackgroundColor(Color.GREEN);
			else
				child.setBackgroundColor(Color.TRANSPARENT);

			if(groupNr == currentGroup)
				childNr++;
		}
	}

	private void setMarked(String childName) {

		for(int i = 0; i < listView.getChildCount(); i++) {
			LinearLayout child = (LinearLayout) listView.getChildAt(i);
			TextView text = (TextView) child.getChildAt(0);


			Toast.makeText(
					this,
					text.getText(),
					Toast.LENGTH_SHORT).show();
			//if(listView.getChildAt(i).)

		}

		//ExpandableListAdapter adapter = listView.getExpandableListAdapter();
		//LinearLayout parent = (LinearLayout) listView.getChildAt(groupPos);


	}

	private List<Map<String, String>> getGroups(String[] groups) {
		List<Map<String, String>> list = new ArrayList<>();

		for(int i = 0; i < groups.length; i++) {
			HashMap<String, String> group = new HashMap<>();
			group.put(ROOT_NAME, groups[i]);
			list.add(group);
		}

		return list;
	}

	private List<List<Map<String, String>>> getChildren(String[][] children) {
		List<List<Map<String, String>>> list = new ArrayList<>();

		// Loopa genom alla grupper
		for(int i = 0; i < children.length; i++) {
			List<Map<String, String>> group = new ArrayList<>();

			// Loopa genom alla barn
			for(int j = 0; j < children[i].length; j++) {
				HashMap<String, String> child = new HashMap<>();
				child.put(CHILD_NAME, children[i][j]);
				group.add(child);
			}

			list.add(group);
		}

		return list;
	}

}
