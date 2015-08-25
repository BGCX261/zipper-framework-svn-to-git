/**
 * 
 */
package br.srv.full.faces.utils;



import javax.el.MethodExpression;

import br.srv.full.virtual.entities.faces.FacesAction;
import br.srv.full.virtual.entities.menu.MenuItem;

/**
 * @author Delfino
 * 
 */
public class FacesActionUtils {

	/**
	 * @param p_l_item
	 * @return
	 */
	public static MethodExpression createActionExpression(MenuItem p_l_item) {
		FacesAction l_action = p_l_item.getFacesAction();

		String action = l_action.toString();

		MethodExpression l_exp = null;
		if (action != null) {
			l_exp = FacesUtils.createMethodExpression(action, null, new Class<?>[0]);

		}
		return l_exp;
	}

}
