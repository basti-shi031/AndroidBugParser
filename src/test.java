public class test {
    public static void main(String[] args)
    {
        String a = "    \"subject\": \"Make hook that report dex files load deeply Now many apps such as wechat(com.tencent.mm) use hot patch case such as tinker(https://github.com/Tencent/tinker) They load their secondary dex not via BaseDexClassLoader, but they finally invoke DexFile#openDexFile to load the secondary dex. Test: Install tinker demo apk, open it ten times, and then execute the command adb shell cmd package bg-dexopt-job test result: 1. Load patch: dex2oat-cmdline \\u003d --runtime-arg -classpath --runtime-arg \\u0026 --debuggable --instruction-set\\u003darm64 --instruction-set-features\\u003da53 --runtime-arg -Xrelocate --boot-image\\u003d/system/framework/boot.art --runtime-arg -Xms64m --runtime-arg -Xmx512m -j4 --instruction-set-variant\\u003dgeneric --instruction-set-features\\u003ddefault --debuggable --dex-file\\u003d/data/data/tinker.sample.android/tinker/patch-d10cb876/dex/classes.dex.jar --output-vdex-fd\\u003d56 --oat-fd\\u003d57 --oat-location\\u003d/data/data/tinker.sample.android/tinker/patch-d10cb876/dex/oat/arm64/classes.dex.odex --compiler-filter\\u003dquicken 2. After background dexopt job: dex2oat-cmdline \\u003d tinker.sample.android --zip-fd\\u003d28 --zip-location\\u003dclasses.dex.jar --input-vdex-fd\\u003d30 --output-vdex-fd\\u003d31 --oat-fd\\u003d29 --oat-location\\u003d/data/data/tinker.sample.android/tinker/patch-d10cb876/dex/oat/arm64/classes.dex.odex --instruction-set\\u003darm64 --instruction-set-variant\\u003dgeneric --instruction-set-features\\u003ddefault --runtime-arg -Xms64m --runtime-arg -Xmx512m --compiler-filter\\u003dspeed-profile -j4 --swap-fd\\u003d32 --debuggable --profile-file-fd\\u003d33 --runtime-arg -classpath --runtime-arg \\u0026 --classpath-dir\\u003d/data/data/tinker.sample.android/tinker/patch-d10cb876/dex\",\n";
        System.out.println(a.length());
    }
}
