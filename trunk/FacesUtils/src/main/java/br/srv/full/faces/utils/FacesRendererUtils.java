/**
 * 
 */
package br.srv.full.faces.utils;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.el.ELResolver;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.base.LRUMap;
import br.srv.full.base.ReflectionUtils;

/**
 * @author Carlos Delfino
 * 
 */
public class FacesRendererUtils {

	private final static Log log = LogFactory.getLog(FacesRendererUtils.class);
	/**
	 * 
	 */
	private static final String OPTION_ATTR = "option";
	/**
	 * 
	 */
	private static final String VALUE_ATTR = "value";
	/**
	 * 
	 */
	private static final String SIZE_ATTR = "size";
	/**
	 * 
	 */
	private static final String SELECT_TAG = "select";
	/**
	 * 
	 */
	public static final String DIV_TAG = "div";
	/**
	 * 
	 */
	public static final String TABLE_TAG = "table";
	/**
	 * 
	 */
	public static final String FOR_ATTR = "for";
	/**
	 * 
	 */
	public static final String NAME_ATTR = "name";
	/**
	 * 
	 */
	public static final String TYPE_ATTR = "type";
	/**
	 * 
	 */
	public static final String AUTOCOMPLETE_ATTR = "autocomplete";
	/**
	 * 
	 */
	public static final String SEP = "_";
	/**
	 * 
	 */
	public static final String INPUT_TAG = "input";
	/**
	 * 
	 */
	public static final String TITLE_ATTR = "title";
	/**
	 * 
	 */
	public static final String LANG_ATTR = "lang";
	/**
	 * 
	 */
	public static final String DIR_ATTR = "dir";
	/**
	 * 
	 */
	public static final String STYLE_ATTR = "style";
	/**
	 * 
	 */
	public static final String CLASS = "class";
	/**
	 * 
	 */
	public static final String STYLE_CLASS_ATTR = "styleClass";
	/**
	 * 
	 */
	public static final String ID_ATTR = "id";
	public static final String TR_TAG = "tr";
	public static final String TD_TAG = "td";
	public static final String LABEL_TAG = "label";
	public static final String SELECTED_ATTR = "selected";

	public static void encodeTextWithLabelToRender(FacesContext p_context, UIComponent p_component, String p_subCompID,
			String p_subCompLabel, String p_currentValue, boolean p_table) throws IOException {

		ResponseWriter l_rw = p_context.getResponseWriter();
		assert (l_rw != null);

		if (p_table) {
			l_rw.startElement(FacesRendererUtils.TR_TAG, p_component);
			l_rw.startElement(FacesRendererUtils.TD_TAG, p_component);
		} else
			l_rw.startElement(DIV_TAG, p_component);

		encodeLabelToRenderer(p_context, p_component, p_subCompID, p_subCompLabel);
		if (p_table) {
			l_rw.endElement(FacesRendererUtils.TD_TAG);
			l_rw.startElement(FacesRendererUtils.TD_TAG, p_component);
		}
		l_rw.startElement(INPUT_TAG, p_component);

		encodeIdAndNameAttribute(p_context, p_component, p_subCompID);

		l_rw.writeAttribute(TYPE_ATTR, "text", null);

		// only output the autocomplete attribute if the value
		// is 'off' since its lack of presence will be interpreted
		// as 'on' by the browser
		String l_autocompleteSubComp = AUTOCOMPLETE_ATTR + SEP + p_subCompID;
		if ("off".equals(p_component.getAttributes().get(l_autocompleteSubComp)))
			l_rw.writeAttribute(AUTOCOMPLETE_ATTR, "off", l_autocompleteSubComp);

		String l_styleClassSubComp = STYLE_CLASS_ATTR + p_subCompID;
		String styleClass = (String) p_component.getAttributes().get(l_styleClassSubComp);
		if (null != styleClass)
			l_rw.writeAttribute(CLASS, styleClass, l_styleClassSubComp);

		String l_styleSubComp = STYLE_ATTR + SEP + p_subCompID;
		String style = (String) p_component.getAttributes().get(l_styleSubComp);
		if (style != null)
			l_rw.writeAttribute(STYLE_ATTR, style, l_styleSubComp);

		String l_dirSubComp = DIR_ATTR + SEP + p_subCompID;
		String dir = (String) p_component.getAttributes().get(l_dirSubComp);
		if (dir != null)
			l_rw.writeAttribute(DIR_ATTR, l_dirSubComp, l_dirSubComp);

		String l_langSubComp = LANG_ATTR + SEP + p_subCompID;
		String lang = (String) p_component.getAttributes().get(l_langSubComp);
		if (lang != null)
			l_rw.writeAttribute(LANG_ATTR, lang, l_langSubComp);

		String titleSubComp = TITLE_ATTR + SEP + p_subCompID;
		String title = (String) p_component.getAttributes().get(titleSubComp);
		if (title != null)
			l_rw.writeAttribute(TITLE_ATTR, title, titleSubComp);

		// render default text specified
		if (p_currentValue != null)
			l_rw.writeAttribute(VALUE_ATTR, p_currentValue, null);

		l_rw.endElement(INPUT_TAG);
		if (p_table) {
			l_rw.endElement(FacesRendererUtils.TD_TAG);
			l_rw.endElement(FacesRendererUtils.TR_TAG);
		} else
			l_rw.endElement(DIV_TAG);

	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_subCompID
	 * @param p_subCompLabel
	 * @param writer
	 * @return
	 * @throws IOException
	 */
	public static void encodeLabelToRenderer(FacesContext p_context, UIComponent p_component, String p_subCompID,
			String p_subCompLabel) throws IOException {
		ResponseWriter l_wr = p_context.getResponseWriter();

		l_wr.startElement(LABEL_TAG, p_component);

		if (p_subCompID != null)
			l_wr.writeAttribute(FOR_ATTR, p_subCompID, null);

		String l_labelStr = createLabelStringIfNeed(p_subCompID, p_subCompLabel);

		if (l_labelStr != null && l_labelStr.length() != 0) {
			String l_labelSubComp = "escape" + SEP + p_subCompID;
			Object l_val = p_component.getAttributes().get(l_labelSubComp);
			boolean l_escape = (l_val != null) && Boolean.valueOf(l_val.toString());
			if (l_escape) {
				l_wr.writeText(l_labelStr, p_component, null);
			} else {
				l_wr.write(l_labelStr);
			}
		}

		l_wr.endElement(LABEL_TAG);
	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_subCompID
	 * @return
	 */
	private static String createSubComponentId(FacesContext p_context, UIComponent p_component, String p_subCompID) {
		return (p_component.getClientId(p_context) + SEP + p_subCompID);
	}

	/**
	 * @param p_subCompID
	 * @param p_subCompLabel
	 * @return
	 */
	private static String createLabelStringIfNeed(String p_subCompID, String p_subCompLabel) {
		return p_subCompLabel != null ? p_subCompLabel : capitalize(p_subCompID) + ":";
	}

	/**
	 * @param p_string
	 * @return
	 */
	private static String capitalize(String p_string) {
		String l_substring = p_string.substring(0, 1);
		return p_string.toLowerCase().replaceFirst(l_substring, l_substring.toUpperCase());
	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_subCompID
	 * @param p_subCompLabel
	 * @param writer
	 * @throws IOException
	 */
	public static void encodeIdAndNameAttribute(FacesContext p_context, UIComponent p_component, String p_subCompID)
			throws IOException {
		ResponseWriter l_wr = p_context.getResponseWriter();
		String l_subCompID = createSubComponentId(p_context, p_component, p_subCompID);
		l_wr.writeAttribute(NAME_ATTR, l_subCompID, null);
		l_wr.writeAttribute(ID_ATTR, l_subCompID, null);
	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_string
	 * @param p_string2
	 * @param p_list
	 * @param p_selected
	 * @param p_selected
	 * @throws IOException
	 */
	public static void encodeSelectWithLabelToRender(FacesContext p_context, UIComponent p_component,
			String p_subCompID, String p_subCompLabel, List<?> p_list, Object p_selected, int p_size, boolean p_useTable)
			throws IOException {
		// TODO Auto-generated method stub

		ResponseWriter l_wr = p_context.getResponseWriter();

		if (p_useTable) {
			l_wr.startElement(TR_TAG, p_component);
			l_wr.startElement(TD_TAG, p_component);
		} else
			l_wr.startElement(DIV_TAG, p_component);

		encodeLabelToRenderer(p_context, p_component, p_subCompID, p_subCompLabel);

		if (p_useTable) {
			l_wr.endElement(TD_TAG);
			l_wr.startElement(TD_TAG, p_component);
		}

		l_wr.startElement(SELECT_TAG, p_component);
		l_wr.writeAttribute(SIZE_ATTR, Math.max(p_size, 1), null);
		encodeIdAndNameAttribute(p_context, p_component, p_subCompID);

		mountSelectList(p_context, p_component, p_list, p_selected);

		l_wr.endElement(SELECT_TAG);
		if (p_useTable) {
			l_wr.endElement(TD_TAG);
			l_wr.endElement(TR_TAG);

		} else
			l_wr.endElement(DIV_TAG);

	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_list
	 * @param p_selected
	 * @param l_wr
	 * @throws IOException
	 */
	private static void mountSelectList(FacesContext p_context, UIComponent p_component, List<?> p_list,
			Object p_selected) throws IOException {
		ResponseWriter l_wr = p_context.getResponseWriter();
		for (Object l_object : p_list) {
			l_wr.startElement(OPTION_ATTR, p_component);

			l_wr.writeAttribute(VALUE_ATTR, l_object, null);

			if (l_object.equals(p_selected)) {

				l_wr.writeAttribute(SELECTED_ATTR, Boolean.TRUE, null);
			}
			Converter l_converter = p_context.getApplication().createConverter(l_object.getClass());
			String l_str;
			if (l_converter != null)
				l_str = l_converter.getAsString(p_context, p_component, l_object);
			else
				l_str = l_object.toString();
			l_wr.writeText(l_object, l_str);
			l_wr.endElement(OPTION_ATTR);
		}
	}

	/**
	 * @param p_context
	 * @param p_component
	 * @throws IOException
	 */
	public static void encodeIdAndNameAttribute(FacesContext p_context, UIComponent p_component) throws IOException {
		ResponseWriter l_wr = p_context.getResponseWriter();
		String l_subCompID = p_component.getClientId(p_context);
		l_wr.writeAttribute(NAME_ATTR, l_subCompID, null);
		l_wr.writeAttribute(ID_ATTR, l_subCompID, null);
	}

	/**
	 * @param p_context
	 * @param p_component
	 */
	public static void encodeHeaderFacet(FacesContext p_context, UIComponent p_component) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param p_context
	 * @param p_component
	 */
	public static void encodeBottonFacet(FacesContext p_context, UIComponent p_component) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param p_context
	 * @param p_component
	 * @param p_string
	 * @param p_string2
	 * @param p_superLocales
	 * @param p_selected
	 * @param p_b
	 * @throws IOException
	 */
	public static void encodeSelectWithLabelToRender(FacesContext p_context, UIComponent p_component,
			String p_subCompID, String p_subCompLabel, List<?> p_list, Object p_selected, boolean p_useTable)
			throws IOException {
		encodeSelectWithLabelToRender(p_context, p_component, p_subCompID, p_subCompLabel, p_list, p_selected, 1,
				p_useTable);
	}

	/**
	 * @param p_context
	 * @param p_component
	 */
	public static void rendererParamsNotNull(FacesContext p_context, UIComponent p_component) {
		// TODO Auto-generated method stub

		FacesUtils.notNull("context", p_context);
		FacesUtils.notNull("component", p_component);

	}

	protected boolean shouldEncode(UIComponent component) {

		// suppress rendering if "rendered" property on the component is
		// false.
		if (!component.isRendered()) {
			if (log.isInfoEnabled()) {
				log.info(String.format("End encoding component {0} since rendered attribute is set to false", component
						.getId()));
			}
			return false;
		}
		return true;

	}

	public static boolean shouldDecode(UIComponent component) {

		if (componentIsDisabledOrReadonly(component)) {
			if (log.isInfoEnabled()) {
				log.info(String.format("No decoding necessary since the component {0} is disabled or read-only",
						component.getId()));
			}
			return false;
		}
		return true;

	}

	protected boolean shouldEncodeChildren(UIComponent component) {

		// suppress rendering if "rendered" property on the component is
		// false.
		if (!component.isRendered()) {
			if (log.isInfoEnabled()) {
				log
						.info(String
								.format(
										"Children of component {0} will not be encoded since this component's rendered attribute is false",
										component.getId()));
			}
			return false;
		}
		return true;

	}

	// README - make sure to add the message identifier constant
	// (ex: Util.CONVERSION_ERROR_MESSAGE_ID) and the number of substitution
	// parameters to test/com/sun/faces/util/TestUtil_messages (see comment
	// there).

	// Loggers
	public static final String RENDERKIT_LOGGER = ".renderkit";
	public static final String TAGLIB_LOGGER = ".taglib";
	public static final String APPLICATION_LOGGER = ".application";
	public static final String CONTEXT_LOGGER = ".context";
	public static final String CONFIG_LOGGER = ".config";
	public static final String LIFECYCLE_LOGGER = ".lifecycle";
	public static final String TIMING_LOGGER = ".timing";

	/**
	 * Flag that, when true, enables special behavior in the RI to enable unit
	 * testing.
	 */
	private static boolean unitTestModeEnabled = false;

	/**
	 * Flag that enables/disables the core TLV.
	 */
	private static boolean coreTLVEnabled = true;

	/**
	 * Flag that enables/disables the html TLV.
	 */
	private static boolean htmlTLVEnabled = true;

	private static final Map<String, Pattern> patternCache = new LRUMap<String, Pattern>(15);

	/**
	 * <p>
	 * Factory method for creating the varius JSF listener instances that may be
	 * referenced by <code>type</code> or <code>binding</code>.
	 * </p>
	 * <p>
	 * If <code>binding</code> is not <code>null</code> and the evaluation
	 * result is not <code>null</code> return that instance. Otherwise try to
	 * instantiate an instances based on <code>type</code>.
	 * </p>
	 * 
	 * @param type
	 *            the <code>Listener</code> type
	 * @param binding
	 *            a <code>ValueExpression</code> which resolves to a
	 *            <code>Listener</code> instance
	 * @return a <code>Listener</code> instance based off the provided
	 *         <code>type</code> and <binding>
	 */
	public static Object getListenerInstance(ValueExpression type, ValueExpression binding) {

		FacesContext faces = FacesContext.getCurrentInstance();
		Object instance = null;
		if (faces == null) {
			return null;
		}
		if (binding != null) {
			instance = binding.getValue(faces.getELContext());
		}
		if (instance == null && type != null) {
			try {
				instance = ReflectionUtils.newInstance(((String) type.getValue(faces.getELContext())));
			} catch (Exception e) {
				throw new AbortProcessingException(e.getMessage(), e);
			}

			if (binding != null) {
				binding.setValue(faces.getELContext(), instance);
			}
		}

		return instance;

	}

	public static void setUnitTestModeEnabled(boolean enabled) {
		unitTestModeEnabled = enabled;
	}

	public static boolean isUnitTestModeEnabled() {
		return unitTestModeEnabled;
	}

	public static void setCoreTLVActive(boolean active) {
		coreTLVEnabled = active;
	}

	public static boolean isCoreTLVActive() {
		return coreTLVEnabled;
	}

	public static void setHtmlTLVActive(boolean active) {
		htmlTLVEnabled = active;
	}

	public static boolean isHtmlTLVActive() {
		return htmlTLVEnabled;
	}


	public static void notNull(String varname, Object var) {

		if (var == null) {
			throw new NullPointerException(FacesMessageUtils.getExceptionMessageString(
					FacesMessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, varname));
		}

	}

	/**
	 * @param context
	 *            the <code>FacesContext</code> for the current request
	 * @return the Locale from the UIViewRoot, the the value of
	 *         Locale.getDefault()
	 */
	public static Locale getLocaleFromContextOrSystem(FacesContext context) {
		Locale result, temp = Locale.getDefault();
		UIViewRoot root;
		result = temp;
		if (null != context) {
			if (null != (root = context.getViewRoot())) {
				if (null == (result = root.getLocale())) {
					result = temp;
				}
			}
		}
		return result;
	}

	public static Converter getConverterForClass(Class converterClass, FacesContext context) {
		if (converterClass == null) {
			return null;
		}
		try {
			Application application = context.getApplication();
			return (application.createConverter(converterClass));
		} catch (Exception e) {
			return (null);
		}
	}

	public static Converter getConverterForIdentifer(String converterId, FacesContext context) {
		if (converterId == null) {
			return null;
		}
		try {
			Application application = context.getApplication();
			return (application.createConverter(converterId));
		} catch (Exception e) {
			return (null);
		}
	}

	public static StateManager getStateManager(FacesContext context) throws FacesException {
		return (context.getApplication().getStateManager());
	}

	public static ViewHandler getViewHandler(FacesContext context) throws FacesException {
		// Get Application instance
		Application application = context.getApplication();
		assert (application != null);

		// Get the ViewHandler
		ViewHandler viewHandler = application.getViewHandler();
		assert (viewHandler != null);

		return viewHandler;
	}

	public static boolean componentIsDisabled(UIComponent component) {

		return (Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled"))));

	}

	public static boolean componentIsDisabledOrReadonly(UIComponent component) {
		return Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled")))
				|| Boolean.valueOf(String.valueOf(component.getAttributes().get("readonly")));
	}

	// W3C XML specification refers to IETF RFC 1766 for language code
	// structure, therefore the value for the xml:lang attribute should
	// be in the form of language or language-country or
	// language-country-variant.

	public static Locale getLocaleFromString(String localeStr) throws IllegalArgumentException {
		// length must be at least 2.
		if (null == localeStr || localeStr.length() < 2) {
			throw new IllegalArgumentException("Illegal locale String: " + localeStr);
		}

		Locale result = null;
		String lang = null, country = null, variant = null;
		char[] seps = { '-', '_' };
		int i = 0, j = 0;

		// to have a language, the length must be >= 2
		if ((localeStr.length() >= 2) && (-1 == (i = indexOfSet(localeStr, seps, 0)))) {
			// we have only Language, no country or variant
			if (2 != localeStr.length()) {
				throw new IllegalArgumentException("Illegal locale String: " + localeStr);
			}
			lang = localeStr.toLowerCase();
		}

		// we have a separator, it must be either '-' or '_'
		if (-1 != i) {
			lang = localeStr.substring(0, i);
			// look for the country sep.
			// to have a country, the length must be >= 5
			if ((localeStr.length() >= 5) && (-1 == (j = indexOfSet(localeStr, seps, i + 1)))) {
				// no further separators, length must be 5
				if (5 != localeStr.length()) {
					throw new IllegalArgumentException("Illegal locale String: " + localeStr);
				}
				country = localeStr.substring(i + 1);
			}
			if (-1 != j) {
				country = localeStr.substring(i + 1, j);
				// if we have enough separators for language, locale,
				// and variant, the length must be >= 8.
				if (localeStr.length() >= 8) {
					variant = localeStr.substring(j + 1);
				} else {
					throw new IllegalArgumentException("Illegal locale String: " + localeStr);
				}
			}
		}
		if (null != variant && null != country && null != lang) {
			result = new Locale(lang, country, variant);
		} else if (null != lang && null != country) {
			result = new Locale(lang, country);
		} else if (null != lang) {
			result = new Locale(lang, "");
		}
		return result;
	}

	/**
	 * @param str
	 *            local string
	 * @param set
	 *            the substring
	 * @param fromIndex
	 *            starting index
	 * @return starting at <code>fromIndex</code>, the index of the first
	 *         occurrence of any substring from <code>set</code> in
	 *         <code>toSearch</code>, or -1 if no such match is found
	 */
	public static int indexOfSet(String str, char[] set, int fromIndex) {
		int result = -1;
		char[] toSearch = str.toCharArray();
		for (int i = fromIndex, len = toSearch.length; i < len; i++) {
			for (int j = 0, innerLen = set.length; j < innerLen; j++) {
				if (toSearch[i] == set[j]) {
					result = i;
					break;
				}
			}
			if (-1 != result) {
				break;
			}
		}
		return result;
	}

	public static void parameterNonNull(Object param) throws FacesException {
		if (null == param) {
			throw new FacesException(FacesMessageUtils.getExceptionMessageString(
					FacesMessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "param"));
		}
	}

	public static void parameterNonEmpty(String param) throws FacesException {
		if (null == param || 0 == param.length()) {
			throw new FacesException(FacesMessageUtils.getExceptionMessageString(FacesMessageUtils.EMPTY_PARAMETER_ID));
		}
	}


	/**
	 * <p>
	 * A slightly more efficient version of <code>String.split()</code> which
	 * caches the <code>Pattern</code>s in an LRUMap instead of creating a
	 * new <code>Pattern</code> on each invocation.
	 * </p>
	 * 
	 * @param toSplit
	 *            the string to split
	 * @param regex
	 *            the regex used for splitting
	 * @return the result of <code>Pattern.spit(String, int)</code>
	 */
	public synchronized static String[] split(String toSplit, String regex) {
		Pattern pattern = patternCache.get(regex);
		if (pattern == null) {
			pattern = Pattern.compile(regex);
			patternCache.put(regex, pattern);
		}
		return pattern.split(toSplit, 0);
	}

	/**
	 * <p>
	 * Returns the URL pattern of the {@link javax.faces.webapp.FacesServlet}
	 * that is executing the current request. If there are multiple URL
	 * patterns, the value returned by
	 * <code>HttpServletRequest.getServletPath()</code> and
	 * <code>HttpServletRequest.getPathInfo()</code> is used to determine
	 * which mapping to return.
	 * </p>
	 * If no mapping can be determined, it most likely means that this
	 * particular request wasn't dispatched through the
	 * {@link javax.faces.webapp.FacesServlet}.
	 * 
	 * @param context
	 *            the {@link FacesContext} of the current request
	 * 
	 * @return the URL pattern of the {@link javax.faces.webapp.FacesServlet} or
	 *         <code>null</code> if no mapping can be determined
	 * 
	 * @throws NullPointerException
	 *             if <code>context</code> is null
	 */
	public static String getFacesMapping(FacesContext context) {

		if (context == null) {
			String message = FacesMessageUtils.getExceptionMessageString(
					FacesMessageUtils.NULL_PARAMETERS_ERROR_MESSAGE_ID, "context");
			throw new NullPointerException(message);
		}

		// Check for a previously stored mapping
		ExternalContext extContext = context.getExternalContext();
		String mapping = null;
		// mapping = (String) RequestStateManager.get(context,
		// RequestStateManager.INVOCATION_PATH);

		if (mapping == null) {

			// first check for javax.servlet.forward.servlet_path
			// and javax.servlet.forward.path_info for non-null
			// values. if either is non-null, use this
			// information to generate determine the mapping.

			String servletPath = extContext.getRequestServletPath();
			String pathInfo = extContext.getRequestPathInfo();

			mapping = getMappingForRequest(servletPath, pathInfo);
			if (mapping == null) {
				if (log.isInfoEnabled()) {
					// log.info("jsf.faces_servlet_mapping_cannot_be_determined_error",
					// new Object[] { servletPath });
				}
			}
		}

		// if the FacesServlet is mapped to /* throw an
		// Exception in order to prevent an endless
		// RequestDispatcher loop
		// if ("/*".equals(mapping)) {
		// throw new FacesException(MessageUtils.getExceptionMessageString(
		// MessageUtils.FACES_SERVLET_MAPPING_INCORRECT_ID));
		// }

		if (mapping != null) {
			// RequestStateManager.set(context,
			// RequestStateManager.INVOCATION_PATH, mapping);
		}
		if (log.isInfoEnabled()) {
			log.info("URL pattern of the FacesServlet executing the current request " + mapping);
		}
		return mapping;
	}

	/**
	 * <p>
	 * Return the appropriate {@link javax.faces.webapp.FacesServlet} mapping
	 * based on the servlet path of the current request.
	 * </p>
	 * 
	 * @param servletPath
	 *            the servlet path of the request
	 * @param pathInfo
	 *            the path info of the request
	 * 
	 * @return the appropriate mapping based on the current request
	 * 
	 * @see javax.servlet.http.HttpServletRequest#getServletPath()
	 */
	private static String getMappingForRequest(String servletPath, String pathInfo) {

		if (servletPath == null) {
			return null;
		}
		if (log.isInfoEnabled()) {
			log.info("servletPath " + servletPath);
			log.info("pathInfo " + pathInfo);
		}
		// If the path returned by HttpServletRequest.getServletPath()
		// returns a zero-length String, then the FacesServlet has
		// been mapped to '/*'.
		if (servletPath.length() == 0) {
			return "/*";
		}

		// presence of path info means we were invoked
		// using a prefix path mapping
		if (pathInfo != null) {
			return servletPath;
		} else if (servletPath.indexOf('.') < 0) {
			// if pathInfo is null and no '.' is present, assume the
			// FacesServlet was invoked using prefix path but without
			// any pathInfo - i.e. GET /contextroot/faces or
			// GET /contextroot/faces/
			return servletPath;
		} else {
			// Servlet invoked using extension mapping
			return servletPath.substring(servletPath.lastIndexOf('.'));
		}
	}

	/**
	 * <p>
	 * Returns true if the provided <code>url-mapping</code> is a prefix path
	 * mapping (starts with <code>/</code>).
	 * </p>
	 * 
	 * @param mapping
	 *            a <code>url-pattern</code>
	 * @return true if the mapping starts with <code>/</code>
	 */
	public static boolean isPrefixMapped(String mapping) {
		return (mapping.charAt(0) == '/');
	}

}
