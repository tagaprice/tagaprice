package org.tagaprice.client.generics.widgets.desktopView;

import org.tagaprice.client.generics.widgets.IMorphWidget;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

public class MorphWidget extends Composite implements IMorphWidget {

	private TextBox _morph = new TextBox();
	private boolean _readonly = true;
	private boolean _isHeadline = false;
	private boolean _alignRight = false;
	
	public MorphWidget() {
		_morph.setStyleName("morphWidget");
		initWidget(_morph);
		
		
		
	}
	
	@Override
	public void setValue(String value){
		_morph.setText(value);
	}

	
	
	@Override
	public String getValue() {
		return _morph.getText();
	}
	
	public void setReadOnly(boolean read){
		
		if(read){
			_readonly=true;
			_morph.setReadOnly(true);
			
			if(_isHeadline){_morph.setStyleName("morphWidget headline");
			}else if(_alignRight){_morph.setStyleName("morphWidget alignRight");
			}else _morph.setStyleName("morphWidget");
		}else{
			_readonly=false;
			_morph.setReadOnly(false);
			
			if(_isHeadline){_morph.setStyleName("morphWidget edit headline");
			}else if(_alignRight){_morph.setStyleName("morphWidget edit alignRight");
			}else _morph.setStyleName("morphWidget edit");
			
		}
		
		
	}

	@Override
	public boolean isReadOnly() {
		return _readonly;
	}

	@Override
	public void config(Type type, boolean notNull, String exampleText,
			boolean alignRight, boolean isHeadline) {
		_isHeadline=isHeadline;
		_alignRight=alignRight;
		
		setReadOnly(_readonly);
		
	}
	
	
}
