package org.tagaprice.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * 
 *
 */
public interface MyResources extends ClientBundle {
    public static final MyResources INSTANCE = GWT.create(MyResources.class);
    
    String PATH = "images/";
    
    @Source(PATH+"barcode_logo.png")
    ImageResource logo();
    
    @Source(PATH+"rating0.png")
    ImageResource rating0();
    
    @Source(PATH+"rating1.png")
    ImageResource rating1();
    
    @Source(PATH+"rating2.png")
    ImageResource rating2();
    
    @Source(PATH+"rating3.png")
    ImageResource rating3();
    
    @Source(PATH+"rating4.png")
    ImageResource rating4();
    
    @Source(PATH+"rating5.png")
    ImageResource rating5();

    @Source(PATH+"ratingChoose1.png")
    ImageResource ratingChoose1();
    
    @Source(PATH+"ratingChoose2.png")
    ImageResource ratingChoose2();
    
    @Source(PATH+"ratingChoose3.png")
    ImageResource ratingChoose3();
    
    @Source(PATH+"ratingChoose4.png")
    ImageResource ratingChoose4();
    
    @Source(PATH+"ratingChoose5.png")
    ImageResource ratingChoose5();
    
    @Source(PATH+"picPreviewProductpng.png")
    ImageResource productPriview();

    
    //plusMinus
    @Source(PATH+"minusActive.png")
    ImageResource minusActive();
    
    @Source(PATH+"minusInactive.png")
    ImageResource minusInactive();
    
    @Source(PATH+"plusActive.png")
    ImageResource plusActive();
    
    @Source(PATH+"plusInactive.png")
    ImageResource plusInactive();
}

