/**
 * 
 */
package com.u3b.client.images;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author Rafael Ferreira
 * Provide images to be used in data-entry components.
 */
public interface InputImages extends ClientBundle {

	@Source("minus.jpg")
	ImageResource minus();

	@Source("plus.jpg")
	ImageResource plus();

}
