package org.tagaprice.shared.entities;

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
	},

	l{
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

		@Override
		public Unit[] getRelativeTypes(){
			Unit[] u = {ml,l};
			return u;
		}
	},

	ml{
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

		@Override
		public Unit[] getRelativeTypes(){
			Unit[] u = {ml,l};
			return u;
		}
	},

	mg{
		@Override
		public UnitType getType() {
			return UnitType.Mass;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			switch (targetUnit) {
			case mg:
				return 1;
			case g:
				return 1000;
			case kg:
				return 1000000;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}

		@Override
		public Unit[] getRelativeTypes(){
			Unit[] u = {kg,g,mg};
			return u;
		}
	},

	g{
		@Override
		public UnitType getType() {
			return UnitType.Mass;
		}

		@Override
		public double getFactor(Unit targetUnit) throws UnitNotConvertibleException {
			switch (targetUnit) {
			case mg:
				return 0.001;
			case g:
				return 1;
			case kg:
				return 1000;
			}
			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}

		@Override
		public Unit[] getRelativeTypes(){
			Unit[] u = {kg,g,mg};
			return u;
		}
	},

	kg{
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
			case mg:
				return 0.000001;
			}

			throw new UnitNotConvertibleException("It is not possible to convert " + this.getType().toString() + " into " + targetUnit.getType().toString());
		}

		@Override
		public Unit[] getRelativeTypes(){
			Unit[] u = {kg,g,mg};
			return u;
		}

	};

	/**
	 * Gives the Type of this Unit
	 * @return the Type of this Unit
	 */
	public UnitType getType() {
		return null;
	}

	public Unit[] getRelativeTypes(){
		Unit[] u = {piece, ml,l,kg,g,mg};
		return u;
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
