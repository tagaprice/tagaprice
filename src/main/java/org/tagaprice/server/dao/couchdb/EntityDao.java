package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.ASimpleEntity;

public class EntityDao extends DAOClass<ASimpleEntity> {

	public EntityDao() {
		super(null, "entity", null);
	}

	protected void _injectFields(AEntity entity) {
		if (entity.getCreatorId() != null) {
			//entity.setCreatorId(userDAO.get(entity.getCreatorId()));
		}
	}
}
