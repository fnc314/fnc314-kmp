package com.fnc314.kmp.tools.gradleplugins

/**
 * Modifies the target [KmpPlugin] so that it can provide the necessary setup for an arbitrary
 *   "app" produced by the repository
 */
internal class KmpAppComposePlugin : KmpPlugin(kmpPluginTarget = KmpPluginTarget.APP_COMPOSE)