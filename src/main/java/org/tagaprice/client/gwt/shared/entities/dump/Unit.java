package org.tagaprice.client.gwt.shared.entities.dump;
/**
 * Represents Units and converts between them.
 * @author Martin
 *
 */
public enum Unit  {

	piece {
		@Override
		public UnitType getType() {
			return UnitType.Piece;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			throw new UnitNotConvertibleException("A Piece is not convertible");
		}
	}, l{
		@Override
		public UnitType getType() {
			return UnitType.Volume;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
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
			return UnitType.Volume;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			switch (targetUnit) {
			case ml:
				return 1;
			case l:
				return 1000;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	}, g{
		@Override
		public UnitType getType() {
			return UnitType.Mass;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			switch (targetUnit) {
			case g:
				return 1;
			case kg:
				return 1000;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	}, kg{
		@Override
		public UnitType getType() {
			return UnitType.Mass;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			switch(targetUnit) {
			case kg:
				return 1;
			case g:
				return 0.001;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}
	};

	/**
	 * Gives the Type of this Unit
	 * @return the Type of this Unit
	 */
	public UnitType getType() {
		return null;
	}

	/**
	 * Converts a Unit to anoter Unit
	 * @param targetUnit
	 * @return the convertion factor
	 * @throws UnitNotConvertibleException if a conversion is not possible
	 */
	public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
		throw new UnitNotConvertibleException("Call on Unit directly is not allowed");
	}

	/**
	 * Every Type is convertible with itself.
	 * @author Martin
	 *
	 */
	public enum UnitType {
		Length, Area, Volume, Mass, Piece
	}

	public class UnitNotConvertibleException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1722614103910484555L;

		public UnitNotConvertibleException(String message) {
			super(message);
		}
	}



}
