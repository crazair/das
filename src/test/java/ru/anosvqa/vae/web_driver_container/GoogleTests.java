package ru.anosvqa.vae.web_driver_container;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode;

public class GoogleTests {

    private static BrowserWebDriverContainer chrome;
    private static File path = new File("./target/container_record/");

    static {
        chrome = new BrowserWebDriverContainer()
                .withCapabilities(DesiredCapabilities.chrome())
                .withRecordingMode(VncRecordingMode.RECORD_ALL, path);

        chrome.start();

        WebDriverRunner.setWebDriver(chrome.getWebDriver());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            chrome.stop();
            System.out.println("@@@ " + path.getAbsolutePath());
        }));
    }

    @Test
    void test() {
        open("http://google.com");
        sleep(5000);
    }

}
