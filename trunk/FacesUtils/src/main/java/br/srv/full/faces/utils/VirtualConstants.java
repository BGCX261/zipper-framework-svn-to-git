/*
 * $Id: RIConstants.java,v 1.94.4.1 2007/12/17 21:14:40 rlubke Exp $
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You may
 * not use this file except in compliance with the License. You can obtain a
 * copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL.html or
 * glassfish/bootstrap/legal/LICENSE.txt. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in
 * each file and include the License file at
 * glassfish/bootstrap/legal/LICENSE.txt. Sun designates this particular
 * file as subject to the "Classpath" exception as provided by Sun in the
 * GPL Version 2 section of the License file that accompanied this code. If
 * applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or
 * GPL Version 2] license." If you don't indicate a single choice of
 * license, a recipient has the option to distribute your version of this
 * file under either the CDDL, the GPL Version 2 or to extend the choice of
 * license to its licensees as provided above. However, if you add GPL
 * Version 2 code and therefore, elected the GPL Version 2 license, then the
 * option applies only if the new code is made subject to such option by the
 * copyright holder.
 *//**
 * 
 */
package br.srv.full.faces.utils;

import javax.faces.render.RenderKitFactory;

/**
 * @author Carlos Delfino
 * 
 */
public class VirtualConstants {

	/**
	 * Used to add uniqueness to the names.
	 */
	public final static String FACES_PREFIX = "virtual.faces.";

	public final static String HTML_BASIC_RENDER_KIT = FACES_PREFIX + RenderKitFactory.HTML_BASIC_RENDER_KIT;

	public static final String SAVESTATE_FIELD_DELIMITER = "~";
	public static final String SAVESTATE_FIELD_MARKER = SAVESTATE_FIELD_DELIMITER + FACES_PREFIX
			+ "saveStateFieldMarker" + SAVESTATE_FIELD_DELIMITER;

	public static final String SAVED_STATE = FACES_PREFIX + "savedState";

	/*
	 * <p>TLV Resource Bundle Location </p>
	 */
	public static final String TLV_RESOURCE_LOCATION = FACES_PREFIX + "resources.Resources";

	public static final Object NO_VALUE = "";
 

	@SuppressWarnings("unchecked")
	public static final Class[] EMPTY_CLASS_ARGS = new Class[0];
	public static final Object[] EMPTY_METH_ARGS = new Object[0];

	/**
	 * <p>
	 * ResponseWriter Content Types and Encoding
	 * </p>
	 */
	public static final String HTML_CONTENT_TYPE = "text/html";
	public static final String XHTML_CONTENT_TYPE = "application/xhtml+xml";
	public static final String APPLICATION_XML_CONTENT_TYPE = "application/xml";
	public static final String TEXT_XML_CONTENT_TYPE = "text/xml";
	public static final String ALL_MEDIA = "*/*";
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String SUN_JSF_JS_URI = "com_sun_faces_sunjsf.js";
	public static final String DEFAULT_LIFECYCLE = FACES_PREFIX + "DefaultLifecycle";
	public static final String DEFAULT_STATEMANAGER = FACES_PREFIX + "DefaultStateManager";

	protected VirtualConstants() {

		throw new IllegalStateException();

	}

}
