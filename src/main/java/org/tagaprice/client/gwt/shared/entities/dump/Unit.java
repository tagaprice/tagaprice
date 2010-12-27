package org.tagaprice.client.gwt.shared.entities.dump;

public enum Unit implements IUnit {
	piece {
		@Override
		public UnitType getType() {
			return IUnit.UnitType.Piece;
		}

		@Override
		public double convert(Unit targetUnit) throws UnitNotConvertibleException {
			throw new UnitNotConvertibleException("A Piece is not convertible");
		}
	}, l{
		@Override
		public UnitType getType() {
			return IUnit.UnitType.Volume;
		}

		@Override
		public double convert(Unit targetUnit) throws UnitNotConvertibleException {
			switch(targetUnit) {
			case l:
				return 1;
			case ml:
				return 0.001;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	}, ml{
		@Override
		public UnitType getType() {
			return IUnit.UnitType.Volume;
		}

		@Override
		public double convert(Unit targetUnit) throws UnitNotConvertibleException {
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	}, g{
		@Override
		public UnitType getType() {
			return IUnit.UnitType.Mass;
		}

		@Override
		public double convert(Unit targetUnit) throws UnitNotConvertibleException {
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	}, kg{
		@Override
		public UnitType getType() {
			return IUnit.UnitType.Mass;
		}

		@Override
		public double convert(Unit targetUnit) throws UnitNotConvertibleException {
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	};

	@Override
	public UnitType getType() {
		return null;
	}

	@Override
	public double convert(Unit targetUnit) throws UnitNotConvertibleException {
		throw new UnitNotConvertibleException("Call on Unit directly is not allowed");
	}



}
