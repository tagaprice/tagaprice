package org.tagaprice.client.generics.widgets.desktopView;

import java.util.HashMap;
import java.util.Map;

import org.tagaprice.client.generics.widgets.IPropertyDefaultSelecter;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PropertyDefaultSelecter extends Composite implements IPropertyDefaultSelecter{

	private SimplePanel _siPa = new SimplePanel();
	private HashMap<String, String> noRec = new HashMap<String, String>();
	
	public PropertyDefaultSelecter() {
		initWidget(_siPa);
	}
	
	@Override
	public void setPropertyList(Map<String, Object> propertyList) {
		noRec.clear();
		map(propertyList);		
		
		Grid grid = new Grid(noRec.keySet().size(),2);
		grid.setWidth("100%");
		grid.setStyleName("propertyGrid");
		
		int i=0;
		for(String k:noRec.keySet()){
			if(k.equals("_id")){
				if(noRec.get(k).substring(0, 1).equals("n")){
					grid.setWidget(i, 0, new Label("source"));
					grid.setWidget(i, 1, new HTML("<a target=\"_blank\" href=\"http://www.openstreetmap.org/browse/node/"+noRec.get(k).substring(1, noRec.get(k).length())+"\">OSM CC-By-SA</a>"));
				}else if(noRec.get(k).substring(0, 1).equals("w")){
					grid.setWidget(i, 0, new Label("source"));
					grid.setWidget(i, 1, new HTML("<a target=\"_blank\" href=\"http://www.openstreetmap.org/browse/way/"+noRec.get(k).substring(1, noRec.get(k).length())+"\">OSM CC-By-SA</a>"));
				}else if(noRec.get(k).substring(0, 1).equals("r")){
					grid.setWidget(i, 0, new Label("source"));
					grid.setWidget(i, 1, new HTML("<a target=\"_blank\" href=\"http://www.openstreetmap.org/browse/relation/"+noRec.get(k).substring(1, noRec.get(k).length())+"\">OSM CC-By-SA</a>"));
				}
			}else{
				grid.setWidget(i, 0, new Label(k));
				grid.setWidget(i, 1, new Label(noRec.get(k)));
			}
			
			grid.getCellFormatter().setStyleName(i, 0, "namecell");
			grid.getCellFormatter().setStyleName(i, 1, "valuecell");
			
			i++;
		}
		
		_siPa.setWidget(grid);
		
		
	}
	
	private void map(Map<String, Object> propertyList){
		for(String po:propertyList.keySet()){
			
			if(propertyList.get(po) instanceof Map){
				map((Map<String, Object>)propertyList.get(po));
			}else{
				noRec.put(po, ""+propertyList.get(po).toString());
			}
		}
	}

}
