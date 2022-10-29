package dev.lumix.astatine;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.MathUtils;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		String[] splashTexts = new String[]{
			"the third one",
			"by lumix",
			"one of a kind",
			"also try minecraft",
			"also try terraria",
			"radioactive",
			"now in java",
			"pre-pre-pre alpha",
			"open source",
			":skull:",
			"advanced technology",
			"never seen before",
			"life changing",
			"masterpiece",
			"mfbot would be proud",
			"borrows code",
			"has *some* bugs",
			"report any bugs",
			"unbelievable",
			"uses CHUNKS",
			"better code than terraria",
			"worse code than minecraft",
			"same language as minecraft",
			"without log4j",
			"optimized",
			"really REALLY cool",
			"\"a simple side-project\"",
			"over 0 copies sold!",
			"made in greece",
			"managing chunks",
			"generating world",
			"never gave up!",
			"someday with music",
			"someday with original textures",
			"deleting system32",
			"null",
			"undefined",
			"NullPointerException",
			"ArrayIndexOutOfBoundsException",
			"Expected ;",
			"Expected )",
			"Expected }",
			"Expected something",
			"85",
			"uses CHEMISTRY",
			"Mr. #FFFFFF",
			"gutman",
			"sleep is for the weak",
			"InvalidSplashTextException",
			"SkillIssueException",
			"needs commenting",
			"needs more splash texts"
		};
		String randomSplashText = splashTexts[MathUtils.random(splashTexts.length - 1)];

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setForegroundFPS(60);
		config.setTitle("astatine v0.0.0 - " + randomSplashText);
		config.setWindowIcon("icon.png");

		new Lwjgl3Application(new Astatine(), config);
	}
}
