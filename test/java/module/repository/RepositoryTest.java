/*
 * Copyright 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import java.io.*;
import java.module.*;
import java.net.URL;
import sun.module.repository.RepositoryConfig;

/**
 * @test RepositoryTest
 * @summary Test various Repository methods
 * @compile -XDignore.symbol.file RepositoryTest.java
 * @run main RepositoryTest
 */
public class RepositoryTest  {
    public static void realMain(String args[]) throws Throwable {
        File src = new File(".").getCanonicalFile();
        String name = "application";
        Repository rep = Modules.newLocalRepository(
            RepositoryConfig.getSystemRepository(), name, src);

        check(rep.getParent() == Repository.getSystemRepository());
        check(name.equals(rep.getName()));
        check(rep.getSourceLocation().equals(src.toURI().toURL()));
        check(rep.getModuleSystem() == ModuleSystem.getDefault());

        Repository bsRep = Repository.getBootstrapRepository();
        check(bsRep.getParent() == null);
        check(bsRep.isReadOnly());

        RepositoryConfig.setSystemRepository(rep);

        Repository sysRep = Repository.getSystemRepository();
        check(sysRep.getModuleSystem() == ModuleSystem.getDefault());

        rep.shutdown();

        // Verify null arg checking
        try {
            rep = Modules.newLocalRepository(null, name, src);
            fail();
        } catch (NullPointerException ex) {
            // expected
        } catch (Throwable ex) {
            unexpected(ex);
        }

        try {
            rep = Modules.newLocalRepository(null, src);
            fail();
        } catch (NullPointerException ex) {
            // expected
        } catch (Throwable ex) {
            unexpected(ex);
        }

        try {
            rep = Modules.newLocalRepository(name, null);
            fail();
        } catch (NullPointerException ex) {
            // expected
        } catch (Throwable ex) {
            unexpected(ex);
        }
    }


    //--------------------- Infrastructure ---------------------------
    static volatile int passed = 0, failed = 0;
    static boolean pass() {passed++; return true;}
    static boolean fail() {failed++; Thread.dumpStack(); return false;}
    static boolean fail(String msg) {System.out.println(msg); return fail();}
    static void unexpected(Throwable t) {failed++; t.printStackTrace();}
    static boolean check(boolean cond) {if (cond) pass(); else fail(); return cond;}
    static boolean equal(Object x, Object y) {
        if (x == null ? y == null : x.equals(y)) return pass();
        else return fail(x + " not equal to " + y);}
    public static void main(String[] args) throws Throwable {
        try {realMain(args);} catch (Throwable t) {unexpected(t);}
        System.out.println("\nPassed = " + passed + " failed = " + failed);
        if (failed > 0) throw new AssertionError("Some tests failed");}
}
