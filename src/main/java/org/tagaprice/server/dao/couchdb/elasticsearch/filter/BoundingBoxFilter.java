package org.tagaprice.server.dao.couchdb.elasticsearch.filter;

import java.util.HashMap;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.Address.LatLon;

public class BoundingBoxFilter implements Filter {
	public static class BoundingBox implements Filter {
		private static final long serialVersionUID = 1L;

		private LatLon m_topLeft, m_bottomRight;

		public BoundingBox() {}

		public BoundingBox convert(org.tagaprice.shared.entities.BoundingBox other) {
			topLeft(new LatLon(other.getNorthLat(), other.getWestLon()));
			bottomRight(new LatLon(other.getSouthLat(), other.getEastLon()));
			return this;
		}
		public BoundingBox topLeft(LatLon topLeft) {
			m_topLeft = topLeft;
			return this;
		}
		
		public BoundingBox bottomRight(LatLon bottomRight) {
			m_bottomRight = bottomRight;
			return this;
		}
		
		@JSONProperty(value="top_left")
		public LatLon getTopLeft() {
			return m_topLeft;
		}
		
		@JSONProperty(value="bottom_right")
		public LatLon getBottomRight() {
			return m_bottomRight;
		}
		
		
		String getHallo() {
			return "bla";
		}
	}
	
	private String m_fieldName;
	private BoundingBox m_bbox;
	
	public BoundingBoxFilter() {}
	
	public BoundingBoxFilter fieldName(String fieldName) {
		m_fieldName = fieldName;
		return this;
	}
	
	public BoundingBoxFilter boundingBox(BoundingBox boundingBox) {
		m_bbox = boundingBox;
		return this;
	}

	@JSONProperty(value="geo_bounding_box")
	public HashMap<String, BoundingBox> getBoundingBoxWrapper() {
		HashMap<String, BoundingBox> rc = new HashMap<String, BoundingBox>();
		rc.put(m_fieldName, m_bbox);
		return rc;
	}
}

