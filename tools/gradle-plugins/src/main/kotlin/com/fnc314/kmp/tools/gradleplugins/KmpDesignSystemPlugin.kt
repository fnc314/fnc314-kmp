package com.fnc314.kmp.tools.gradleplugins

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "design-system" of the application
*/
internal abstract class KmpDesignSystemPlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.DESIGN_SYSTEM)