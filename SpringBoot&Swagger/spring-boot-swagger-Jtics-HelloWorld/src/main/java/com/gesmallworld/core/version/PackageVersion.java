//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gesmallworld.core.version;

import java.io.PrintStream;
import java.util.Vector;

public class PackageVersion {
    public String package_name;
    public int major_version;
    public int minor_version;
    public int bugfix_version;
    public String description;
    private static Vector version_list = new Vector(5);

    public static void declareVersion(String package_name, int major, int minor, int bugfix, String description) {
        version_list.add(new PackageVersion(package_name, major, minor, bugfix, description));
    }

    protected PackageVersion() {
    }

    private PackageVersion(String package_name, int major, int minor, int bugfix, String description) {
        this.package_name = package_name;
        this.major_version = major;
        this.minor_version = minor;
        this.bugfix_version = bugfix;
        this.description = description;
    }

    public String getVersionString() {
        return this.major_version + "." + this.minor_version + "." + this.bugfix_version;
    }

    public String getPrintString() {
        return this.package_name + "\t" + this.getVersionString() + "\t" + this.description;
    }

    public void printStringOn(PrintStream w) {
        w.println(this.getPrintString());
    }

    public void printString() {
        this.printStringOn(System.err);
    }

    public static void printVersions() {
        int s = version_list.size();

        for(int i = 0; i < s; ++i) {
            System.err.println(((PackageVersion)version_list.elementAt(i)).getPrintString());
        }

    }

    public static void printVersionsIfRequested(String[] args) {
        for(int i = 0; i < args.length; ++i) {
            if(args[i].equals("-v")) {
                printVersions();
                System.exit(0);
            }
        }

    }

    public static PackageVersion getVersionDescription(String package_name) {
        int s = version_list.size();

        for(int i = 0; i < s; ++i) {
            PackageVersion v = (PackageVersion)version_list.elementAt(i);
            if(v.package_name.equals(package_name)) {
                return v;
            }
        }

        return null;
    }

    public static void requiresVersion(String package_name, int major_version, int minor_version) throws Error {
        PackageVersion v = getVersionDescription(package_name);
        if(v == null) {
            throw new Error("Version information for package " + package_name + " not specified -maybe package not loaded");
        } else if(major_version >= v.major_version && (major_version != v.major_version || minor_version > v.minor_version)) {
            throw new Error("Package " + package_name + " has incompatible version\nRequired: " + major_version + "." + minor_version + "\nVersion loaded: " + v.major_version + "." + v.minor_version);
        }
    }
}
