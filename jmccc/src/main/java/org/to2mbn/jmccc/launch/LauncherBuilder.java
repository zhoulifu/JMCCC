package org.to2mbn.jmccc.launch;

/**
 * The builder for {@link Launcher}.
 * 
 * @author yushijinhun
 */
public class LauncherBuilder {

	/**
	 * Creates a new <code>LauncherBuilder</code> object.
	 * 
	 * @return a new <code>LauncherBuilder</code> object
	 */
	public static LauncherBuilder create() {
		return new LauncherBuilder();
	}

	private boolean nativeFastCheck = false;
	private boolean debugPrintCommandline = false;

	protected LauncherBuilder() {
	}

	/**
	 * Sets whether to do a fast check on natives.
	 * <p>
	 * By default, this feature is off. In this case, when decompressing natives,
	 * the jmccc will fully compare the existing natives and the natives in jars. If, and only if,
	 * a existing native is modified, jmccc will replace it. Because replacing a native in use
	 * may cause the running JVM to be crashed.<br>
	 * If the feature is on, the jmccc won't compare the full content of natives.
	 * Jmccc only compares the sizes. This can improve the launching speed.
	 * But we cannot ensure the content of the natives are correct.
	 * 
	 * @param nativeFastCheck true to let jmccc do a fast check on natives
	 * @return the builder itself
	 */
	public LauncherBuilder setNativeFastCheck(boolean nativeFastCheck) {
		this.nativeFastCheck = nativeFastCheck;
		return this;
	}

	/**
	 * Sets whether to print the launch commandline for debugging.
	 * <p>
	 * The commandline will be printed to stderr. The format is
	 * <blockquote>
	 * 
	 * <pre>
	 * jmccc: &lt;commandline&gt;
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param debugPrintCommandline whether to print the launch commandline for debugging.
	 * @return the builder itself
	 */
	public LauncherBuilder setDebugPrintCommandline(boolean debugPrintCommandline) {
		this.debugPrintCommandline = debugPrintCommandline;
		return this;
	}

	/**
	 * Creates a new Launcher object according to the configurations.
	 * 
	 * @return a Launcher object
	 */
	public Launcher build() {
		Jmccc launcher = new Jmccc();
		launcher.setNativeFastCheck(nativeFastCheck);
		launcher.setDebugPrintCommandline(debugPrintCommandline);
		return launcher;
	}

}