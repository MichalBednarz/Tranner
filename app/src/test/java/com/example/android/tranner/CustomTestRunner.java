package com.example.android.tranner;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.manifest.AndroidManifest;
import org.robolectric.res.FileFsFile;

/**
 * Created by Michał on 2017-04-22.
 */

/**
 * Class created to substitute original RobolectricTestRunner.
 * Usage of original RobolectricTestRunner was reason of issues concerning AndroidManifest.
 */
public class CustomTestRunner extends RobolectricTestRunner {
    public CustomTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected AndroidManifest getAppManifest(Config config) {
        final String BUILD_PATH = "app/build/intermediates";
        final String flavor = BuildConfig.FLAVOR;
        final String type = BuildConfig.BUILD_TYPE;

        final FileFsFile assetsFile = FileFsFile.from(BUILD_PATH, config.assetDir(), flavor, type);
        final FileFsFile resFile = FileFsFile.from(BUILD_PATH, config.resourceDir(), "merged", flavor, type);
        final FileFsFile manifestFile = FileFsFile.from(
                BUILD_PATH, "manifests", "full", flavor, type, "AndroidManifest.xml"
        );

        return new AndroidManifest(manifestFile, resFile, assetsFile);
    }
}
