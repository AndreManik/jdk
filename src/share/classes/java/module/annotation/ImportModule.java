/*
 * Copyright 2008 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
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

package java.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates an import of a module definition.  This metadata annotation
 * is used as nested annotation inside other enclosing annotations.
 * <p>
 * For example,
 * <blockquote><pre>
 *    &#064;ImportModules({
 *       &#064;ImportModule(name="org.xml.foo", version="[1.0, 1.1)"),
 *       &#064;ImportModule(name="com.sun.zombie", version="[2.0, 3.0)", optional=false, reexport=true)
 *    })
 *    superpackage com.wombat.xyz {
 *        ...
 *    }
 * </pre></blockquote>
 *
 * @see java.module.annotation.ImportModules
 * @since 1.7
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImportModule {

    /**
     * Name of the imported module definition.
     */
    String name();

    /**
     * Version constraint of the imported module definition.
     * <p>
     * The version constraint is either a version, a version range, or a
     * combination of both, following the format described in the
     * {@link java.module.VersionConstraint} class. If the version constraint
     * is not specified, the default is the highest version available.
     */
    String version() default "0.0.0.0+";

    /**
     * Optionality of the import module definition at runtime.
     * <p>
     * If the optionality is not specified, the default is false.
     */
    boolean optional() default false;

    /**
     * Re-export the import module definition.
     * <p>
     * If the re-export is not specified, the default is false.
     */
    boolean reexport() default false;
}
