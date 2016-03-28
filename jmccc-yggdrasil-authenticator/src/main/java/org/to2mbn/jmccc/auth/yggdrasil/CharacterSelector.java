package org.to2mbn.jmccc.auth.yggdrasil;

import org.to2mbn.jmccc.auth.AuthenticationException;
import org.to2mbn.jmccc.auth.yggdrasil.core.GameProfile;

public interface CharacterSelector {

	/**
	 * Selects one of the given characters to login.
	 * <p>
	 * This method may be called during the authentication if no character is
	 * selected. An {@link AuthenticationException} will be thrown if the method
	 * returns {@code null}.
	 * <p>
	 * 假如在登录期间发现还没有选择一个角色，就会调用这个方法来选择角色。假如这个方法返回 {@code null} ，则会抛出一个
	 * {@link AuthenticationException} 。（注：一个Yggdrasil账号可以拥有多个游戏角色）
	 * 
	 * @param availableProfiles the available characters
	 * @return the character to login
	 */
	GameProfile select(GameProfile[] availableProfiles);

}
