package net.pikrass.sporzmc.util;

import org.xnap.commons.i18n.I18nFactory;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.Enumeration;

public final class I18n
{
	public static org.xnap.commons.i18n.I18n i;

	public static void init(Locale locale) {
		try {
			i = I18nFactory.getI18n(I18n.class, "Messages",
					locale, I18nFactory.DEFAULT);
		} catch(MissingResourceException e) {
			i = new org.xnap.commons.i18n.I18n(
					new DefaultResourceBundle(Locale.ENGLISH)
					);
		}
	}

	public static String _(String str) {
		return i.tr(str);
	}

	public static String _(String comment, String str) {
		return i.trc(comment, str);
	}

	public static String _(String str, String plural, long n) {
		return i.trn(str, plural, n);
	}

	private I18n() {
	}

	private static class DefaultResourceBundle extends ResourceBundle
	{
		private Locale locale;

		public DefaultResourceBundle(Locale locale)
		{
			this.locale = locale;
		}

		protected Object handleGetObject(String key)
		{
			throw new MissingResourceException("This language isn't supported",
					"net.pikrass.sporzmc.util.I18n", key.toString());
		}

		public Enumeration<String> getKeys()
		{
			return new EmptyStringEnumeration();
		}

		public Locale getLocale()
		{
			return locale;
		}

		private static class EmptyStringEnumeration implements Enumeration<String>
		{

			public boolean hasMoreElements()
			{
				return false;
			}

			public String nextElement()
			{
				throw new IllegalStateException("nextElement must not be " +
						"called on empty enumeration");
			}

		}

	}
}
