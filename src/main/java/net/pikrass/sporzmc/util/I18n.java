package net.pikrass.sporzmc.util;

import java.util.Locale;

public final class I18n
{
	public static org.xnap.commons.i18n.I18n i =
		org.xnap.commons.i18n.I18nFactory.getI18n(I18n.class,
				new Locale("Fr", "fr"),
				org.xnap.commons.i18n.I18nFactory.FALLBACK);

	public static String _(String str) {
		return i.tr(str);
	}

	private I18n() {
	}
}
