package quiltcore_test

import java.nio.file.Path

import com.quiltdata.quiltcore.Registry
import com.quiltdata.quiltcore.Namespace
import com.quiltdata.quiltcore.Manifest
import com.quiltdata.quiltcore.key.S3PhysicalKey


class App {
    void install(String bucket, String name, Path dest) {
        S3PhysicalKey registryPath = new S3PhysicalKey(bucket, "", null)
        Registry registry = new Registry(registryPath)
        Namespace namespace = registry.getNamespace(name)
        String hash = namespace.getHash('latest')
        Manifest manifest = namespace.getManifest(hash)

        manifest.install(dest)
    }

    static void main(String[] args) {
        if (args.length != 3) {
            println "Usage: bucket package dest"
            System.exit(1)
        }
        new App().install(args[0], args[1], Path.of(args[2]))
        println "done"
    }
}
