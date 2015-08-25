package br.srv.full.faces.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class FacesMessageUtils {

	/**
	 * Below I use the same contants name and algoritms with JSF RI
	 * implementations.
	 * 
	 */
	public static final String APPLICATION_ASSOCIATE_CTOR_WRONG_CALLSTACK_ID = "virtual.faces.APPLICATION_ASSOCIATE_CTOR_WRONG_CALLSTACK";
	public static final String APPLICATION_ASSOCIATE_EXISTS_ID = "virtual.faces.APPLICATION_ASSOCIATE_EXISTS";
	public static final String APPLICATION_INIT_COMPLETE_ERROR_ID = "virtual.faces.APPLICATION_INIT_COMPLETE_ERROR_ID";
	public static final String ASSERTION_FAILED_ID = "virtual.faces.ASSERTION_FAILED";
	public static final String ATTRIBUTE_NOT_SUPORTED_ERROR_MESSAGE_ID = "virtual.faces.ATTRIBUTE_NOT_SUPORTED";
	public static final String CANT_CLOSE_INPUT_STREAM_ID = "virtual.faces.CANT_CLOSE_INPUT_STREAM";
	public static final String CANT_CONVERT_VALUE_ERROR_MESSAGE_ID = "virtual.faces.CANT_CONVERT_VALUE";
	public static final String CANT_CREATE_CLASS_ERROR_ID = "virtual.faces.CANT_CREATE_CLASS_ERROR";
	public static final String CANT_CREATE_LIFECYCLE_ERROR_MESSAGE_ID = "virtual.faces.CANT_CREATE_LIFECYCLE_ERROR";
	public static final String CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID = "virtual.faces.CANT_INSTANTIATE_CLASS";
	public static final String CANT_INTROSPECT_CLASS_ERROR_MESSAGE_ID = "virtual.faces.CANT_INTROSPECT_CLASS";
	public static final String CANT_LOAD_CLASS_ERROR_MESSAGE_ID = "virtual.faces.CANT_INSTANTIATE_CLASS";
	public static final String CANT_PARSE_FILE_ERROR_MESSAGE_ID = "virtual.faces.CANT_PARSE_FILE";
	public static final String CANT_WRITE_ID_ATTRIBUTE_ERROR_MESSAGE_ID = "virtual.faces.CANT_WRITE_ID_ATTRIBUTE";
	public static final String CHILD_NOT_OF_EXPECTED_TYPE_ID = "virtual.faces.CHILD_NOT_OF_EXPECTED_TYPE";
	public static final String COMMAND_LINK_NO_FORM_MESSAGE_ID = "virtual.faces.COMMAND_LINK_NO_FORM_MESSAGE";
	public static final String COMPONENT_NOT_FOUND_ERROR_MESSAGE_ID = "virtual.faces.COMPONENT_NOT_FOUND_ERROR";
	public static final String COMPONENT_NOT_FOUND_IN_VIEW_WARNING_ID = "virtual.faces.COMPONENT_NOT_FOUND_IN_VIEW_WARNING";
	public static final String CONTENT_TYPE_ERROR_MESSAGE_ID = "virtual.faces.CONTENT_TYPE_ERROR";
	public static final String CONVERSION_ERROR_MESSAGE_ID = "virtual.faces.TYPECONVERSION_ERROR";
	public static final String CYCLIC_REFERENCE_ERROR_ID = "virtual.faces.CYCLIC_REFERENCE_ERROR";
	public static final String DUPLICATE_COMPONENT_ID_ERROR_ID = "virtual.faces.DUPLICATE_COMPONENT_ID_ERROR";
	public static final String EL_OUT_OF_BOUNDS_ERROR_ID = "virtual.faces.OUT_OF_BOUNDS_ERROR";
	public static final String EL_PROPERTY_TYPE_ERROR_ID = "virtual.faces.PROPERTY_TYPE_ERROR";
	public static final String EL_SIZE_OUT_OF_BOUNDS_ERROR_ID = "virtual.faces.SIZE_OUT_OF_BOUNDS_ERROR";
	public static final String EMPTY_PARAMETER_ID = "virtual.faces.EMPTY_PARAMETER";
	public static final String ENCODING_ERROR_MESSAGE_ID = "virtual.faces.ENCODING_ERROR";
	public static final String ERROR_GETTING_VALUEREF_VALUE_ERROR_MESSAGE_ID = "virtual.faces.ERROR_GETTING_VALUEREF_VALUE";
	public static final String ERROR_GETTING_VALUE_BINDING_ERROR_MESSAGE_ID = "virtual.faces.ERROR_GETTING_VALUE_BINDING";
	public static final String ERROR_OPENING_FILE_ERROR_MESSAGE_ID = "virtual.faces.ERROR_OPENING_FILE";
	public static final String ERROR_PROCESSING_CONFIG_ID = "virtual.faces.ERROR_PROCESSING_CONFIG";
	public static final String ERROR_REGISTERING_DTD_ERROR_MESSAGE_ID = "virtual.faces.ERROR_REGISTERING_DTD";
	public static final String ERROR_SETTING_BEAN_PROPERTY_ERROR_MESSAGE_ID = "virtual.faces.ERROR_SETTING_BEAN_PROPERTY";
	public static final String EVAL_ATTR_UNEXPECTED_TYPE = "virtual.faces.EVAL_ATTR_UNEXPECTED_TYPE";
	public static final String FACES_CONTEXT_CONSTRUCTION_ERROR_MESSAGE_ID = "virtual.faces.FACES_CONTEXT_CONSTRUCTION_ERROR";
	public static final String FACES_SERVLET_MAPPING_CANNOT_BE_DETERMINED_ID = "virtual.faces.FACES_SERVLET_MAPPING_CANNOT_BE_DETERMINED";
	public static final String FACES_SERVLET_MAPPING_INCORRECT_ID = "virtual.faces.FACES_SERVLET_MAPPING_INCORRECT";
	public static final String FACES_CONTEXT_NOT_FOUND_ID = "virtual.faces.FACES_CONTEXT_NOT_FOUND";
	public static final String FILE_NOT_FOUND_ERROR_MESSAGE_ID = "virtual.faces.FILE_NOT_FOUND";
	public static final String ILLEGAL_ATTEMPT_SETTING_STATEMANAGER_ID = "virtual.faces.ILLEGAL_ATTEMPT_SETTING_STATEMANAGER";
	public static final String ILLEGAL_ATTEMPT_SETTING_VIEWHANDLER_ID = "virtual.faces.ILLEGAL_ATTEMPT_SETTING_VIEWHANDLER";
	public static final String ILLEGAL_CHARACTERS_ERROR_MESSAGE_ID = "virtual.faces.ILLEGAL_CHARACTERS_ERROR";
	public static final String ILLEGAL_IDENTIFIER_LVALUE_MODE_ID = "virtual.faces.ILLEGAL_IDENTIFIER_LVALUE_MODE";
	public static final String ILLEGAL_MODEL_REFERENCE_ID = "virtual.faces.ILLEGAL_MODEL_REFERENCE";
	public static final String ILLEGAL_VIEW_ID_ID = "virtual.faces.ILLEGAL_VIEW_ID";
	public static final String INCORRECT_JSP_VERSION_ID = "virtual.faces.INCORRECT_JSP_VERSION";
	public static final String INVALID_EXPRESSION_ID = "virtual.faces.INVALID_EXPRESSION";
	public static final String INVALID_INIT_PARAM_ERROR_MESSAGE_ID = "virtual.faces.INVALID_INIT_PARAM";
	public static final String INVALID_MESSAGE_SEVERITY_IN_CONFIG_ID = "virtual.faces.INVALID_MESSAGE_SEVERITY_IN_CONFIG";
	public static final String INVALID_SCOPE_LIFESPAN_ERROR_MESSAGE_ID = "virtual.faces.INVALID_SCOPE_LIFESPAN";
	public static final String LIFECYCLE_ID_ALREADY_ADDED_ID = "virtual.faces.LIFECYCLE_ID_ALREADY_ADDED";
	public static final String LIFECYCLE_ID_NOT_FOUND_ERROR_MESSAGE_ID = "virtual.faces.LIFECYCLE_ID_NOT_FOUND";
	public static final String MANAGED_BEAN_CANNOT_SET_LIST_ARRAY_PROPERTY_ID = "virtual.faces.MANAGED_BEAN_CANNOT_SET_LIST_ARRAY_PROPERTY";
	public static final String MANAGED_BEAN_EXISTING_VALUE_NOT_LIST_ID = "virtual.faces.MANAGED_BEAN_EXISTING_VALUE_NOT_LIST";
	public static final String MANAGED_BEAN_TYPE_CONVERSION_ERROR_ID = "virtual.faces.MANAGED_BEAN_TYPE_CONVERSION_ERROR";
	public static final String MANAGED_BEAN_CLASS_NOT_FOUND_ERROR_ID = "virtual.faces.MANAGED_BEAN_CLASS_NOT_FOUND_ERROR";
	public static final String MANAGED_BEAN_CLASS_DEPENDENCY_NOT_FOUND_ERROR_ID = "virtual.faces.MANAGED_BEAN_CLASS_DEPENDENCY_NOT_FOUND_ERROR";
	public static final String MANAGED_BEAN_CLASS_IS_NOT_PUBLIC_ERROR_ID = "virtual.faces.MANAGED_BEAN_CLASS_IS_NOT_PUBLIC_ERROR";
	public static final String MANAGED_BEAN_CLASS_IS_ABSTRACT_ERROR_ID = "virtual.faces.MANAGED_BEAN_CLASS_IS_ABSTRACT_ERROR";
	public static final String MANAGED_BEAN_CLASS_NO_PUBLIC_NOARG_CTOR_ERROR_ID = "virtual.faces.MANAGED_BEAN_CLASS_NO_PUBLIC_NOARG_CTOR_ERROR";
	public static final String MANAGED_BEAN_INJECTION_ERROR_ID = "virtual.faces.MANAGED_BEAN_INJECTION_ERROR";
	public static final String MANAGED_BEAN_LIST_PROPERTY_CONFIG_ERROR_ID = "virtual.faces.MANAGED_BEAN_LIST_PROPERTY_CONFIG_ERROR";
	public static final String MANAGED_BEAN_AS_LIST_CONFIG_ERROR_ID = "virtual.faces.MANAGED_BEAN_AS_LIST_CONFIG_ERROR";
	public static final String MANAGED_BEAN_AS_MAP_CONFIG_ERROR_ID = "virtual.faces.MANAGED_BEAN_AS_MAP_CONFIG_ERROR";
	public static final String MANAGED_BEAN_MAP_PROPERTY_CONFIG_ERROR_ID = "virtual.faces.MANAGED_BEAN_MAP_PROPERTY_CONFIG_ERROR";
	public static final String MANAGED_BEAN_MAP_PROPERTY_INCORRECT_SETTER_ERROR_ID = "virtual.faces.MANAGED_BEAN_MAP_PROPERTY_INCORRECT_SETTER_ERROR";
	public static final String MANAGED_BEAN_MAP_PROPERTY_INCORRECT_GETTER_ERROR_ID = "virtual.faces.MANAGED_BEAN_MAP_PROPERTY_INCORRECT_GETTER_ERROR";
	public static final String MANAGED_BEAN_DEFINED_PROPERTY_CLASS_NOT_COMPATIBLE_ERROR_ID = "virtual.faces.MANAGED_BEAN_DEFINED_PROPERTY_CLASS_NOT_COMPATIBLE_ERROR";
	public static final String MANAGED_BEAN_INTROSPECTION_ERROR_ID = "virtual.faces.MANAGED_BEAN_INTROSPECTION_ERROR";
	public static final String MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR_ID = "virtual.faces.MANAGED_BEAN_PROPERTY_DOES_NOT_EXIST_ERROR";
	public static final String MANAGED_BEAN_PROPERTY_HAS_NO_SETTER_ID = "virtual.faces.MANAGED_BEAN_PROPERTY_HAS_NO_SETTER_ERROR";
	public static final String MANAGED_BEAN_PROPERTY_INCORRECT_ARGS_ERROR_ID = "virtual.faces.MANAGED_BEAN_PROPERTY_INCORRECT_ARGS_ERROR";
	public static final String MANAGED_BEAN_LIST_SETTER_DOES_NOT_ACCEPT_LIST_OR_ARRAY_ERROR_ID = "virtual.faces.MANAGED_BEAN_LIST_SETTER_DOES_NOT_ACCEPT_LIST_OR_ARRAY_ERROR";
	public static final String MANAGED_BEAN_LIST_GETTER_DOES_NOT_RETURN_LIST_OR_ARRAY_ERROR_ID = "virtual.faces.MANAGED_BEAN_LIST_SETTER_DOES_NOT_RETURN_LIST_OR_ARRAY_ERROR";
	public static final String MANAGED_BEAN_LIST_GETTER_ARRAY_NO_SETTER_ERROR_ID = "virtual.faces.MANAGED_BEAN_LIST_GETTER_ARRAY_NO_SETTER_ERROR";
	public static final String MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR_ID = "virtual.faces.MANAGED_BEAN_UNABLE_TO_SET_PROPERTY_ERROR";
	public static final String MANAGED_BEAN_PROBLEMS_ERROR_ID = "virtual.faces.MANAGED_BEAN_PROBLEMS_ERROR";
	public static final String MANAGED_BEAN_PROBLEMS_STARTUP_ERROR_ID = "virtual.faces.MANAGED_BEAN_PROBLEMS_STARTUP_ERROR";
	public static final String MANAGED_BEAN_UNKNOWN_PROCESSING_ERROR_ID = "virtual.faces.MANAGED_BEAN_UNKNOWN_PROCESSING_ERROR";
	public static final String MANAGED_BEAN_PROPERTY_UNKNOWN_PROCESSING_ERROR_ID = "virtual.faces.MANAGED_BEAN_PROPERTY_UNKNOWN_PROCESSING_ERROR";
	public static final String MAXIMUM_EVENTS_REACHED_ERROR_MESSAGE_ID = "virtual.faces.MAXIMUM_EVENTS_REACHED";
	public static final String MISSING_CLASS_ERROR_MESSAGE_ID = "virtual.faces.MISSING_CLASS_ERROR";
	public static final String MISSING_RESOURCE_ERROR_MESSAGE_ID = "virtual.faces.MISSING_RESOURCE_ERROR";
	public static final String MODEL_UPDATE_ERROR_MESSAGE_ID = "virtual.faces.MODELUPDATE_ERROR";
	public static final String NAMED_OBJECT_NOT_FOUND_ERROR_MESSAGE_ID = "virtual.faces.NAMED_OBJECT_NOT_FOUND_ERROR";
	public static final String NOT_NESTED_IN_FACES_TAG_ERROR_MESSAGE_ID = "virtual.faces.NOT_NESTED_IN_FACES_TAG_ERROR";
	public static final String NOT_NESTED_IN_TYPE_TAG_ERROR_MESSAGE_ID = "virtual.faces.NOT_NESTED_IN_TYPE_TAG_ERROR";
	public static final String NOT_NESTED_IN_UICOMPONENT_TAG_ERROR_MESSAGE_ID = "virtual.faces.NOT_NESTED_IN_UICOMPONENT_TAG_ERROR";
	public static final String NO_DTD_FOUND_ERROR_ID = "virtual.faces.NO_DTD_FOUND_ERROR";
	public static final String NO_COMPONENT_ASSOCIATED_WITH_UICOMPONENT_TAG_MESSAGE_ID = "virtual.faces.NO_COMPONENT_ASSOCIATED_WITH_UICOMPONENT_TAG";
	public static final String NULL_BODY_CONTENT_ERROR_MESSAGE_ID = "virtual.faces.NULL_BODY_CONTENT_ERROR";
	public static final String NULL_COMPONENT_ERROR_MESSAGE_ID = "virtual.faces.NULL_COMPONENT_ERROR";
	public static final String NULL_CONFIGURATION_ERROR_MESSAGE_ID = "virtual.faces.NULL_CONFIGURATION";
	public static final String NULL_CONTEXT_ERROR_MESSAGE_ID = "virtual.faces.NULL_CONTEXT_ERROR";
	public static final String NULL_EVENT_ERROR_MESSAGE_ID = "virtual.faces.NULL_EVENT_ERROR";
	public static final String NULL_FORVALUE_ID = "virtual.faces.NULL_FORVALUE";
	public static final String NULL_HANDLER_ERROR_MESSAGE_ID = "virtual.faces.NULL_HANDLER_ERROR";
	public static final String NULL_LOCALE_ERROR_MESSAGE_ID = "virtual.faces.NULL_LOCALE_ERROR";
	public static final String NULL_MESSAGE_ERROR_MESSAGE_ID = "virtual.faces.NULL_MESSAGE_ERROR";
	public static final String NULL_PARAMETERS_ERROR_MESSAGE_ID = "virtual.faces.NULL_PARAMETERS_ERROR";
	public static final String NULL_REQUEST_VIEW_ERROR_MESSAGE_ID = "virtual.faces.NULL_REQUEST_VIEW_ERROR";
	public static final String NULL_RESPONSE_STREAM_ERROR_MESSAGE_ID = "virtual.faces.NULL_RESPONSE_STREAM_ERROR";
	public static final String NULL_RESPONSE_VIEW_ERROR_MESSAGE_ID = "virtual.faces.NULL_RESPONSE_VIEW_ERROR";
	public static final String NULL_RESPONSE_WRITER_ERROR_MESSAGE_ID = "virtual.faces.NULL_RESPONSE_WRITER_ERROR";
	public static final String OBJECT_CREATION_ERROR_ID = "virtual.faces.OBJECT_CREATION_ERROR";
	public static final String OBJECT_IS_READONLY = "virtual.faces.OBJECT_IS_READONLY";
	public static final String PHASE_ID_OUT_OF_BOUNDS_ERROR_MESSAGE_ID = "virtual.faces.PHASE_ID_OUT_OF_BOUNDS";
	public static final String RENDERER_NOT_FOUND_ERROR_MESSAGE_ID = "virtual.faces.RENDERER_NOT_FOUND";
	public static final String REQUEST_VIEW_ALREADY_SET_ERROR_MESSAGE_ID = "virtual.faces.REQUEST_VIEW_ALREADY_SET_ERROR";
	public static final String RESTORE_VIEW_ERROR_MESSAGE_ID = "virtual.faces.RESTORE_VIEW_ERROR";
	public static final String SAVING_STATE_ERROR_MESSAGE_ID = "virtual.faces.SAVING_STATE_ERROR";
	public static final String SUPPORTS_COMPONENT_ERROR_MESSAGE_ID = "virtual.faces.SUPPORTS_COMPONENT_ERROR";
	public static final String VALIDATION_COMMAND_ERROR_ID = "virtual.faces.VALIDATION_COMMAND_ERROR";
	public static final String VALIDATION_EL_ERROR_ID = "virtual.faces.VALIDATION_EL_ERROR";
	public static final String VALIDATION_ID_ERROR_ID = "virtual.faces.VALIDATION_ID_ERROR";
	public static final String VALUE_NOT_SELECT_ITEM_ID = "virtual.faces.OPTION_NOT_SELECT_ITEM";
	public static final String CANNOT_CONVERT_ID = "virtual.faces.CANNOT_CONVERT";
	public static final String CANNOT_VALIDATE_ID = "virtual.faces.CANNOT_VALIDATE";
	public static final String VERIFIER_CLASS_NOT_FOUND_ID = "virtual.faces.verifier.CLASS_NOT_FOUND";
	public static final String VERIFIER_CLASS_MISSING_DEP_ID = "virtual.faces.verifier.CLASS_MISSING_DEP";
	public static final String VERIFIER_CTOR_NOT_PUBLIC_ID = "virtual.faces.verifier.NON_PUBLIC_DEF_CTOR";
	public static final String VERIFIER_NO_DEF_CTOR_ID = "virtual.faces.verifier.NO_DEF_CTOR";
	public static final String VERIFIER_WRONG_TYPE_ID = "virtual.faces.verifier.WRONG_TYPE";
	public static final String RENDERER_CANNOT_BE_REGISTERED_ID = "virtual.faces.CONFIG_RENDERER_REGISTRATION_MISSING_RENDERKIT";
	public static final String JS_RESOURCE_WRITING_ERROR_ID = "virtual.faces.JS_RESOURCE_WRITING_ERROR";

	/**
	 * @param _serverity
	 * @param _resum
	 * @param _detail
	 * @return
	 */
	public static FacesMessage getMessage(Severity _serverity, String _resum, String _detail) {
		return new FacesMessage(_serverity, _resum, _detail);
	}

	/**
	 * Retorna a mensagem conforme chave armazenada em um MessageBundle
	 * 
	 * @param _serverity
	 * @param _bundle
	 * @param _resum
	 * @return
	 */
	static public FacesMessage getMessageBundle(Severity _serverity, String _bundle, String _resum) {
		String summary = FacesUtils.getString(_bundle, _resum);

		String detail = FacesUtils.getString(_bundle, _resum + ".Detail");

		return new FacesMessage(_serverity, summary, detail);
	}

	private FacesMessageUtils() {

	}

	/**
	 * 
	 * 
	 * @param p_id -
	 *            Label or component ID
	 * @param p_severity -
	 *            {@link FacesMessage}: {@link FacesMessage#SEVERITY_ERROR},
	 *            {@link FacesMessage#SEVERITY_ERROR},
	 *            {@link FacesMessage#SEVERITY_FATAL},
	 *            {@link FacesMessage#SEVERITY_INFO},
	 *            {@link FacesMessage#SEVERITY_WARN},
	 * @param p_shortMsg
	 * @param p_string3
	 * @return
	 */
	public static FacesMessage addMessage(String p_id, Severity p_severity, String p_shortMsg, String p_string3) {
		FacesMessage l_msg = FacesMessageUtils.getMessage(p_severity, p_shortMsg, p_string3);
		FacesUtils.addMessage(p_id, l_msg);
		return l_msg;
	}

	/**
	 * @see #getMessage(String, Object...)
	 * @param FacesMessage.Serverity
	 *            set a custom severity
	 */
	static FacesMessage getMessage(String messageId, FacesMessage.Severity severity, Object... params) {
		FacesMessage message = getMessage(messageId, params);
		message.setSeverity(severity);
		return message;
	}

	/**
	 * @see #getMessage(Locale, String, Object...)
	 * @param FacesMessage.Serverity
	 *            set a custom severity
	 */
	public static FacesMessage getMessage(Locale locale, String messageId, FacesMessage.Severity severity,
			Object... params) {
		FacesMessage message = getMessage(locale, messageId, params);
		message.setSeverity(severity);
		return message;
	}

	/**
	 * @see #getMessage(FacesContext, String, Object...)
	 * @param FacesMessage.Serverity
	 *            set a custom severity
	 */
	public static FacesMessage getMessage(FacesContext context, String messageId, FacesMessage.Severity severity,
			Object... params) {
		FacesMessage message = getMessage(context, messageId, params);
		message.setSeverity(severity);
		return message;
	}

	/**
	 * <p>
	 * This version of getMessage() is used for localizing implementation
	 * specific messages.
	 * </p>
	 * 
	 * @param messageId -
	 *            the key of the message in the resource bundle
	 * @param params -
	 *            substittion parameters
	 * 
	 * @return a localized <code>FacesMessage</code> with the severity of
	 *         FacesMessage.SEVERITY_ERROR
	 */
	static FacesMessage getMessage(String messageId, Object... params) {
		Locale locale = null;
		FacesContext context = FacesContext.getCurrentInstance();
		// context.getViewRoot() may not have been initialized at this point.
		if (context != null && context.getViewRoot() != null) {
			locale = context.getViewRoot().getLocale();
			if (locale == null) {
				locale = Locale.getDefault();
			}
		} else {
			locale = Locale.getDefault();
		}

		return getMessage(locale, messageId, params);
	}

	/**
	 * <p>
	 * Creates and returns a FacesMessage for the specified Locale.
	 * </p>
	 * 
	 * @param locale -
	 *            the target <code>Locale</code>
	 * @param messageId -
	 *            the key of the message in the resource bundle
	 * @param params -
	 *            substittion parameters
	 * 
	 * @return a localized <code>FacesMessage</code> with the severity of
	 *         FacesMessage.SEVERITY_ERROR
	 */
	public static FacesMessage getMessage(Locale locale, String messageId, Object... params) {
		String summary = null;
		String detail = null;
		ResourceBundle bundle;
		String bundleName;

		// see if we have a user-provided bundle
		if (null != (bundleName = getApplication().getMessageBundle())) {
			if (null != (bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentLoader(bundleName)))) {
				// see if we have a hit
				try {
					summary = bundle.getString(messageId);
					detail = bundle.getString(messageId + "_detail");
				} catch (MissingResourceException e) {
					// ignore
				}
			}
		}

		// we couldn't find a summary in the user-provided bundle
		if (null == summary) {
			// see if we have a summary in the app provided bundle
			bundle = ResourceBundle.getBundle(FacesMessage.FACES_MESSAGES, locale, getCurrentLoader(bundleName));
			if (null == bundle) {
				throw new NullPointerException();
			}
			// see if we have a hit
			try {
				summary = bundle.getString(messageId);
				// we couldn't find a summary anywhere! Return null
				if (null == summary) {
					return null;
				}
				detail = bundle.getString(messageId + "_detail");
			} catch (MissingResourceException e) {
				// ignore
			}
		}
		// At this point, we have a summary and a bundle.
		FacesMessage ret = new BindingFacesMessage(locale, summary, detail, params);
		ret.setSeverity(FacesMessage.SEVERITY_ERROR);
		return (ret);
	}

	/**
	 * <p>
	 * Creates and returns a FacesMessage for the specified Locale.
	 * </p>
	 * 
	 * @param context -
	 *            the <code>FacesContext</code> for the current request
	 * @param messageId -
	 *            the key of the message in the resource bundle
	 * @param params -
	 *            substittion parameters
	 * 
	 * @return a localized <code>FacesMessage</code> with the severity of
	 *         FacesMessage.SEVERITY_ERROR
	 */
	public static FacesMessage getMessage(FacesContext context, String messageId, Object... params) {

		if (context == null || messageId == null) {
			throw new NullPointerException(" context " + context + " messageId " + messageId);
		}
		Locale locale;
		// viewRoot may not have been initialized at this point.
		if (context.getViewRoot() != null) {
			locale = context.getViewRoot().getLocale();
		} else {
			locale = Locale.getDefault();
		}

		if (null == locale) {
			throw new NullPointerException(" locale is null ");
		}

		FacesMessage message = getMessage(locale, messageId, params);
		if (message != null) {
			return message;
		}
		locale = Locale.getDefault();
		return (getMessage(locale, messageId, params));
	}

	/**
	 * <p>
	 * Returns the <code>label</code> property from the specified component.
	 * </p>
	 * 
	 * @param context -
	 *            the <code>FacesContext</code> for the current request
	 * @param component -
	 *            the component of interest
	 * 
	 * @return the label, if any, of the component
	 */
	public static Object getLabel(FacesContext context, UIComponent component) {

		Object o = component.getAttributes().get("label");
		if (o == null || (o instanceof String && ((String) o).length() == 0)) {
			o = component.getValueExpression("label");
		}
		// Use the "clientId" if there was no label specified.
		if (o == null) {
			o = component.getClientId(context);
		}
		return o;
	}

	protected static Application getApplication() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			return (FacesContext.getCurrentInstance().getApplication());
		}
		ApplicationFactory afactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		return (afactory.getApplication());
	}

	protected static ClassLoader getCurrentLoader(Object fallbackClass) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = fallbackClass.getClass().getClassLoader();
		}
		return loader;
	}

	/**
	 * This class overrides FacesMessage to provide the evaluation of binding
	 * expressions in addition to Strings. It is often the case, that a binding
	 * expression may reference a localized property value that would be used as
	 * a substitution parameter in the message. For example:
	 * <code>#{bundle.userLabel}</code> "bundle" may not be available until
	 * the page is rendered. The "late" binding evaluation in
	 * <code>getSummary</code> and <code>getDetail</code> allow the
	 * expression to be evaluated when that property is available.
	 */
	static class BindingFacesMessage extends FacesMessage {
		BindingFacesMessage(Locale locale, String messageFormat, String detailMessageFormat,
		// array of parameters, both Strings and ValueBindings
				Object[] parameters) {

			super(messageFormat, detailMessageFormat);
			this.locale = locale;
			this.parameters = parameters;
			if (parameters != null) {
				resolvedParameters = new Object[parameters.length];
			}
		}

		public String getSummary() {
			String pattern = super.getSummary();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		public String getDetail() {
			String pattern = super.getDetail();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		private void resolveBindings() {
			FacesContext context = null;
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					Object o = parameters[i];
					if (o instanceof ValueBinding) {
						if (context == null) {
							context = FacesContext.getCurrentInstance();
						}
						o = ((ValueBinding) o).getValue(context);
					}
					if (o instanceof ValueExpression) {
						if (context == null) {
							context = FacesContext.getCurrentInstance();
						}
						o = ((ValueExpression) o).getValue(context.getELContext());
					}
					// to avoid 'null' appearing in message
					if (o == null) {
						o = "";
					}
					resolvedParameters[i] = o;
				}
			}
		}

		private String getFormattedString(String msgtext, Object[] params) {
			String localizedStr = null;

			if (params == null || msgtext == null) {
				return msgtext;
			}
			StringBuffer b = new StringBuffer(100);
			MessageFormat mf = new MessageFormat(msgtext);
			if (locale != null) {
				mf.setLocale(locale);
				b.append(mf.format(params));
				localizedStr = b.toString();
			}
			return localizedStr;
		}

		private Locale locale;
		private Object[] parameters;
		private Object[] resolvedParameters;
	}

	/**
	 * @param p_nullParametersErrorMessageId
	 * @param p_string
	 * @return
	 */
	/**
     * <p>Returns the localized message for the specified 
     * #messageId.</p>
     * 
     * @param messageId the message ID
     * @param params an array of substitution parameters
     * @return the localized message for the specified 
     *  <code>messageId</code>
     */
    public static String getExceptionMessageString(
          String messageId,
          Object... params) {

        String result = null;

        FacesMessage message = MessageFactory.getMessage(messageId, params);
        if (null != message) {
            result = message.getSummary();
        }


        if (null == result) {
            result = "null MessageFactory";
        } else {
            if (params != null) {
                result = MessageFormat.format(result, params);
            }
        }
        return result;

    }

}
