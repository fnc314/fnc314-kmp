package com.fnc314.kmp.tools.gradleplugins

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "feature" component of the application
 */
internal class KmpFeaturePlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.FEATURE)