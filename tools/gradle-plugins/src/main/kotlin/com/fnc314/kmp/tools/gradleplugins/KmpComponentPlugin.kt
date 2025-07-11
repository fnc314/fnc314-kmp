package com.fnc314.kmp.tools.gradleplugins

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "components" of the application
 */
internal abstract class KmpComponentPlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.COMPONENT)