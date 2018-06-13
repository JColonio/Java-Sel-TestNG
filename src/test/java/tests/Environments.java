package tests;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:src\\test\\resources\\Environments\\${env}.properties" })
public interface Environments extends Config {
	String URL();

	String SearchField();
}
