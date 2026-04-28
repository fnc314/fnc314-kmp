package com.fnc314.kmp.tools.gradleconfigs

interface GradleConfigsExtension {
  val KotlinVersion: String

  val AndroidSdkCompile: Int
  val AndroidSdkTarget: Int
  val AndroidSdkMin: Int

  val AndroidBuildTools: String
}